import java.io.IOException;
import java.util.Scanner;

public class MainLaby {
    public static void main(String[] args) throws IOException {
        try {
            Scanner sc = new Scanner(System.in); // instancie un scanner (peut lancer une IOException)
            String action = ""; // action du joueur
            // instancie et charge le labyrinthe (peut lancer une FichierIncorrectException)
            Labyrinthe laby = Labyrinthe.chargerLabyrinthe("laby/laby1.txt");
            System.out.println(laby); // affichage du labyrinthe
            boolean valide = true; // verification de la validite de l'action du joueur
            while (!laby.etreFini()) { // boucle du jeu
                try { // vérifie si l'action est valide
                    if (valide) { // si oui affiche "Quelle action ?"
                        System.out.println("Quelle action ?");
                    } else { // si non affiche "Une autre action ?"
                        System.out.println("Une autre action ?");
                    }
                    action = sc.nextLine(); // prend l'entrée clavier du joueur
                    if (action.equals("exit")) break; // si le joueur entre "exit"
                    // tente de deplacer le personnage (peut lancer une ActionInconnueException)
                    laby.deplacerPerso(action);
                    valide = true; // si il n'y a pas d'exception, l'action est valide
                    System.out.println(laby); // affiche le labyrinthe
                } catch (ActionInconnueException e) { // attrape l'ActionInconnueException
                    System.out.println(e.getMessage()); // affiche le message de l'exception
                    valide = false; // l'action est invalide
                }
            }

            // Affiche "Vous avez arrêté la partie." si le joueur a entré "exit"
            if (action.equals("exit")) System.out.println("Vous avez arrêté la partie.");
            // Sinon, le joueur a forcément gagné, donc affiche "Vous avez gagné !"
            else System.out.println("Vous avez gagné !");
        } catch (FichierIncorrectException e) { // Attrape une FichierIncorrectException
            System.out.println(e.getMessage()); // affiche le message de l'exception
        }
    }
}
