package Modele;

import java.util.ArrayList;

public class TestPartie {
    public static void main(String[] args) {
        Partie partie = new Partie();

        // int [][] t = {
        //     {0,0,0,0,0,0,0,0,0},
        //     {0,0,0,0,0,0,0,0,0},
        //     {0,0,0,0,0,0,0,0,0},
        //     {0,0,0,0,0,0,0,0,0},
        //     {0,0,0,0,0,0,0,0,0},
        // };
        // partie.tab = t;

        partie.afficher();

        partie.joueur = 1;
        partie.jouer(1,4,2,4,1);

        // ArrayList<Coordonnees> A = new ArrayList<Coordonnees>();
        // Coordonnees C1 = new Coordonnees(2,4);
        // A.add(C1);
        // Coordonnees C2 = new Coordonnees(2,4);
        // System.out.println(A.indexOf(C2));
        // System.out.println(C1.equals(C2));

        ArrayList<Integer> A = new ArrayList<Coordonnees>();
        Coordonnees C1 = new Coordonnees(2,4);
        A.add(C1);
        Coordonnees C2 = new Coordonnees(2,4);
        System.out.println(A.indexOf(C2));
        System.out.println(C1.equals(C2));
        

        partie.afficher();
    }
}