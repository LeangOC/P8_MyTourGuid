package com.openclassrooms.tourguide.service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import com.openclassrooms.tourguide.user.User;
import com.openclassrooms.tourguide.user.UserReward;

@Service
public class RewardsService {

	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
	private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private final GpsUtil gpsUtil;
	private final RewardCentral rewardsCentral;

	// Ajout Pool interne pour paralléliser les récompenses
	private final ExecutorService executor = Executors.newCachedThreadPool();

	public RewardsService(GpsUtil gpsUtil, RewardCentral rewardCentral) {
		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;
	}


	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}


	//  MÉTHODE 1 : VERSION SYNCHRONE EXISTANTE
	public void calculateRewards(User user) {
		List<VisitedLocation> userLocations = user.getVisitedLocations();
		List<Attraction> attractions = gpsUtil.getAttractions();

		for(VisitedLocation visitedLocation : userLocations) {
			for(Attraction attraction : attractions) {
				if(user.getUserRewards().stream().filter(r -> r.attraction.attractionName.equals(attraction.attractionName)).count() == 0) {
					if(nearAttraction(visitedLocation, attraction)) {
						user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
					}
				}
			}
		}
	}
	/* MY
	public void calculateRewards(User user) {
		List<VisitedLocation> visited = user.getVisitedLocations();
		List<Attraction> attractions = gpsUtil.getAttractions();

		// Pour éviter les doublons, plus performant que stream().count()
		Set<String> alreadyRewarded = user.getUserRewards().stream()
				.map(r -> r.attraction.attractionName)
				.collect(Collectors.toSet());

		for (VisitedLocation visitedLocation : visited) {
			for (Attraction attraction : attractions) {
				if (alreadyRewarded.contains(attraction.attractionName)) {
					continue;
				}
				if (nearAttraction(visitedLocation, attraction)) {
					int points = getRewardPoints(attraction, user);
					user.addUserReward(new UserReward(visitedLocation, attraction, points));
					alreadyRewarded.add(attraction.attractionName);
				}
			}
		}
	} */



	// Ajout : VERSION ASYNCHRONE POUR UN UTILISATEUR
		public CompletableFuture<Void> calculateRewardsAsync(User user) {

		return CompletableFuture.runAsync(() -> {

			List<VisitedLocation> visited = user.getVisitedLocations();
			List<Attraction> attractions = gpsUtil.getAttractions();

			Set<String> alreadyRewarded = user.getUserRewards().stream()
					.map(r -> r.attraction.attractionName)
					.collect(Collectors.toSet());

			List<CompletableFuture<Void>> futures = new ArrayList<>();

			for (VisitedLocation visitedLocation : visited) {
				for (Attraction attraction : attractions) {

					if (alreadyRewarded.contains(attraction.attractionName)) {
						continue;
					}

					if (nearAttraction(visitedLocation, attraction)) {

						CompletableFuture<Void> f = CompletableFuture.runAsync(() -> {
							int points = getRewardPoints(attraction, user);
							user.addUserReward(new UserReward(visitedLocation, attraction, points));
						}, executor);

						futures.add(f);
						alreadyRewarded.add(attraction.attractionName);
					}
				}
			}

			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

		}, executor);
	}


	// Ajout : VERSION BATCH — À UTILISER POUR LE TEST DE PERFORMANCE
		public void calculateRewardsForAllUsers(List<User> users) {

		List<CompletableFuture<Void>> futures = users.stream()
				.map(this::calculateRewardsAsync)
				.collect(Collectors.toList());

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
	}


	//  UTILITAIRES

	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) <= attractionProximityRange;
	}

	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return getDistance(attraction, visitedLocation.location) <= proximityBuffer;
	}

	private int getRewardPoints(Attraction attraction, User user) {
		return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
	}

	public int getRewardPointsForUserId(Attraction attraction, UUID userId) {
		return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, userId);
	}

	public double getDistance(Location loc1, Location loc2) {
		double lat1 = Math.toRadians(loc1.latitude);
		double lon1 = Math.toRadians(loc1.longitude);
		double lat2 = Math.toRadians(loc2.latitude);
		double lon2 = Math.toRadians(loc2.longitude);

		double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

		double nauticalMiles = 60 * Math.toDegrees(angle);
		double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
		return statuteMiles;
	}
}
