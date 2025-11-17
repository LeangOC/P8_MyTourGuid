# dev1
2. Erreur :  "Expected :26  et  Actual : 1"
Même si l’utilisateur n’a qu’une seule position visitée (ou les trois par défaut de tracker), et que 
proximityBuffer = Integer.MAX_VALUE,alors toutes les attractions (26 dans la base gpsUtil) sont censées 
être considérées comme proches,et donc calculateRewards() devrait générer 26 récompenses (une par attraction)


Ajoute ceci après calculateRewards() dans le test nearAllAttractions():

System.out.println("Rewards found: " + user.getUserRewards().size());
user.getUserRewards().forEach(r ->System.out.println("→ " + r.attraction.attractionName + " / " + r.visitedLocation.location));

Résultat d'affichage :
Rewards found: 1
→ Disneyland / gpsUtil.location.Location@69997e9d org.opentest4j.AssertionFailedError:
Expected :26 Actual :1