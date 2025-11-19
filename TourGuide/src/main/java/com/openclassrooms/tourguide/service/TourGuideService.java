package com.openclassrooms.tourguide.service;

import com.openclassrooms.tourguide.helper.InternalTestHelper;
import com.openclassrooms.tourguide.model.NearbyAttraction;
import com.openclassrooms.tourguide.tracker.Tracker;
import com.openclassrooms.tourguide.user.User;
import com.openclassrooms.tourguide.user.UserReward;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;

import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TourGuideService {

	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	private final GpsUtil gpsUtil;
	private final RewardsService rewardsService;
	private final TripPricer tripPricer = new TripPricer();
	public final Tracker tracker;
	boolean testMode = true;



	// Ajout : Exécuteur interne pour le suivi des utilisateurs
	private final ExecutorService executor = Executors.newCachedThreadPool();

	//  Clé API TripPricer
	private static final String tripPricerApiKey = "test-server-api-key";

	// Utilisateurs internes (mémoire)
	private final Map<String, User> internalUserMap = new HashMap<>();

	public TourGuideService(GpsUtil gpsUtil, RewardsService rewardsService) {
		this.gpsUtil = gpsUtil;
		this.rewardsService = rewardsService;

		Locale.setDefault(Locale.US);

		if (testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
			//  NE PAS démarrer le tracker en mode test
			this.tracker = new Tracker(this, false);// Tracker créé mais non démarré
			logger.info("Tracker created but not started");
		} else {
			this.tracker = new Tracker(this, true); // Tracker actif
		}
		addShutDownHook();
	}

	// =====================================================================
	//  MÉTHODE AJOUTÉE : TRACKING PARALLÈLE DE TOUS LES UTILISATEURS
	// =====================================================================
	public void trackAllUsers() {
		List<User> users = getAllUsers();

		List<CompletableFuture<Void>> futures = users.stream()
				.map(user -> CompletableFuture.runAsync(() -> trackUserLocation(user), executor))
				.collect(Collectors.toList());

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
	}

	//  Permet d’arrêter proprement le pool
	public void shutdown() {
		try {
			if (tracker != null) {
				tracker.stopTracking();
			}
		} catch (Exception ignored) {}

		executor.shutdown();
	}



	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}

	public VisitedLocation getUserLocation(User user) {
		VisitedLocation visitedLocation = (user.getVisitedLocations().size() > 0)
				? user.getLastVisitedLocation()
				: trackUserLocation(user);
		return visitedLocation;
	}

	public User getUser(String userName) {
		return internalUserMap.get(userName);
	}

	public List<User> getAllUsers() {
		return new ArrayList<>(internalUserMap.values());
	}

	public void addUser(User user) {
		if (!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}

	public List<Provider> getTripDeals(User user) {

		int cumulativeRewardPoints = user.getUserRewards().stream()
				.mapToInt(UserReward::getRewardPoints)
				.sum();

		List<Provider> providers = new ArrayList<>();

		providers.addAll(tripPricer.getPrice(
				tripPricerApiKey,
				user.getUserId(),
				user.getUserPreferences().getNumberOfAdults(),
				user.getUserPreferences().getNumberOfChildren(),
				user.getUserPreferences().getTripDuration(),
				cumulativeRewardPoints
		));

		providers.addAll(tripPricer.getPrice(
				tripPricerApiKey,
				user.getUserId(),
				user.getUserPreferences().getNumberOfAdults(),
				user.getUserPreferences().getNumberOfChildren(),
				user.getUserPreferences().getTripDuration(),
				cumulativeRewardPoints
		));

		user.setTripDeals(providers);
		return providers;
	}

	public VisitedLocation trackUserLocation(User user) {
		VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
		user.addToVisitedLocations(visitedLocation);
		rewardsService.calculateRewards(user);
		return visitedLocation;
	}

	public List<NearbyAttraction> getNearByAttractions(VisitedLocation visitedLocation) {

		return gpsUtil.getAttractions().stream()
				.sorted((a1, a2) -> Double.compare(
						getDistance(new Location(a1.latitude, a1.longitude), visitedLocation.location),
						getDistance(new Location(a2.latitude, a2.longitude), visitedLocation.location)
				))
				.limit(5)
				.map(a -> {
					double distance = getDistance(
							new Location(a.latitude, a.longitude),
							visitedLocation.location);

					int rewardPoints = rewardsService.getRewardPointsForUserId(a, visitedLocation.userId);

					return new NearbyAttraction(
							a.attractionName,
							a.latitude,
							a.longitude,
							visitedLocation.location.latitude,
							visitedLocation.location.longitude,
							distance,
							rewardPoints
					);
				})
				.collect(Collectors.toList());
	}

	// =====================================================================
	//  UTILITAIRES
	// =====================================================================

	private double getDistance(Location loc1, Location loc2) {
		double lat1 = Math.toRadians(loc1.latitude);
		double lon1 = Math.toRadians(loc1.longitude);
		double lat2 = Math.toRadians(loc2.latitude);
		double lon2 = Math.toRadians(loc2.longitude);

		double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2)
				* Math.cos(lon1 - lon2));

		return angle * 3958.8;
	}

	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown()));
	}

	// =====================================================================
	// INITIALISATION (Tests OC)
	// =====================================================================

	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);

			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}

	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i -> {
			user.addToVisitedLocations(new VisitedLocation(
					user.getUserId(),
					new Location(generateRandomLatitude(), generateRandomLongitude()),
					getRandomTime()));
		});
	}

	private double generateRandomLongitude() {
		return -180 + new Random().nextDouble() * (360);
	}

	private double generateRandomLatitude() {
		return -85.05112878 + new Random().nextDouble() * (170.10225756);
	}

	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
		return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}
}
