package com.openclassrooms.tourguide.tracker;

import java.util.List;
import java.util.concurrent.*;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openclassrooms.tourguide.service.TourGuideService;

public class Tracker {

	private Logger logger = LoggerFactory.getLogger(Tracker.class);

	// Toutes les 5 minutes
	private static final long trackingPollingIntervalSeconds = TimeUnit.MINUTES.toSeconds(5);

	// 🔥 Nouveau : un scheduler propre
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	private final TourGuideService tourGuideService;
	private ScheduledFuture<?> taskHandle;

	public Tracker(TourGuideService tourGuideService) {
		this.tourGuideService = tourGuideService;

		// 🔥 Tâche exécutée toutes les X minutes
		this.taskHandle = scheduler.scheduleAtFixedRate(
				this::runTrackerTask,
				0,
				trackingPollingIntervalSeconds,
				TimeUnit.SECONDS
		);
	}

	private void runTrackerTask() {

		try {
			logger.debug("Tracker starting.");

			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			// 🔥 Appel optimisé !
			tourGuideService.trackAllUsers();

			stopWatch.stop();
			logger.debug("Tracker Time Elapsed: " +
					TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");

		} catch (Exception e) {
			logger.error("Error in tracker execution", e);
		}
	}

	public void stopTracking() {
		logger.debug("Stopping Tracker...");
		if (taskHandle != null) {
			taskHandle.cancel(true);
		}
		scheduler.shutdownNow();
	}
}
