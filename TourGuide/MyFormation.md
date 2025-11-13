https://openclassrooms.com/fr/courses/5684021-be-more-efficient-with-advanced-java-programming/6655161-connect-concurrent-actions-using-completablefutures

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


