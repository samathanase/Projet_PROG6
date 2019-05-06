package Modele;

import java.util.Scanner;

/*
Une version ultra basique en console
*/

public class FanoronaConsole {
    public static void main(String[] args) {
        Partie partie = new Partie(3,5);
        Scanner scan = new Scanner(System.in);

        
        int [][] t = {
            {1,2,1,1,1},
            {0,0,0,1,2},
            {2,2,1,2,2},

        };
        partie.joueur = 2;
        partie.tab = t;


        int lPion,cPion,lDestination,cDestination,capture;
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
            } while(!partie.coupValide(lPion,cPion,lDestination,cDestination));

            
            //Jouer le coup
            if(partie.aspirationPercution(lPion,cPion,lDestination,cDestination)) {
                System.out.println("Percussion:1 , Aspiration:2");
                capture = scan.nextInt();
                partie.jouer(lPion,cPion,lDestination,cDestination,capture);
            }
            else if(partie.aspiration(lPion,cPion,lDestination,cDestination)) {
                partie.jouer(lPion,cPion,lDestination,cDestination,2);
            }
            else if(partie.percussion(lPion,cPion,lDestination,cDestination)) {
                partie.jouer(lPion,cPion,lDestination,cDestination,1);
            }
            else {
                partie.jouer(lPion,cPion,lDestination,cDestination,0);
            }

            partie.afficher();
        }

        System.out.println("Le joueur "+partie.gagnant()+" a gagné!");

    }
}