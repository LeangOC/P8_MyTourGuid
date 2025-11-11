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

# dev3 :
Activation getNearbyAttractions() de TestTourGuideService 
org.opentest4j.AssertionFailedError:
                            Expected :5
                            Actual   :0

1. Refactorer TourGuideController, TestTourGuideService.getNearbyAttractions , service.getNearByAttractions,
2. Implémentation service.getDistance , model.NearbyAttraction et RewardsService.getRewardPointsForUserId
3. Test getNearByAttractions OK
4. Verification endpoints OK  

Requête sous Postman GET : http://localhost:8080/getNearbyAttractions?userName=internalUser0

<img width="706" height="828" alt="image" src="https://github.com/user-attachments/assets/9fc71183-f121-465e-b3b9-63d77d521f4a" />  

# dev4 
Test de performance avant optimisation : 
  - le nombre d’utilisateurs : 100  
    <img width="711" height="147" alt="image" src="https://github.com/user-attachments/assets/c0469913-4328-4214-a29b-8f97c7ae5b97" />

- le nombre d’utilisateurs : 1.000  
  <img width="707" height="141" alt="image" src="https://github.com/user-attachments/assets/c3e5d46a-7b54-426b-917e-b37162106b48" />

- le nombre d’utilisateurs : 10.000  
  <img width="864" height="389" alt="image" src="https://github.com/user-attachments/assets/47d9412b-b1ff-44b2-be32-86b4fba375a8" />
  
 - le nombre d'utilisateurs : 100.000  
   <img width="1292" height="630" alt="image" src="https://github.com/user-attachments/assets/3c6760de-f6db-46ea-bc62-ca59ed671118" />



