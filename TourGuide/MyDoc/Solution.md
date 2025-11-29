# dev2 
1. ConcurrentModificationException
   Cela veut dire que dans calculateRewards(), on modifies une liste pendant que l'on l'itères
   => d'après le cours "Modify Arrays on Multiple Threads with **CopyOnWriteArrayList**", il faut remplacer User():
   
   private List<VisitedLocation> visitedLocations = new ArrayList<>();
   private List<UserReward> userRewards = new ArrayList<>();
   par  
   private List<VisitedLocation> visitedLocations = new CopyOnWriteArrayList<>();  
   private List<UserReward> userRewards = new CopyOnWriteArrayList<>();  
   
2. Erreur :  "Expected :26  et  Actual : 1"
   Remplacer dans User() : 

   public void addUserReward(UserReward userReward) {
      if(userRewards.stream().filter(r -> !r.attraction.attractionName.equals(userReward.attraction)).count() == 0) {
      userRewards.add(userReward);
   }  
par  
   public void addUserReward(UserReward userReward) {
      boolean alreadyExists = userRewards.stream()
      .anyMatch(r -> r.attraction.attractionName.equals(userReward.attraction.attractionName));
      if (!alreadyExists) {
      userRewards.add(userReward);
      }
   }  

3. Erreur getTripDeals()
   Comme TripPricer génère 5 offres (for (int i = 0; i < 5; i++) ...) d'où erreur.
   solution refactore la méthode getTipDeals pour repasse deux fois pour 10 offres au lieu de 5.
   => Voir le nouveau méthode getTripDeals()

