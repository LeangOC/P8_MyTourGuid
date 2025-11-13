https://openclassrooms.com/fr/courses/5684021-be-more-efficient-with-advanced-java-programming/6655161-connect-concurrent-actions-using-completablefutures

1. # CompletableFuture<String> future = new CompletableFuture<>();
    => Un conteneur vide qui contiendra une valeur de type String dans le **futur**

2. # future.thenAccept((person) -> System.out.println("Hi " + person));
    => Définir ce qu’il faut faire quand le futur se termine
    => thenAccept(...) : méthode qui prend une lambda 
    => Cette lambda reçoit en paramètre la valeur produite par le futur (person).

3. # future.complete("Captain Crunchy");      
    => Compléter le futur avec une valeur

