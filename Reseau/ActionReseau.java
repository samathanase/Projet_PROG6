package Reseau;

import java.io.Serializable;
import Modele.Coup;
import Modele.Partie;;

//Les actions envoyés par les joueurs

public class ActionReseau implements Serializable{
    private static final long serialVersionUID = 6803544947918720615L;
    
    // 1-2: quel joueur commence
    // 3: coup joué
    // 4: annuler
    // 5: refaire
    // 6: fin tour
    // 7: message
    // 8: recommencer
    // 9: quitter
    // 10: la partie envoyé par l'adversaire (quand on en charge une)
    int typeCommande;

    Boolean reponse;
    Coup coup; //Le coup joué
    String message;
    Partie partie;


    public ActionReseau(int typeCommande) {
        coup = null; message = null; partie =null;
        this.typeCommande = typeCommande;
    }


    public Coup coup() {
        return coup;
    }

    public boolean annuler() {
        return typeCommande == 4;
    }

    public boolean refaire() {
        return typeCommande == 5;
    }

    public boolean finTour() {
        return typeCommande == 6;
    }

    public boolean message() {
        return typeCommande == 7;
    }

    public boolean recommencer() {
        return typeCommande == 8;
    }

    public boolean quitter() {
        return typeCommande == 9;
    }

    public boolean partie() {
        return typeCommande == 10;
    }

    public boolean joueur () {
        return typeCommande==1 || typeCommande==2;
    }
}