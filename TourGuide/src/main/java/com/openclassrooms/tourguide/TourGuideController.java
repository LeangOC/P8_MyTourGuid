package com.openclassrooms.tourguide;

import java.util.List;

import com.openclassrooms.tourguide.model.NearbyAttraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gpsUtil.location.VisitedLocation;

import com.openclassrooms.tourguide.service.TourGuideService;
import com.openclassrooms.tourguide.user.User;
import com.openclassrooms.tourguide.user.UserReward;

import tripPricer.Provider;

@RestController
public class TourGuideController {

	@Autowired
	TourGuideService tourGuideService;
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }


    @GetMapping("/getLocation")
    public ResponseEntity<?> getLocation(@RequestParam String userName) {

        User user = tourGuideService.getUser(userName);
        if (user == null) {
            return ResponseEntity.badRequest().body("Unknown user: " + userName);
        }
        VisitedLocation location = tourGuideService.getUserLocation(user);
        return ResponseEntity.ok(location);
    }
    //  Modification effectuée :
    //  TODO: Change this method to no longer return a List of Attractions.
    //  Instead: Get the closest five tourist attractions to the user - no matter how far away they are.
    //  Return a new JSON object that contains:
    // Name of Tourist attraction,
    // Tourist attractions lat/long,
    // The user's location lat/long,
    // The distance in miles between the user's location and each of the attractions.
    // The reward points for visiting each Attraction.
    //    Note: Attraction reward points can be gathered from RewardsCentral
@GetMapping("/getNearbyAttractions")
public ResponseEntity<?> getNearbyAttractions(@RequestParam String userName) {

    User user = tourGuideService.getUser(userName);
    if (user == null) {
        return ResponseEntity.badRequest().body("Unknown user: " + userName);
    }

    VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);
    List<NearbyAttraction> result = tourGuideService.getNearByAttractions(visitedLocation);

    return ResponseEntity.ok(result);
}

    @GetMapping("/getRewards")
    public ResponseEntity<?> getRewards(@RequestParam String userName) {

        User user = tourGuideService.getUser(userName);
        if (user == null) {
            return ResponseEntity.badRequest().body("Unknown user: " + userName);
        }

        List<UserReward> rewards = tourGuideService.getUserRewards(user);
        return ResponseEntity.ok(rewards);
    }

    @GetMapping("/getTripDeals")
    public ResponseEntity<?> getTripDeals(@RequestParam String userName) {

        User user = tourGuideService.getUser(userName);
        if (user == null) {
            return ResponseEntity.badRequest().body("Unknown user: " + userName);
        }

        List<Provider> deals = tourGuideService.getTripDeals(user);
        return ResponseEntity.ok(deals);
    }

}