La requête Get "http://localhost:8080/rewards?userName=internalUser0" retourne vide.
=> c'est normal :

1. En mode Test, l'utilisateur  n’a aucune location récente au moment où on appelle l'endpoint  /getRewards
  =>  generateUserLocationHistory(user) ajoute 3 locations aléatoires mais ne calcule jamais les récompenses.
 
2.  En mode Production ( Tracker démarré et apple ) : Utilisateur est inexistant ( car pas de base d'utilisateurs) 