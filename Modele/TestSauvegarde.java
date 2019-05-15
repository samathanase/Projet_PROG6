package Modele;

public class TestSauvegarde {
    public static void main(String[] args) {
        Partie partie = new Partie();
        Partie partieCharge = null;

        partie.joueur = 1;
        partie.jouer(new Coup(1,4,2,4,1));

        SauvegarderPartie save = new SauvegarderPartie(partie, "testSave.txt");
        save.sauvegarder();
        ChargerPartie load = new ChargerPartie("testSave.txt");
        partieCharge = load.charger();
        partieCharge.afficher();
        partieCharge.jouer(new Coup(3,3,3,4,0));
        partieCharge.afficher();


        // SauvegarderPartie save = new SauvegarderPartie(partie);
    }
}