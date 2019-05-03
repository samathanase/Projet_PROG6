package Modele;

/*
Classe pour représenter les coordonnées d'une case
*/

public class Coordonnees {
    int c;
    int l;
    public Coordonnees(int l,int c) {
        this.l = l;
        this.c = c;
    }

    public int ligne() {
        return l;
    }

    public int colonne() {
        return c;
    }

    //Mis à jour de ligne 
    public void l(int l) {
        this.l = l;
    }

    public void c(int c) {
        this.c = c;
    }

    //Change les coordonnées par l'opposé
    public void oppose() {
        l = -l;
        c = -c;
    }

    //Addition avec les coordonnées données
    public void plus(Coordonnees C) {
        c += C.c;
        l += C.l;
    }

    @Override
    //Retourne vrai si l'object coordonnees passé en paramètre est égal à l'objet
    public boolean equals(Object O) {
        Coordonnees C = (Coordonnees) O;
        return O!=null && this.l == C.l && this.c == C.c;
    }

    @Override
    public String toString() {
        String str = new String();
        str = l+" "+c;
        return str;
    } 

}