package Modele;

/*
Classe pour représenter les coups
*/

public class Coup {
    Coordonnees depart;
    Coordonnees arrivee;
    public Coup(Coordonnees d,Coordonnees a) {
        depart = d;
        arrivee = a;
    }

    public Coordonnees depart() {
        return depart;
    }

    public Coordonnees arrivee() {
        return arrivee;
    }


    //Retourne vrai si l'object coordonnees passé en paramètre est égal à l'objet
    public boolean equals(Coup C) {
        return depart.equals(C.depart) && arrivee.equals(C.arrivee);
    }

}