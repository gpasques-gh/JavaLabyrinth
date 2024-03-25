import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Labyrinthe {
    public final static char MUR = 'X';
    public final static char PJ = 'P';
    public final static char SORTIE = 'S';
    public final static char VIDE = '.';
    public final static String HAUT = "haut";
    public final static String BAS = "bas";
    public final static String GAUCHE = "gauche";
    public final static String DROITE = "droite";
    private static boolean[][] murs;

    private Sortie sortie;
    private Personnage personnage;

    /**
     * @param x position de la ligne
     * @param y position de la colonne
     * @return un caractère char décrivant le contenu de la case (x,y).
     */
    public char getChar(int x, int y) {
        char caseCar; // instancie un caractère
        // si c'est un mur
        if (murs[x][y]) caseCar = MUR;
            // si c'est une sortie
        else if (sortie.getX() == x && sortie.getY() == y) caseCar = SORTIE;
            // si c'est un personnage
        else if (personnage.getX() == x && personnage.getY() == y) caseCar = PJ;
            // si c'est vide
        else caseCar = VIDE;
        return caseCar;
    }

    /**
     * @param l nombre de lignes dans le labyrinthe
     * @param c nombre de colonnes dans le labyrinthe
     * @throws IOException
     */
    public Labyrinthe(int l, int c) throws IOException {
        murs = new boolean[l][c];
    }

    /**
     * @param x      ligne
     * @param y      colonne
     * @param action direction sera une des constantes d´efinies (HAUT, BAS, GAUCHE, DROITE).
     * @return retourner les coordonnées de la case voisine de (x,y) selon la direction passée en paramètre.
     */

    static int[] getSuivant(int x, int y, String action) throws ActionInconnueException {
        int[] voisin = new int[2];
        switch (action) {
            case (HAUT): // si action haut
                voisin[0] = x - 1;
                voisin[1] = y;
                break;
            case (BAS): // si action bas
                voisin[0] = x + 1;
                voisin[1] = y;
                break;
            case (GAUCHE): // si action gauche
                voisin[0] = x;
                voisin[1] = y - 1;
                break;
            case (DROITE):
                voisin[0] = x; // si action droite
                voisin[1] = y + 1;
                break;
            default:
                throw new ActionInconnueException("action inconnue : " + action);
        }
        return voisin;
    }


    /**
     * methode deplacerPerso
     * @param action
     */
    void deplacerPerso(String action) throws ActionInconnueException {
        // tableau d'entier
        int[] coordonnees = getSuivant(personnage.getX(), personnage.getY(), action);
        int[] co2 = coordonnees; // second tableau pour sauvegarder l'ancien
        char c = getChar(coordonnees[0], coordonnees[1]); // caractère aux coordonnées
        char c2 = ' '; // second caractère pour sauvegarder l'ancier
        while (c != MUR) { // tant que le caractère n'est pas un mur
            c2 = c; // sauvegarde l'ancien caractère
            co2 = coordonnees; // sauvegardes les anciennes coordonnées
            // prend les coordonnées suivantes
            coordonnees = getSuivant(coordonnees[0], coordonnees[1], action);
            // prend le caractère des coordonnées
            c = getChar(coordonnees[0], coordonnees[1]);
        }
        switch (c2) { // conditions
            case (VIDE):
            case (SORTIE): // si sortie ou case vide
                personnage.setX(co2[0]);
                personnage.setY(co2[1]);
                break;
            default: // si c'est un mur
                break;
        }
    }


    /***
     * méthode toString
     * @return toString
     */
    @Override
    public String toString() {
        StringBuilder lab = new StringBuilder();
        for (int i = 0; i < murs.length; i++) {
            for (int j = 0; j < murs[i].length; j++) {
                lab.append(getChar(i, j));
            }
            lab.append("\n");
        }
        return lab.toString();
    }

    /***
     * méthode etreFini
     * @return vrai si le personnage est sur la sortie
     */
    public boolean etreFini() {
        // renvoit vrai si le personnage est sur la sortie
        return personnage.getX() == sortie.getX() && personnage.getY() == sortie.getY();
    }

    /***
     * méthode chargerLabyrinthe
     * charge un labyrinthe depuis un fichier .txt
     * @param nom chemin vers le fichier .txt
     * @return le labyrinthe créé à partir du fichier
     * @throws IOException erreur d'entrées/sorties
     * @throws NumberFormatException si les nombres ne sont pas entiers
     */
    public static Labyrinthe chargerLabyrinthe(String nom) throws IOException, NumberFormatException {
        try {
            int compteur = 0; // compteur des lignes
            FileReader fileReader = new FileReader((nom)); // ouvre un fichier .txt
            // ouvre un BufferedReader à partir du FileReader
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String ligne = bufferedReader.readLine(); // lis la première ligne
            // la convertit en entier representant le nombre de lignes du labyrinthe
            int lignes = Integer.parseInt(ligne);
            ligne = bufferedReader.readLine(); // lis la deuxième ligne
            // la convertit en entier representant le nombre de colonnes du labyrinthe
            int colonnes = Integer.parseInt(ligne);
            // instancie un nouveau labyrinthe en fonction du nombre de lignes et de colonnes
            Labyrinthe laby = new Labyrinthe(lignes, colonnes);
            // la ligne suivante
            ligne = bufferedReader.readLine();
            while (ligne != null) { // boucle sur les lignes du BufferedReader
                // vérifie si le nombre de colonnes et la longueur de la ligne correspondent
                if (ligne.length() != colonnes)
                    // si non lance une FichierIncorrectException
                    throw new FichierIncorrectException("nbColonnes ne correspond pas");
                // boucle sur les caractères de la ligne
                for (int i = 0; i < colonnes; i++) {
                    switch (ligne.charAt(i)) {
                        case (MUR): // si c'est un mur (X)
                            laby.murs[compteur][i] = true; // c'est un mur donc true
                            break;
                        case (PJ): // si c'est un personnage (P)
                            laby.murs[compteur][i] = false; // pas un mur donc false
                            if (laby.personnage != null) // verifie s'il n'y a pas déjà un personnage
                                // si oui lance une FichierIncorrectException
                                throw new FichierIncorrectException("plusieurs personnages");
                            // si non instancie un nouveau personnage
                            laby.personnage = new Personnage(compteur, i);
                            break;
                        case (SORTIE): // si c'est une sortie (S)
                            laby.murs[compteur][i] = false; // pas un mur donc false
                            if (laby.sortie != null) // vérifie s'il n'y a pas déjà une sortie
                                // si oui lance une FichierIncorrectException
                                throw new FichierIncorrectException("plusieurs sorties");
                            // si non instancie une nouvelle sortie
                            laby.sortie = new Sortie(compteur, i);
                            break;
                        case (VIDE): // si c'est une case vide (.)
                            laby.murs[compteur][i] = false; // pas un mur donc false
                            break;
                        default: // si ce n'est pas un caractère valide
                            // lance une FichierIncorrectException
                            throw new FichierIncorrectException("caractère inconnu <" + ligne.charAt(i) + ">");
                    }
                }
                ligne = bufferedReader.readLine(); // lit la ligne suivante
                compteur++; // incrémente le compteur de lignes
            }
            // Vérifications des conditions
            if (compteur != lignes) // si il n'y a pas autant de lignes que prévu
                // lance une FichierIncorrectException
                throw new FichierIncorrectException("nbLignes ne correspond pas");
            if (laby.personnage == null) // s'il n'y a pas de personnage
                // lance une FichierIncorrectException
                throw new FichierIncorrectException("personnage inconnu");
            if (laby.sortie == null) // s'il n'y a pas de sortie
                // lance une FichierIncorrectException
                throw new FichierIncorrectException("sortie inconnue");
            return laby; // si tout s'est bien passé, renvoit le labyrinthe
        } catch (NumberFormatException e) {
            // si le nombre de lignes/colonnes n'était pas un entier lance une FichierIncorrectException
            throw new FichierIncorrectException("pb num lignes ou colonnes");
        }
    }
}
