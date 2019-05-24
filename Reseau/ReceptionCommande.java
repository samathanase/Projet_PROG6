package Reseau;


import Modele.Partie;


//Utiliser dans un thread pour récupèrer les commandes de l'adversaire
public class ReceptionCommande implements Runnable {
    Communication communication;
    int joueur;
    Partie partie;

    public ReceptionCommande(Communication communication,Partie partie,int joueur) {
        this.communication = communication;
        this.joueur = joueur;
        this.partie = partie;
    }


    @Override
    public void run() {
        ActionReseau action;
        while(true) {
            action = communication.recevoir();//Récupère l'action qu'a fait l'adversaire

            //On fait l'action adéquat
            if(action.joueur()) {
                partie.joueur = action.typeCommande; //Récupère le joueur qui commence
                System.out.println(joueur==partie.joueur ? "C'est à vous de commencer" : "C'est à l'adversaire de commencer");                
                
            }
            else if(action.coup()!=null) {
                partie.jouerSansFinTour(action.coup());
                partie.afficher();
                if(partie.estFinie()) {
                    System.out.println("Le joueur "+partie.gagnant()+" a gagné!");
                    System.out.println("Vous pouvez quitter (taper quitter) le jeu ou recommencer (taper recommencer) une partie!");
                }
            }
            else if(action.annuler()) {
                partie.annuler();
                partie.afficher();
            }
            else if(action.refaire()) {
                partie.refaire();
                partie.afficher();
            }
            else if(action.finTour()) {
                partie.finTour(); //Fin du tour pour l'adversaire
                System.out.println("C'est à votre tour!");
            }
            else if(action.message()) {
                System.out.println("\""+action.message+"\"");                
            }
            else if(action.recommencer()) {
                partie.recommencer();
                System.out.println("Partie recommencée!");
                partie.afficher();
            }

            else if(action.partie()) { //Chargement d'une partie
                partie.changer(action.partie);
                System.out.println("Partie chargée!");
                partie.afficher();
                System.out.println(joueur==partie.joueur ? "C'est à vous de commencer" : "C'est à l'adversaire de commencer");                
            }

            else if(action.quitter()) {
                System.out.println("L'adversaire a quitté!");
                System.exit(1);
            }

        }
    }
}