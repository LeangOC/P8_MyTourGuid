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

# Future
1. # Future<Integer> aSquaredFuture = executorService.submit(() -> a * a);
   # Future<Integer> bSquaredFuture = executorService.submit(() -> b * b);
   => On lance deux calculs (Threads Worker ou Pool ) a² et b² en **parallèle** grâce à un ExecutorService
2. # Integer aSquared = aSquaredFuture.get();
   # Integer bSquared = bSquaredFuture.get();
   =>on bloque le thread principal avec get() jusqu’à ce que chaque calcul soit terminé.  
3. # Double c = Math.sqrt(aSquared + bSquared);  
   =>Une fois qu’on a a² et b², on peut finalement calculer c = √(a² + b²)  
   <img width="786" height="503" alt="image" src="https://github.com/user-attachments/assets/cba54b52-fda2-4ecd-89bc-48d132bd9285" />
