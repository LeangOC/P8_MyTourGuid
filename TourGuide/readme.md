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

# dev1_ci <= dev1
1. Mettre en place le Pipeline d'Integration Continue avec GitHub Actions. 
2. creation fichier maven.xml dans .github/worklows/maven.xml avec -Dfile=libs/gpsUtil.jar
3. La structure du projet  
P8_MyTourGuid/  
 ├── .github/workflows/maven.yml  
 └── TourGuide/  
      ├── pom.xml  
      └── libs/  
          ├── gpsUtil.jar  
          ├── TripPricer.jar  
          └── RewardCentral.jar
      
Run avec Actions :    
<img width="282" height="306" alt="image" src="https://github.com/user-attachments/assets/1ae4c65c-5d66-4bd7-a993-a73ab924c779" />  

