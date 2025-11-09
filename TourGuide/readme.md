# Technologies

> Java 17  
> Spring Boot 3.X  
> JUnit 5  

# How to have gpsUtil, rewardCentral and tripPricer dependencies available ?

> Run : 
- mvn install:install-file -Dfile=/libs/gpsUtil.jar -DgroupId=gpsUtil -DartifactId=gpsUtil -Dversion=1.0.0 -Dpackaging=jar  
- mvn install:install-file -Dfile=/libs/RewardCentral.jar -DgroupId=rewardCentral -DartifactId=rewardCentral -Dversion=1.0.0 -Dpackaging=jar  
- mvn install:install-file -Dfile=/libs/TripPricer.jar -DgroupId=tripPricer -DartifactId=tripPricer -Dversion=1.0.0 -Dpackaging=jar

# dev1
1.  Installation des trois dépendances avec un chemin relatif (./libs)

# dev2
- Activer le test nearAllAttractions() de  TestRewardsService ( voir Solution.md)
1.    Erreur :   ConcurrentModificationException : RewardsService.calculateRewards(RewardsService.java:43)
2.    Erreur :  "Expected :26  et  Actual : 1"

- Activer le getTripDeals() de TestTourGuideService
1. Erreur :  org.opentest4j.AssertionFailedError:  Expected :10     Actual   :5

- Activer les deux tests de TestPerformance:
1. highVolumeTrackLocation: Time Elapsed: 8 seconds
2. highVolumeGetRewards: Time Elapsed: 42 seconds.

# dev2b :
Activation getNearbyAttractions() de TestTourGuideService 
org.opentest4j.AssertionFailedError:
                            Expected :5
                            Actual   :0

1. Refactorer TourGuideController, TestTourGuideService.getNearbyAttractions , service.getNearByAttractions,
2. Implémentation service.getDistance , model.NearbyAttraction et RewardsService.getRewardPointsForUserId
3. Test getNearByAttractions OK
4. Verification endpoints OK
