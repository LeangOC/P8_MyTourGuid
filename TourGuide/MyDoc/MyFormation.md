https://openclassrooms.com/fr/courses/5684021-be-more-efficient-with-advanced-java-programming/6655161-connect-concurrent-actions-using-completablefutures

# CompletableFuture  

1. # CompletableFuture<String> future = new CompletableFuture<>();
    => Un conteneur vide qui contiendra une valeur de type String dans le **futur**

2. # future.thenAccept((person) -> System.out.println("Hi " + person));
    => Définir ce qu’il faut faire quand le futur se termine
    => thenAccept(...) : méthode qui prend une lambda 
    => Cette lambda reçoit en paramètre la valeur produite par le futur (person).

3. # future.complete("Captain Crunchy");      
    => Compléter le futur avec une valeur  

Copy d'écran de Jshell :  
<img width="610" height="299" alt="image" src="https://github.com/user-attachments/assets/b673934b-89a0-4892-935b-3c67091790a4" />

# Future - ExecutorService 
1. # Future<Integer> aSquaredFuture = executorService.submit(() -> a * a);
   # Future<Integer> bSquaredFuture = executorService.submit(() -> b * b);
   => On lance deux calculs (Threads Worker ou Pool ) a² et b² en **parallèle** grâce à un ExecutorService
2. # Integer aSquared = aSquaredFuture.get();
   # Integer bSquared = bSquaredFuture.get();
   =>on bloque le thread principal avec get() jusqu’à ce que chaque calcul soit terminé.  
3. # Double c = Math.sqrt(aSquared + bSquared);  
   =>Une fois qu’on a a² et b², on peut finalement calculer c = √(a² + b²)  
   <img width="813" height="466" alt="image" src="https://github.com/user-attachments/assets/a4c9c07b-dc0b-4000-9a4e-9a074532c51b" />
 

# Schéma simplifié de notion Thread Principal et worker ou pool
TP (thread principal)  
├─► crée TP1 → calcule a*a 
├─► crée TP2 → calcule b*b  
├─► appelle aSquaredFuture.get()  → attend TP1  
├─► appelle bSquaredFuture.get()  → attend TP2  
└─► calcule √(a² + b²)  

TP1 et TP2 = vrais threads de travail  
TP = le thread principal qui orchestre et attend  
.get() = une action de blocage, pas un thread supplémentaire  

Conclusion entre Future et CompleteFuture:  
Le ExecutorService() permet d'optimiser la vitesse de traitement en utilisant le multithreading .  
Et tandis que Future() et CompleteFuture() se différencient par la façon de gérer le multithreading,   
l'un manuel avec get qui peut bloquer le thread principal alors que l'autre de manière automatique   
mais à la fin les deux arrivent bien à la même vitesse si je me trompe pas.  

ps -L -p 12345 ( ou 12345 est le processus java )  
Cela affiche tous les threads (LWP = Lightweight Processes) du processus Java  

<img width="817" height="530" alt="image" src="https://github.com/user-attachments/assets/50096c70-7e2a-4128-adb7-e8643e6902fe" />

"main"(27837) → le thread principal
"pool-1-thread-1", "pool-1-thread-2" → tes threads workers
les autres → threads internes de la JVM (garbage collector, etc.)

executorService.shutdown(); = > Termine toutes les tâches et ferme tout le pool

Et si on veut "arrêter un seul thread":  

    ExecutorService poolA = Executors.newSingleThreadExecutor();
    ExecutorService poolB = Executors.newSingleThreadExecutor();

    // Tu peux arrêter indépendamment chaque pool
    poolA.shutdown();  // Arrête le thread A
    poolB.shutdown();  // Arrête le thread B

# CompleteFuture vs Future

    Integer a = 2;
    Integer b = 2;

    CompletableFuture<Double> cFuture =
    CompletableFuture.supplyAsync(() -> a * a)
        .thenCombine(
            CompletableFuture.supplyAsync(() -> b * b),
            (aSquared, bSquared) -> Math.sqrt(aSquared + bSquared)
        );

    cFuture.get();

=> supplyAsync(() -> a * a)
Crée un CompletableFuture qui calcule a² de manière asynchrone.  
Idem pour b².  
Ces deux appels s’exécutent en parallèle sans bloquer le thread principal.  

=>thenCombine(...)
Permet de combiner le résultat de deux CompletableFuture dès qu’ils sont tous les deux terminés.
La fonction (aSquared, bSquared) -> Math.sqrt(aSquared + bSquared) s’exécute automatiquement quand les deux résultats sont disponibles

Ce qu’il faut retenir :  
Future : bonne première approche, mais limitée. On doit gérer manuellement l’attente des résultats.  
CompletableFuture : plus moderne et puissant.  
Permet de chaîner les calculs (thenApply, thenCombine, etc.)  
Facilite la programmation réactive et non bloquante  
Réduit le code répétitif  

Exemple concret.
comparer le temps d’exécution d’une même tâche :
 - d’abord de façon séquentielle (1 seul thread)  
<img width="322" height="150" alt="image" src="https://github.com/user-attachments/assets/32a6e988-6a78-475b-bfea-bc83165dcc6f" />
<img width="400" height="123" alt="image" src="https://github.com/user-attachments/assets/106b9078-3b24-4bf5-a032-4fa955c9e512" />  

 - puis en parallèle (avec plusieurs threads dans un ExecutorService)

<img width="383" height="292" alt="image" src="https://github.com/user-attachments/assets/188c389a-4ffb-4d17-9dbd-1c839230583a" />
<img width="443" height="265" alt="image" src="https://github.com/user-attachments/assets/be81773f-2453-4ab0-90ad-a0b1c6cdc472" />
<img width="206" height="245" alt="image" src="https://github.com/user-attachments/assets/2f255546-05f3-4d00-ae97-282e6437db6f" />   
  
Conclusion:  
Les 5 tâches durent environ 1 seconde au total, au lieu de 5 secondes !  
(car elles sont exécutées en parallèle sur 5 threads différents).  

