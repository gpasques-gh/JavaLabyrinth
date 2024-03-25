import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * classe de test fournie destinee a verifier les methodes
 */
public class TestLabyProf {

    /**
     * test de chargement + getChar + Constantes
     * (NE PAS MODIFIER)
      */
    @Test
    public void test_charger() throws Exception{
        // utilise laby0.txt fourni
        Labyrinthe l = Labyrinthe.chargerLabyrinthe("laby/laby0.txt");

        // verifie labyrinthe
        assertEquals(l.getChar(0,0),Labyrinthe.MUR);
        assertEquals(l.getChar(1,1),Labyrinthe.SORTIE);
        assertEquals(l.getChar(2,3),Labyrinthe.PJ);
        assertEquals(l.getChar(2,1),Labyrinthe.VIDE);
    }


    /**
     * test des methodes publiques
     * (NE PAS MODIFIER)
      */
    @Test
    public void test_methodes() throws Exception{
        // utilise laby0.txt fourni
        Labyrinthe l = Labyrinthe.chargerLabyrinthe("laby/laby0.txt");

        // getsuivant et constantes action
        int[] res;
        res = Labyrinthe.getSuivant(1,1,Labyrinthe.HAUT);
        res = Labyrinthe.getSuivant(1,1,Labyrinthe.BAS);
        res = Labyrinthe.getSuivant(1,1,Labyrinthe.DROITE);
        res = Labyrinthe.getSuivant(1,1,Labyrinthe.GAUCHE);

        // deplacerPerso
        l.deplacerPerso(Labyrinthe.HAUT);

        // etrefini
        boolean b = l.etreFini();

        // toString
        String s = l.toString();
    }

    /***
     * test si le fichier n'a pas de sortie (S)
     */
    @Test
    public void test_pasSortie() {
        // utilise laby_pasSortie.txt fourni
        Exception exception = assertThrows(
                FichierIncorrectException.class,
                () -> Labyrinthe.chargerLabyrinthe("laby/laby_pasSortie.txt")
        );

        String expectedMessage = "sortie inconnue";
        assertEquals(expectedMessage, exception.getMessage(), "devrait afficher : sortie inconnue");
    }

    /***
     * test si le fichier a plusieurs sorties (S)
     */
    @Test
    public void test_plusieursSorties() {
        // utilise laby_deuxSorties.txt fourni
        Exception exception = assertThrows(
                FichierIncorrectException.class,
                () -> Labyrinthe.chargerLabyrinthe("laby/laby_deuxSortie.txt")
        );

        String expectedMessage = "plusieurs sorties";
        assertEquals(expectedMessage, exception.getMessage(), "devrait afficher : sortie inconnue");
    }

    /***
     * test si l'action du joueur est invalide
     * @throws Exception possible FichierIncorrectException
     */
    @Test
    public void test_actionInconnue() throws Exception {
        // utilise laby0.txt
        Labyrinthe l = Labyrinthe.chargerLabyrinthe("laby/laby0.txt");

        Exception exception = assertThrows(
                ActionInconnueException.class,
                () -> l.deplacerPerso("fausseAction"),
                "devrait lancer une ActionInconnueException"
        );
    }

    /***
     * test si le fichier n'a pas de personnage (P)
     */
    @Test
    public void test_pasPJ() {
        // utilise laby_pasPJ.txt
        Exception exception = assertThrows(
                FichierIncorrectException.class,
                () -> Labyrinthe.chargerLabyrinthe("laby/laby_pasPJ.txt")
        );

        String expectedMessage = "personnage inconnu";
        assertEquals(expectedMessage, exception.getMessage(), "devrait afficher : personnage inconnu");
    }

    /***
     * test si le fichier a plusieurs personnages (P)
     */
    @Test
    public void test_plusieursPJ() {
        // utilise laby_deuxPJ.txt
        Exception exception = assertThrows(
                FichierIncorrectException.class,
                () -> Labyrinthe.chargerLabyrinthe("laby/laby_deuxPJ.txt")
        );

        String expectedMessage = "plusieurs personnages";
        assertEquals(expectedMessage, exception.getMessage(), "devrait afficher : plusieurs personnages");
    }

    /***
     * test si le fichier comporte un mauvais caractère
     */
    @Test
    public void test_mauvaisCaractere() {
        // utilise laby_mauvaisChar.txt, un fichier avec un 'M' à l'interieur
        Exception exception = assertThrows(
                FichierIncorrectException.class,
                () -> Labyrinthe.chargerLabyrinthe("laby/laby_mauvaisChar.txt")
        );

        String expectedMessage = "caractère inconnu <M>";
        assertEquals(expectedMessage, exception.getMessage(), "devrait afficher : caractère inconnu <M>");
    }

    /***
     * test si un des nombres (lignes ou colonnes) n'est pas un entier
     */
    @Test
    public void test_pasEntier() {
        // utilise laby_pasEntier.txt, un fichier avec comme nombre de lignes le caracètre 'A'
        Exception exception = assertThrows(
                FichierIncorrectException.class,
                () -> Labyrinthe.chargerLabyrinthe("laby/laby_pasEntier.txt")
        );

        String expectedMessage = "pb num lignes ou colonnes";
        assertEquals(expectedMessage, exception.getMessage());
    }

    /***
     * test si les lignes ne correspondent pas avec le nombre attendu
     */
    @Test
    public void test_pbLignes() {
        // utilise laby_pbLignes.txt, un fichier avec 8 lignes attendues mais 7 lignes en réalité
        Exception exception = assertThrows(
                FichierIncorrectException.class,
                () -> Labyrinthe.chargerLabyrinthe("laby/laby_pbLignes.txt")
        );

        String expectedMessage = "nbLignes ne correspond pas";
        assertEquals(expectedMessage, exception.getMessage());
    }

    /***
     * test si les colonnes ne correspondent pas avec le nombre attendu
     */
    @Test
    public void test_pbColonnes() {
        // utilise laby_pbColonnes.txt, un fichier avec 11 colonnes attendues mais 10 colonnes en réalité
        Exception exception = assertThrows(
                FichierIncorrectException.class,
                () -> Labyrinthe.chargerLabyrinthe("laby/laby_pbColonnes.txt")
        );

        String expectedMessage = "nbColonnes ne correspond pas";
        assertEquals(expectedMessage, exception.getMessage());
    }

    /***
     * test si le laby.etreFini() renvoit vrai si le personnage est sur la sortie
     * @throws Exception possible FichierIncorrectException
     */
    @Test
    public void test_sortie() throws Exception {
        // utilise laby0.txt
        // fichier dans lequel il suffit d'aller une fois en haut et une fois à gauche pour arriver sur la sortie
        Labyrinthe l = Labyrinthe.chargerLabyrinthe("laby/laby0.txt");
        l.deplacerPerso(l.HAUT); // on va en haut
        l.deplacerPerso(l.GAUCHE); // on va à gauche

        assertTrue(l.etreFini(), "devrait etre fini");
    }
}
