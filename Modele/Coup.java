package Modele;

import java.io.Serializable;

/*
Classe pour représenter les coups
*/

public class Coup implements Serializable{
    private static final long serialVersionUID = 324916122093584715L;

    Coordonnees depart;
    Coordonnees arrivee;
    int action;
    Direction dir;
    //Action: -1:inconnu(la partie essayera de déterminer l'action) ,0:pas de capture, 1:percussion, 2:aspiration
    public Coup(Coordonnees d,Coordonnees a, int action) {
        depart = d;
        arrivee = a;
        this.action = action;
        directionCoup(); //Calcule la direction du coup
    }
    public Coup(Coordonnees d,Coordonnees a) {
        depart = d;
        arrivee = a;
        action = -1; //Direction inconnue
        directionCoup(); //Calcule la direction du coup
    }
    public Coup(int lPion,int cPion,int lDest,int cDest,int action) {
        depart = new Coordonnees(lPion, cPion);
        arrivee = new Coordonnees(lDest, cDest);
        this.action = action;
        directionCoup(); //Calcule la direction du coup
    }
    public Coup(int lPion,int cPion,int lDest,int cDest) {
        depart = new Coordonnees(lPion, cPion);
        arrivee = new Coordonnees(lDest, cDest);
        action = -1; //Direction inconnue
        directionCoup(); //Calcule la direction du coup
    }

    public Coup(Coup C) { //Copie le coup
        depart = new Coordonnees(C.pion());
        arrivee = new Coordonnees(C.destination());
        action = C.action();//Direction inconnue
        directionCoup(); //Calcule la direction du coup
    }

    //Retourne le pion d'origine
    public Coordonnees pion() {
        return depart;
    }

    //Retourne le pion de destination
    public Coordonnees destination() {
        return arrivee;
    }

    //Change l'action
    public void changerAction (int a) {
        action = a;
    }
    //Change l'action en percussion
    public void actionPercussion () {
        action = 1;
    }
    public void actionAspiration () {
        action = 2;
    }
    public void actionPasCapture () {
        action = 0;
    }

    //Retourne l'action
    public int action() {
        return this.action;
    }

    //Si le coup ne capture pas de pions
    public boolean pasCapture() {
        return action == 0;
    }
    public boolean percussion() {
        return action == 1;
    }
    public boolean aspiration() {
        return action == 2;
    }
    public boolean inconnu() {
        return action == -1;
    }

    //Calcule la direction du coup
    private void directionCoup() {
        int lPion = pion().ligne();
        int cPion = pion().colonne();
        int lDestination = destination().ligne(); 
        int cDestination = destination().colonne();
        dir = new Direction(EnumDirection.Inconnue);
        if(lPion-1==lDestination && cPion-1==cDestination) {
            dir.changerDirection(EnumDirection.HautGauche);
        }
        else if(lPion-1==lDestination && cPion==cDestination) {
            dir.changerDirection(EnumDirection.Haut);
        }
        else if(lPion-1==lDestination && cPion+1==cDestination) {
            dir.changerDirection(EnumDirection.HautDroite);
        }
        else if(lPion==lDestination && cPion-1==cDestination) {
            dir.changerDirection(EnumDirection.Gauche);
        }
        else if(lPion==lDestination && cPion+1==cDestination) {
            dir.changerDirection(EnumDirection.Droite);
        }
        else if(lPion+1==lDestination && cPion-1==cDestination) {
            dir.changerDirection(EnumDirection.BasGauche);
        }
        else if(lPion+1==lDestination && cPion==cDestination) {
            dir.changerDirection(EnumDirection.Bas);
        }
        else if(lPion+1==lDestination && cPion+1==cDestination) {
            dir.changerDirection(EnumDirection.BasDroite);
        }
    }

    //Retourne la direction
    public Direction direction() {
        return dir;
    }

    @Override
    public String toString() {
        return pion().ligne()+","+pion().colonne()+"->"+destination().ligne()+","+destination().colonne();
    }

    //Retourne vrai si l'object coordonnees passé en paramètre est égal à l'objet
    @Override
    public boolean equals(Object O) {
        Coup C = (Coup)O;
        return depart.equals(C.depart) && arrivee.equals(C.arrivee) && action==C.action;
    }

}