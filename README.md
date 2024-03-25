## Projet Labyrinthe Java - Qualité de développement IUTNC (BUT1 groupe S2C)

*Germain PASQUES & Baptiste HENNEQUIN*

### 1) Difficultés rencontrées
- Nous n'avons pas rencontré de difficultés particulières lors de la réalisation de ce projet, mais ce projet nous a permis de mieux nous familiariser avec les outils apportés par IntelliJ, les tests JUnit, les exceptions et les flux en Java


### 2) Nos choix de programmation
    
   - #### 2.a) Liste des différentes classes du projet
        - La classe `Labyrinthe` dans laquelle la majeure partie du programme se fait, elle possède (entre autres) un attribut de type `Personnage` et un attribut de type `Sortie`
        - Les classes `Personnage` et `Sortie` héritant de la classe `Position`
        - La classe `MainLaby` dans laquelle se trouve le `main()` du jeu
        - Les classes d'exception `ActionInconnueException` et `FichierIncorrectException` héritant de la classe `IOException`
   - #### 2.b) Calcul du mouvement avec les méthodes `getSuivant(int x, int y, String action)` et `deplacerPerso(String action)` :
        - Nous calculons le mouvement du personnage de la manière suivante :
            - Premièrement : nous utilisons la méthode `getSuivant(int x, int y, String action)` pour trouver la case adjacente (en fonction de la direction passée en paramètres). Cette méthode peut renvoyer une `ActionInconnueException` dans le cas d'une action non prévue
            - Deuxièmement : nous utilisons la méthode `deplacerPerso(String action)` qui appelle la méthode `getSuivant(int x, int y, String action)` jusqu'à être arrivé sur un mur ou sur la sortie
   - #### 2.c) Chargement du labyrinthe via un fichier texte avec la méthode `chargerLabyrinthe(String nom)`:
     - Nous utilisons un `BufferedReader` afin de pouvoir comparer la longueur attendue des colonnes (via l'entier passé en début de fichier) et la longueur réelle de la ligne avec `String.length()`
     - Nous utilisons la méthode `String.charAt(int i)` pour parcourir les lignes du fichier car cela rend le code plus lisible selon nous
     - Nous utilisons un switch sur le caractère renvoyé par `String.charAt(int i)` pour pouvoir agir en conséquence (changements dans les variables, exceptions à lancer, etc)
     - Cette méthode de chargement peut lancer une `FichierIncorrectException` dans les cas suivants :
        - Les nombres de lignes/colonnes ne sont pas des entiers
        - Les nombres de lignes/colonnes ne correspondent pas au labyrinthe du fichier
        - Il y a un caractère invalide (tout sauf 'X', 'S', 'P', '.') dans le labyrinthe du fichier
        - Il y a plusieurs sorties et/ou personnages
        - Toutes ces exceptions ont un message prédifini qui sera lu dans le `main()` lorsqu'une `FichierIncorrectException` est interceptée par le try/catch
   - #### 2.d) Classe `MainLaby` et `main()`
     - Le `main()` est composé d'un `try/catch` interceptant les `FichierIncorrectException` dans lequel se trouvent :
        - Le chargement d'un Labyrinthe via la méthode `chargerLabyrinthe(String nom)` de la classe Labyrinthe
        - L'instanciation d'un `Scanner` pour les entrées utilisateurs
        - Un second `try/catch` interceptant les `ActionInconnueException` permettant de continuer le jeu après une action imprévue à l'aide d'une boucle `while`
        - Dans cette boucle `while` se trouve une affectation d'un `Scanner.nextLine()` à une variable `String action` permettant de lancer l'appel à `Labyrinthe.deplacerPerso(String action)`
        - Cette boucle peut être arrếtée à l'aide de l'action supplémentaire "exit"
     - À la sortie de ce `try/catch` se trouve une condition verifiant si le joueur est sorti de la boucle en gagnant ou avec l'action "exit", afin de vérifier sa victoire
     

### 3) Comment lancer notre application
- Il faut lancer le `main()` de la classe `MainLaby` et écrire la direction souhaitée via le claver après que le terminal affiche la ligne : "Quelle action ?" ou "Une autre action ?" dans le cas où l'on a fait une action invalide au préalable
- On peut sortir du jeu en écrivant l'action "exit"


### 4) Résultats de tests
- Tous les tests que nous avons écrits réussissent


### 5) Présentation de nos tests
- Les tests `test_charger()` et `test_methodes()`  présents dans la classe de base n'ont pas été modifiés
- Les tests dans lesquels on devait tester une levée d'exception ont été effectués à l'aide de `assertThrows()` et de la comparaison du message attendu avec le message obtenu à l'aide d'un `assertEquals(String s, String s2)`
- Un test dans le cas où le fichier fourni ne comporte pas de sortie nommé `test_pasSortie()`
- Un test dans le cas où le fichier fourni comporte plusieurs sorties nommé `test_plusieursSorties()`
- Un test dans le cas où le fichier fourni ne comporte pas de personnage nommé `test_pasPJ()`
- Un test dans le cas où le fichier fourni comporte plusieurs personnages nommé `test_plusieursPJ()`
- Un test dans le cas où le fichier fourni n'a pas des entiers comme nombres de lignes/colonnes nommé `test_pasEntier()`
- Un test dans le cas où le fichier fourni possède un caractère invalide nommé `test_mauvaisCaractere()`
- Un test dans le cas où le labyrinthe du fichier fourni ne respecte pas le nombre de lignes indiqué nommé `test_pbLignes()`
- Un test dans le cas où le labyrinthe du fichier fourni ne respecte pas le nombre de colonnes indiqué nommé `test_pbColonnes()`
- Un test dans le cas où le joueur a entré une action invalide nommé `test_actionInconnue()`
- Un test dans le cas où le personnage se trouve sur la sortie (à l'aide d'un `assertTrue(Labyrinthe.etreFini())`)
- Après lancement des tests avec la couverture, 100% des lignes de toutes les classes (exceptée `MainLaby`) ont été utilisées
