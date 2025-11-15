package com.openclassrooms.tourguide;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import com.openclassrooms.tourguide.helper.InternalTestHelper;
import com.openclassrooms.tourguide.service.RewardsService;
import com.openclassrooms.tourguide.service.TourGuideService;
import com.openclassrooms.tourguide.user.User;
import java.util.concurrent.ThreadPoolExecutor;

public class TestPerformance {

	/*
	 * A note on performance improvements:
	 * 
	 * The number of users generated for the high volume tests can be easily
	 * adjusted via this method:
	 * 
	 * InternalTestHelper.setInternalUserNumber(100000);
	 * 
	 * 
	 * These tests can be modified to suit new solutions, just as long as the
	 * performance metrics at the end of the tests remains consistent.
	 * 
	 * These are performance metrics that we are trying to hit:
	 * 
	 * highVolumeTrackLocation: 100,000 users within 15 minutes:
	 * assertTrue(TimeUnit.MINUTES.toSeconds(15) >=
	 * TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 *
	 * highVolumeGetRewards: 100,000 users within 20 minutes:
	 * assertTrue(TimeUnit.MINUTES.toSeconds(20) >=
	 * TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */

	//@Disabled
	@Test
	public void highVolumeTrackLocation() {
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		// Users should be incremented up to 100,000, and test finishes within 15
		// minutes
		InternalTestHelper.setInternalUserNumber(10000);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

		List<User> allUsers = tourGuideService.getAllUsers();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		// Taille du pool : on choisit une valeur raisonnable (ex : min(100, nbrUsers))
			//int threadPoolSize = Math.min(100, Math.max(4, Runtime.getRuntime().availableProcessors() * 10));
			//ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

		// Pool auto-adaptatif très performant
			//ExecutorService executor = Executors.newCachedThreadPool();
			//ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;

        // Taille du pool max 300
		int threadPoolSize = Math.min(allUsers.size(), 300);
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

		try {
			// Lance un CompletableFuture pour chaque utilisateur
			List<CompletableFuture<Void>> futures = allUsers.stream()
					.map(user -> CompletableFuture.runAsync(() -> {
						tourGuideService.trackUserLocation(user);
					}, executor))
					.collect(Collectors.toList());

			// Attend la complétion de tous les futures
			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

		} finally {
			// Arrêt propre du pool
			executor.shutdown();
		}


		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeTrackLocation: Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	//@Disabled
	@Test
	public void highVolumeGetRewards() {
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());

		// Users should be incremented up to 100,000, and test finishes within 20
		// minutes
		InternalTestHelper.setInternalUserNumber(100000);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

		List<User> allUsers = tourGuideService.getAllUsers();

		// Ajout d'une attraction visitée pour déclencher un reward
		Attraction attraction = gpsUtil.getAttractions().get(0);
		for (User user : allUsers) {
			user.addToVisitedLocations(
					new VisitedLocation(user.getUserId(), attraction, new Date())
			);
		}

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ExecutorService executor = Executors.newCachedThreadPool();
		ThreadPoolExecutor tpe = (ThreadPoolExecutor) executor;
		try {

			List<CompletableFuture<Void>> futures = allUsers.stream()
					.map(user -> CompletableFuture.runAsync(() -> {
						rewardsService.calculateRewards(user);
					}, executor))
					.collect(Collectors.toList());

			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

		} finally {
			executor.shutdown();
		}

		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeGetRewards: Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");

		// Test OpenClassrooms : doit finir en moins de 20 minutes
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

}



