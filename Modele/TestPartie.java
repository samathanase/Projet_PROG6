package Modele;

import java.util.ArrayList;
import java.util.Random;

public class TestPartie {
    public static void main(String[] args) {


        testCasesAccessibles();
        testCasesAdjacentes();
        testCoupValide();
        testPercussion();
        testAspiration();


    }




    public static boolean testCasesAccessibles() {
        boolean b = true;
        ArrayList<Coordonnees> listC;
        Partie partie = new Partie();
        int [][] t = {
            {0,0,0,0,2,0,0,0,0},
            {0,0,0,2,1,2,0,0,0},
            {0,0,0,0,2,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        partie.tab = t;
        listC = partie.casesAccessibles(1,4);
        if(listC.size()!=0) { //Pas de cases accessibles
            b = false;
        }

        int [][] t1 = {
            {0,0,0,0,2,0,0,0,0},
            {0,0,0,2,1,2,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        partie.tab = t1;
        listC = partie.casesAccessibles(1,4);

        if(listC.size()!=1 || !listC.contains(new Coordonnees(2,4))) { //Pas de cases accessibles
            b = false;
            System.out.println(listC);
        }

        int [][] t2 = {
            {0,0,0,0,1,0,0,0,0},
            {0,0,0,1,1,1,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        partie.tab = t2;
        listC = partie.casesAccessibles(1,4);

        if(listC.size()!=1 || !listC.contains(new Coordonnees(2,4))) { //Pas de cases accessibles
            b = false;
            System.out.println(listC);
        }

        int [][] t3 = {
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        partie.tab = t3;
        listC = partie.casesAccessibles(2,2);

        if(listC.size()!=8 || !listC.contains(new Coordonnees(1,1)) || !listC.contains(new Coordonnees(1,2)) || !listC.contains(new Coordonnees(1,3)) || !listC.contains(new Coordonnees(2,1))
        || !listC.contains(new Coordonnees(2,3)) || !listC.contains(new Coordonnees(3,1)) || !listC.contains(new Coordonnees(3,2)) || !listC.contains(new Coordonnees(3,3) )) {
            b = false;
            System.out.println(listC);
        }

        if(!b) {
            System.out.println("Erreur casesAccessibles");
        }
        return b;
    }

    public static boolean testCasesAdjacentes() {
        ArrayList<Coordonnees> listC;
        boolean b = true;
        Partie partie = new Partie();
        int [][] t = {
            {1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        partie.tab = t;
        listC = partie.casesAdjacentes(0,0);
        if(listC.size()!=3 || !listC.contains(new Coordonnees(1,0)) || !listC.contains(new Coordonnees(1,0)) || !listC.contains(new Coordonnees(1,1))) {
            b = false;
            System.out.println(listC);
        }
        listC = partie.casesAdjacentes(2,2);
        if(listC.size()!=8 || !listC.contains(new Coordonnees(1,1)) || !listC.contains(new Coordonnees(1,2)) || !listC.contains(new Coordonnees(1,3)) || !listC.contains(new Coordonnees(2,1))
        || !listC.contains(new Coordonnees(2,3)) || !listC.contains(new Coordonnees(3,1)) || !listC.contains(new Coordonnees(3,2)) || !listC.contains(new Coordonnees(3,3) )) {
            b = false;
            System.out.println(listC);
        }
        listC = partie.casesAdjacentes(1,2);
        if(listC.size()!=4 || !listC.contains(new Coordonnees(0,2)) || !listC.contains(new Coordonnees(2,2)) || !listC.contains(new Coordonnees(1,1)) || !listC.contains(new Coordonnees(1,3))) {
            b = false;
            System.out.println(listC);
        }

        if(!b) {
            System.out.println("Erreur CasesAdjacentes");
        }

        return b;
    }

    public static boolean testCoupValide() {
        boolean b  = true;
        boolean coup = false;
        Partie partie = new Partie();
        int [][] t = {
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,1,0,0,0},
            {0,0,0,2,2,2,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        partie.joueur = 1;
        partie.tab = t;

        coup = partie.coupValide(1,4,1,5);
        if(coup==true) {
            b = false;
        }
        coup = partie.coupValide(1,4,1,3);
        if(coup==false) {
            b = false;
        }
        coup = partie.coupValide(1,4,2,4);
        if(coup==true) {
            b = false;
        }
        coup = partie.coupValide(1,4,1,2);
        if(coup==true) {
            b = false;
        }
        coup = partie.coupValide(1,1,1,2); //pas de pion
        if(coup==true) {
            b = false;
        }
        coup = partie.coupValide(2,3,3,3); //c'est un pion adverse
        if(coup==true) {
            b = false;
        }

        partie.joueur = 2;
        for(Coordonnees c: partie.casesAccessibles(2,4)) {
            if(!partie.coupValide(2,4,c.l,c.c)) {
                b = false;
            }
        }

        if(!b) {
            System.out.println("Erreur coupValide");
        }

        return b;
    }


    public static boolean testPercussion() {
        boolean b =true;
        ArrayList<Coordonnees> listC;
        Partie partie = new Partie();
        int [][] t = {
            {0,0,2,0,2,0,2,0,0},
            {0,0,0,0,0,0,0,0,0},
            {2,2,2,0,1,0,2,0,2},
            {0,0,0,0,0,0,0,0,0},
            {0,0,2,0,2,0,2,0,0},
        };
        partie.joueur = 1;
        partie.tab = t;

        listC = partie.pionsCapturablesPercussion(2,4,1,3);
        Coordonnees [] c1 = {new Coordonnees(0,2)};
        if(!comparerArray(listC,  c1)) {
            b = false;
        }

        listC = partie.pionsCapturablesPercussion(2,4,1,4);
        Coordonnees [] c2 = {new Coordonnees(0,4)};
        if(!comparerArray(listC,  c2)) {
            b = false;
        }

        listC = partie.pionsCapturablesPercussion(2,4,1,5);
        Coordonnees [] c3 = {new Coordonnees(0,6)};
        if(!comparerArray(listC,  c3)) {
            b = false;
        }

        listC = partie.pionsCapturablesPercussion(2,4,2,3);
        Coordonnees[] c4 = {new Coordonnees(2,2),new Coordonnees(2,1),new Coordonnees(2,0)};
        if(!comparerArray(listC,  c4)) {
            b = false;
        }

        listC = partie.pionsCapturablesPercussion(2,4,2,5);
        Coordonnees[] c5 = {new Coordonnees(2,6)};
        if(!comparerArray(listC,  c5)) {
            b = false;
        }

        listC = partie.pionsCapturablesPercussion(2,4,3,3);
        Coordonnees[] c6 = {new Coordonnees(4,2)};
        if(!comparerArray(listC,  c6)) {
            b = false;
        }

        listC = partie.pionsCapturablesPercussion(2,4,3,4);
        Coordonnees[] c7 = {new Coordonnees(4,4)};
        if(!comparerArray(listC, c7)) {
            b = false;
        }

        listC = partie.pionsCapturablesPercussion(2,4,3,5);
        Coordonnees[] c8 = {new Coordonnees(4,6)};
        if(!comparerArray(listC, c8)) {
            b = false;
        }


        if(!b) {
            System.out.println("Erreur percussion");
        }
        return b;
    }

    public static boolean testAspiration() {
        boolean b =true;
        ArrayList<Coordonnees> listC;
        Partie partie = new Partie();
        int [][] t = {
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,2,0,0,2},
            {0,0,0,2,2,2,0,0,0},
            {0,0,0,0,2,0,0,0,0},
        };
        partie.joueur = 1;
        partie.tab = t;

        listC = partie.pionsCapturablesAspiration(2,4,1,3);
        Coordonnees [] c1 = {new Coordonnees(3,5)};
        if(!comparerArray(listC,  c1)) {
            b = false;
        }

        listC = partie.pionsCapturablesAspiration(2,4,1,4);
        Coordonnees [] c2 = {new Coordonnees(3,4),new Coordonnees(3,4)};
        if(!comparerArray(listC,  c2)) {
            b = false;
        }

        listC = partie.pionsCapturablesAspiration(2,4,1,5);
        Coordonnees [] c3 = {new Coordonnees(3,3)};
        if(!comparerArray(listC,  c3)) {
            b = false;
        }

        listC = partie.pionsCapturablesAspiration(2,4,2,3);
        Coordonnees[] c4 = {new Coordonnees(2,5)};
        if(!comparerArray(listC,  c4)) {
            b = false;
        }

        if(!b) {
            System.out.println("Erreur aspiration");
        }
        return b;
    }






        //Compare la liste avec le tableau
    public static boolean comparerArray( ArrayList<Coordonnees> listC,Coordonnees [] arrayC) {
        boolean b = true;
        if(listC.size()!=arrayC.length) {
            b = false;
        }
        else {
            for(Coordonnees c : arrayC) {
                if(!listC.contains(c)) {
                    b = false;
                }
            }
        }
        return b;
    }

}