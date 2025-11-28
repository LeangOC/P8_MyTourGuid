# Pipeline CI/CD 

Un pipeline CI/CD est une suite automatisée d’étapes qui permet de :  
  CI – Intégration Continue (Continuous Integration)  
  -> vérifier automatiquement que le code fonctionne à chaque modification  
  -> compiler, tester, analyser la qualité

CD – Livraison Continue (Continuous Delivery) ou Déploiement Continu (Continuous Deployment)  
  -> préparer ou déployer automatiquement l’application  


# Processus d'un pipeline CI/CD  
c’est comme une chaîne de production automatisée pour de notre code.  
À chaque fois que nous poussons une modification sur GitHub :  
1. Le code est récupéré (checkout)  
2. Le projet est compilé  
3. Les tests s’exécutent  
4. Les artefacts sont générés  
5. (Optionnel) Le projet est déployé 

Tout cela se fait sans intervention humaine, garantissant que :     
  - le code est propre     
  - les tests passent     
  - l’application reste stable    

En une phrase,un pipeline CI/CD automatise la vérification, la construction et éventuellement le déploiement d’une application à chaque mise à jour du code.
