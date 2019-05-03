package Modele;

import java.util.Scanner;

public class FanoronaConsole {
    public static void main(String[] args) {
        Partie partie = new Partie();
        Scanner scan = new Scanner(System.in);

        int fin;
        int lPion,cPion,lDestination,cDestination,capture;
        partie.afficher();
        while((fin=partie.estFinie())==0) {
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

            }
            else if(partie.aspiration(lPion,cPion,lDestination,cDestination)) {
                partie.jouer(lPion,cPion,lDestination,cDestination,2);
            }
            else if(partie.percussion(lPion,cPion,lDestination,cDestination)) {
                partie.jouer(lPion,cPion,lDestination,cDestination,1);
            }

            partie.afficher();
        }

        System.out.println("Le joueur "+fin+" a gagné!");

    }
}