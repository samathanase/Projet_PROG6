package Modele;

import java.util.Scanner;

/*
Une version ultra basique en console
*/

public class FanoronaConsole {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Partie partie = new Partie();
        // int [][] t = {
        //     {1,2,1,1,1},
        //     {0,0,0,1,2},
        //     {2,2,1,2,2},

        // };
        partie.joueur = 1;
        // partie.tab = t;

        // Partie partie = new Partie();
        // int [][] t = {
        //     {0,0,0,0,0,0,0,0,0},
        //     {0,0,1,0,2,0,0,0,0},
        //     {0,0,0,0,0,0,0,0,0},
        //     {0,0,0,0,0,2,0,0,0},
        //     {0,0,0,0,0,0,0,0,0},
        // };
        // partie.joueur = 1;
        // partie.tab = t;


        int lPion,cPion,lDestination,cDestination,capture;
        Coup coup;
        partie.afficher();
        while(!partie.estFinie()) {
            if(partie.joueur1()) {
                System.out.println("Au joueur 1!");
            }

            else if(partie.joueur2()) {
                System.out.println("Au joueur 2!");
            }


            do { //Tant que le coup n'est pas valide
                lPion = scan.nextInt();
                cPion = scan.nextInt();
                lDestination = scan.nextInt();
                cDestination = scan.nextInt();
                coup = new Coup(lPion, cPion,lDestination,cDestination);
            } while(!partie.coupValide(coup));

            
            //Jouer le coup
            if(partie.aspirationPercution(coup)) {
                System.out.println("Percussion:1 , Aspiration:2");
                capture = scan.nextInt();
                coup.changerAction(capture);
                partie.jouer(coup);
            }
            else if(partie.aspiration(coup)) {
                coup.actionAspiration();
                partie.jouer(coup);
            }
            else if(partie.percussion(coup)) {
                coup.actionPercussion();
                partie.jouer(coup);
            }
            else {
                coup.actionPasCapture();
                partie.jouer(coup);
            }

            partie.afficher();
        }

        System.out.println("Le joueur "+partie.gagnant()+" a gagné!");

    }
}