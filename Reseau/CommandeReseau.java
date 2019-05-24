
package Reseau;


import Modele.*;
/*
    Interprète une chaine de caractères écrite par l'utilisateur
    Les commandes sont pour le réseau
*/





public class CommandeReseau extends Commande {

    Communication communication;

    //On lui passe la partie et le joueur concerné
    public CommandeReseau(Partie partie,int joueur,Communication communication) {
        super(partie, joueur);
        this.communication = communication;
    }

    
    @Override
    public void interpreterAide() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Liste des commandes importantes:");
        System.out.println("Jouer un coup: les coordonnées de départ et d'arriver du pion. S'il y a un choix entre de capture ajouter 1 pour faire une percussion et 2 pour une aspiration");
        System.out.println("Mettre fin au tour: fin ou finTour");
        System.out.println("Pour annuler un coup: annuler (seulement si c'est votre tour)");
        System.out.println("Pour refaire un coup: refaire (seulement si c'est votre tour)");
        System.out.println("Pour quitter la partie: quitter (l'adversaire est aussi déconnecté)");
        System.out.println("Pour recommencer la partie: recommencer");
        System.out.println("Pour naviguer dans l'historique: b pour revenir en arrière et n pour aller un coup en avant");

        System.out.println("\nListe des autre commandes : ");
        System.out.println("Pour avoir la liste des coups possibles: coups");
        System.out.println("Pour envoyer un message un message: chat le message");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    @Override
    public void interpreterQuitter() {
        ActionReseau actionReseau = new ActionReseau(9);
        communication.envoyer(actionReseau);
        System.exit(1);
    }

    @Override
    public void interpreterRecommencer() {
        typeCmd = 9;
        valide = true;
        partie.recommencer();
        ActionReseau actionReseau = new ActionReseau(8); //recommence la partie
        communication.envoyer(actionReseau);
        ActionReseau actionReseauJoueur = new ActionReseau(partie.joueur()); //Indique qui commence
        communication.envoyer(actionReseauJoueur);
        partie.afficher();
        if(partie.joueur()==joueur)
            System.out.println("C'est à vous de commencer!");
        else
            System.out.println("C'est à l'adversaire de commencer!");

    }

    @Override
    public void interpreterFinTour() {
        if(!peutJouer()) {
            System.out.println("Ce n'est pas à votre tour!");
        }
        else if (partie.peutFinTour()){
            System.out.println("Vous ne pouvez pas mettre fin au tour");
        }
        else{
            super.interpreterFinTour();
            ActionReseau actionReseau = new ActionReseau(6);
            communication.envoyer(actionReseau);
            System.out.println("C'est au tour de l'adversaire!");
        }

    }


    //Il faut pour chaque commande l'exécuter pour l'utilisateur et envoyer la commande à l'adversaire
    @Override
    public void interpreterAnnuler() {
        if (peutJouer() && partie.peutAnnuler(joueur)) {
            super.interpreterAnnuler();
            ActionReseau actionReseau = new ActionReseau(4);
            communication.envoyer(actionReseau);
        }
        else {
            System.out.println("Vous ne pouvez pas annuler!");
        }
    }

    @Override
    public void interpreterRefaire() {
        if (peutJouer() && partie.peutRefaire()) {
            super.interpreterRefaire();
            ActionReseau actionReseau = new ActionReseau(5);
            communication.envoyer(actionReseau);
        }
        else {
            System.out.println("Vous ne pouvez pas refaire!");
        }
    }

    @Override
    public Coup interpreterJouerCoup(int l,int c,int lD,int cD,int action) {
        if(peutJouer()) { //Si c'est au tour du joueur
            Coup coup =super.interpreterJouerCoup(l, c, lD, cD, action);
            if(coup!=null) {
                ActionReseau actionReseau = new ActionReseau(3);
                actionReseau.coup = coup;
                communication.envoyer(actionReseau); //Envoye à l'adversaire le coup joué
                if(partie.estFinie()) {
                    System.out.println("Le joueur "+partie.gagnant()+" a gagné!");
                    System.out.println("Vous pouvez quitter (taper quitter) le jeu ou recommencer (taper recommencer) une partie!");
                }
            }
            return coup;
        }
        else {
            System.out.println("Ce n'est pas à votre tour");
            return null;
        }
    }

    @Override
    public void interpreterChat(String str) {
        if(str.length()!=0) {
            typeCmd = 20;
            valide = true;
            ActionReseau actionReseau = new ActionReseau(7);
            actionReseau.message = str ;
            communication.envoyer(actionReseau); //Envoye à l'adversaire le message
        }
    }

    @Override
    public void interpreterJoueur() {
        typeCmd = 18;
        if(joueur==partie.joueur())
            System.out.println("C'est à votre tour de jouer!");
        else
            System.out.println("C'est à l'adversaire de jouer!");
        valide = true;
    }

    @Override
    public void interpreterCharger(String nomFichier) {
        typeCmd = 12;
        Partie partieChargee;
        ChargerPartie charger;
        charger = new ChargerPartie(nomFichier);
        partieChargee = charger.charger();
        if (partieChargee==null) {
            System.out.println("Impossible de charger la partie");
        }
        else {
            System.out.println("Partie chargée avec succés");
            partie.changer(partieChargee); //On change la partie courante avec la partie chargée
            partie.afficher();
            System.out.println(joueur==partie.joueur ? "C'est à vous de commencer" : "C'est à l'adversaire de commencer");                
            ActionReseau actionReseau = new ActionReseau(10);
            actionReseau.partie = partie ;
            communication.envoyer(actionReseau); //Envoye à l'adversaire la partie chargée
        }    
        valide = true;

    }

    public boolean peutJouer() {
        return joueur == partie.joueur();
    }


}