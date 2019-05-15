package Modele;

import java.util.ArrayList;
import java.util.Random;

public class TestPartie {
    static boolean verbeux;
    public static void main(String[] args) {
        if(args.length > 0) {
            verbeux = true;
        }
        else {
            verbeux = false;
        }

        testCasesAccessibles();
        testCasesAdjacentes();
        testCoupValide();
        testPercussion();
        testAspiration();
        testJouerCoupsMultiples();
        testJouer();
        partie35();
        testJouerChaineInutile();
        testListeCoupsValides();

        testAnnuler();
        testRefaire();
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

        coup = partie.coupValide(new Coup(1,4,1,5));
        if(coup==true) {
            b = false;
        }
        coup = partie.coupValide(new Coup(1,4,1,3));
        if(coup==false) {
            b = false;
        }
        coup = partie.coupValide(new Coup(1,4,2,4));
        if(coup==true) {
            b = false;
        }
        coup = partie.coupValide(new Coup(1,4,1,2));
        if(coup==true) {
            b = false;
        }
        coup = partie.coupValide(new Coup(1,1,1,2)); //pas de pion
        if(coup==true) {
            b = false;
        }
        coup = partie.coupValide(new Coup(2,3,3,3)); //c'est un pion adverse
        if(coup==true) {
            b = false;
        }

        partie.joueur = 2;
        for(Coordonnees c: partie.casesAccessibles(2,4)) {
            if(!partie.coupValide(new Coup(2,4,c.l,c.c))) {
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

        listC = partie.pionsCapturablesPercussion(new Coup(2,4,1,3));
        Coordonnees [] c1 = {new Coordonnees(0,2)};
        if(!comparerListArray(listC,  c1)) {
            b = false;
            if (verbeux) System.out.println("Erreur aspi: "+listC +"  "+c1 );
        }

        listC = partie.pionsCapturablesPercussion(new Coup(2,4,1,4));
        Coordonnees [] c2 = {new Coordonnees(0,4)};
        if(!comparerListArray(listC,  c2)) {
            b = false;
            if (verbeux) System.out.println("Erreur aspi: "+listC +"  "+c2 );

        }

        listC = partie.pionsCapturablesPercussion(new Coup(2,4,1,5));
        Coordonnees [] c3 = {new Coordonnees(0,6)};
        if(!comparerListArray(listC,  c3)) {
            b = false;
            if (verbeux) System.out.println("Erreur aspi: "+listC +"  "+c3 );

        }

        listC = partie.pionsCapturablesPercussion(new Coup(2,4,2,3));
        Coordonnees[] c4 = {new Coordonnees(2,2),new Coordonnees(2,1),new Coordonnees(2,0)};
        if(!comparerListArray(listC,  c4)) {
            b = false;
            if (verbeux) System.out.println("Erreur aspi: "+listC +"  "+c4 );

        }

        listC = partie.pionsCapturablesPercussion(new Coup(2,4,2,5));
        Coordonnees[] c5 = {new Coordonnees(2,6)};
        if(!comparerListArray(listC,  c5)) {
            b = false;
            if (verbeux) System.out.println("Erreur aspi: "+listC +"  "+c5 );

        }

        listC = partie.pionsCapturablesPercussion(new Coup(2,4,3,3));
        Coordonnees[] c6 = {new Coordonnees(4,2)};
        if(!comparerListArray(listC,  c6)) {
            b = false;
            if (verbeux) System.out.println("Erreur aspi: "+listC +"  "+c6 );

        }

        listC = partie.pionsCapturablesPercussion(new Coup(2,4,3,4));
        Coordonnees[] c7 = {new Coordonnees(4,4)};
        if(!comparerListArray(listC, c7)) {
            b = false;
            if (verbeux) System.out.println("Erreur aspi: "+listC +"  "+c7 );

        }

        listC = partie.pionsCapturablesPercussion(new Coup(2,4,3,5));
        Coordonnees[] c8 = {new Coordonnees(4,6)};
        if(!comparerListArray(listC, c8)) {
            b = false;
            if (verbeux) System.out.println("Erreur aspi: "+listC +"  "+c8 );

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

        listC = partie.pionsCapturablesAspiration(new Coup(2,4,1,3));
        Coordonnees [] c1 = {new Coordonnees(3,5)};
        if(!comparerListArray(listC,  c1)) {
            b = false;
        }

        listC = partie.pionsCapturablesAspiration(new Coup(2,4,1,4));
        Coordonnees [] c2 = {new Coordonnees(3,4),new Coordonnees(3,4)};
        if(!comparerListArray(listC,  c2)) {
            b = false;
        }

        listC = partie.pionsCapturablesAspiration(new Coup(2,4,1,5));
        Coordonnees [] c3 = {new Coordonnees(3,3)};
        if(!comparerListArray(listC,  c3)) {
            b = false;
        }

        listC = partie.pionsCapturablesAspiration(new Coup(2,4,2,3));
        Coordonnees[] c4 = {new Coordonnees(2,5)};
        if(!comparerListArray(listC,  c4)) {
            b = false;
        }

        if(!b) {
            System.out.println("Erreur aspiration");
        }
        return b;
    }


    public static boolean testJouerCoupsMultiples() {
        boolean b =true;
        ArrayList<Coordonnees> listC;
        Partie partie = new Partie();
        int [][] t = {
            {0,0,0,0,0,0,1,0,0},
            {0,0,0,1,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,2,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        partie.joueur = 2;
        partie.tab = t;

        //Plusieurs coups dans le même tour

        partie.jouer(new Coup(3,4,2,4,1));
        if(partie.joueur1()) {
            b = false;
            if (verbeux) System.out.println("Erreur changement joueur:c'est au j2");
        }
        partie.jouer(new Coup(2,4,1,5,1));
        if(partie.joueur1()) {
            b = false;
            if (verbeux) System.out.println("Erreur changement joueur: c'est au j2");
        }
        partie.jouer(new Coup(1,5,1,4,1));
        if(!partie.estFinie()) {
            b = false;
            if (verbeux) System.out.println("Erreur partie est finie");
        }
        int [][] tR = {
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,2,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        if(!comparerTab(partie.tab,tR)) {
            b = false;
            if (verbeux) System.out.println("Erreur terrain,tR");
        }

        //
        




        if(!b) {
            System.out.println("Erreur testJouerCoupsMultiples");
        }
        return b;
    }

    public static boolean testJouerChaineInutile() {
        boolean b = true;
        Partie partie = new Partie();
        int [][] t = {
            {0,0,0,0,0,0,0,0,0},
            {0,0,1,0,2,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,2,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        partie.joueur = 1;
        partie.tab = t;
        partie.jouer(new Coup(1,2,1,3,1));
        partie.jouer(new Coup(1,3,0,3,0)); //Coup impossible

        int [][] tR = {
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,2,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        if(!comparerTab(partie.tab,tR)) {
            b = false;
        }

        if(!b) {
            System.out.println("Erreur testJouerChaineInutile");
        }
        return b;
    }


    public static boolean testJouer() {
        boolean b =true;
        ArrayList<Coordonnees> listC;
        Partie partie = new Partie();
        int [][] t = {
            {1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1},
            {1,2,1,2,0,1,2,1,2},
            {2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2},
        };
        partie.joueur = 1;
        partie.tab = t;


        partie.jouer(new Coup(1,4,2,4,1));
        partie.jouer(new Coup(4,3,4,4,0));

        int [][] tR = {
            {1,1,1,1,1,1,1,1,1},
            {1,1,1,1,0,1,1,1,1},
            {1,2,1,2,1,1,2,1,2},
            {2,2,2,2,0,2,2,2,2},
            {2,2,2,0,2,2,2,2,2},
        };
        if(!comparerTab(partie.tab,tR)) {
            b = false;
        }

        if(!b) {
            System.out.println("Erreur testJouer");
        }
        return b;
    }


    //test partie avex taille 3x5
    public static boolean partie35() {
        boolean b =true;
        ArrayList<Coordonnees> listC;
        Partie partie = new Partie(3,5);
        int [][] t = {
            {1,2,1,1,1},
            {0,0,0,1,2},
            {2,2,1,2,2},

        };
        partie.joueur = 2;
        partie.tab = t;
        partie.jouer(new Coup(2,0,1,0,1));

        int [][] tR = {
            {0,2,1,1,1},
            {2,0,0,1,2},
            {0,2,1,2,2},
        };
        if(!comparerTab(partie.tab,tR)) {
            b = false;
            if (verbeux) System.out.println("Erreur terrain,tR");
        }


        if(!b) {
            System.out.println("Erreur partie35");
        }
        return b;
    }


    public static boolean testListeCoupsValides() {
        boolean b =true;
        ArrayList<Coup> listC;
        Partie partie = new Partie();
        int [][] t = {
            {0,1,2,0,2,0,0,2,0},
            {0,0,0,1,2,0,0,0,0},
            {0,0,2,0,2,0,2,1,2},
            {0,0,0,2,0,0,0,2,0},
            {0,0,0,0,0,0,0,2,0},
        };
        partie.joueur = 1;
        partie.tab = t;

        listC = partie.listeCoupsValides();
        Coup [] cR = {
            new Coup(1,3,0,3,0),
            new Coup(1,3,1,2,2),
            new Coup(1,3,2,3,1),
            new Coup(0,1,0,0,2),
            new Coup(0,1,1,1,0),
            new Coup(2,7,1,7,1),
            new Coup(2,7,1,7,2),
        };

        if(!comparerListArray(listC,cR)) {
            System.out.println(listC);
            b= false;
        }


        //Avec chaine
        int [][] t2 = {
            {0,0,0,2,2,0,0,0,0},
            {0,0,1,0,2,0,0,0,0},
            {0,0,2,2,0,0,0,0,0},
            {0,0,0,0,0,2,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        partie.joueur = 1;
        partie.tab = t2;
        partie.jouer(new Coup(1,2,1,3,1));

        listC = partie.listeCoupsValides();
        Coup [] cR2 = {
            new Coup(1,3,2,4,1),
        };

        if(!comparerListArray(listC,cR2)) {
            System.out.println(listC);
            b= false;
        }

        if(!b) {
            System.out.println("Erreur testListeCoupsValides");
        }
        return b;
    }


    public static boolean testAnnuler() {
        System.out.println("testAnnuler");

        boolean b = true;
        Partie partie = new Partie();
        int [][] t = {
            {0,0,0,0,0,0,0,0,0},
            {0,2,1,0,2,2,0,1,0},
            {0,0,0,0,0,0,0,0,0},
            {0,2,0,0,0,2,0,0,0},
            {0,0,0,0,0,0,2,0,0},
        };
        int [][] tR = {
            {0,0,0,0,0,0,0,0,0},
            {0,2,1,0,2,2,0,1,0},
            {0,0,0,0,0,0,0,0,0},
            {0,2,0,0,0,2,0,0,0},
            {0,0,0,0,0,0,2,0,0},
        };
        partie.joueur = 1;
        partie.tab = t;
        for(Coup c:partie.listeCoupsValides()) {
            partie.jouer(c);
            partie.annuler();
            if(!comparerTab(tR, partie.tab)) {
                b = false;
            }
        }

        partie.jouer(new Coup(1,2,1,3,1));
        partie.jouer(new Coup(1,3,2,4,1));
        partie.jouer(new Coup(3,1,2,1,0));

        partie.annuler();
        partie.annuler();
        partie.annuler();
        if(!comparerTab(tR, partie.tab)) {
            b = false;
        }

        if(!b) {
            System.out.println("Erreur annuler");
        }
        return b;
    }



    public static boolean testRefaire() {
        System.out.println("testRefaire");

        boolean b = true;
        Partie partie = new Partie();
        int [][] t = {
            {0,0,0,0,0,0,0,0,0},
            {0,2,1,0,2,2,0,1,0},
            {0,0,0,0,0,0,0,0,0},
            {0,2,0,0,0,2,0,0,0},
            {0,0,0,0,0,0,2,0,0},
        };
        int [][] tR = {
            {0,0,0,0,0,0,0,0,0},
            {0,2,1,0,2,2,0,1,0},
            {0,0,0,0,0,0,0,0,0},
            {0,2,0,0,0,2,0,0,0},
            {0,0,0,0,0,0,2,0,0},
        };
        partie.joueur = 1;
        partie.tab = t;

        for(Coup c:partie.listeCoupsValides()) {
            partie.jouer(c);
            partie.annuler();
            partie.refaire();
            partie.annuler();
            if(!comparerTab(tR, partie.tab)) {
                b = false;
            }
        }


        if(!b) {
            System.out.println("Erreur refaire");
        }
        return b;
    }




    // Compare 2 tableaux
    public static boolean comparerTab(int[][] A,int [][] B) {
        boolean b = true;
        if(A.length!=B.length || (A.length>0 && A[0].length!=B[0].length)) {
            b = false;
        }
        else {
            for(int i=0;i< A.length;i++) {
                for(int j=0;j< A[0].length;j++) {
                    if(A[i][j]!=B[i][j]) {
                        b= false;
                    }
                }
            }
        }
        return b;
    }


    //Compare la liste avec le tableau
    public static boolean comparerListArray( ArrayList<Coordonnees> listC,Coordonnees [] arrayC) {
        boolean b = true;
        if(listC.size()!=arrayC.length) {
            b = false;
        }
        else {
            for(Object c : arrayC) {
                if(!listC.contains(c)) {
                    b = false;
                }
            }
        }
        return b;
    }
    //Compare la liste avec le tableau
    public static boolean comparerListArray( ArrayList<Coup> listC,Coup [] arrayC) {
        boolean b = true;
        if(listC.size()!=arrayC.length) {
            b = false;
        }
        else {
            for(Object c : arrayC) {
                if(!listC.contains(c)) {
                    b = false;
                }
            }
        }
        return b;
    }

    public static void aff(int [][]t) {
        for(int i=0;i<5;i++) {
            for(int j=0;j<9;j++) {
                System.out.print(t[i][j]);
            }
            System.out.println();
        }
    }

}