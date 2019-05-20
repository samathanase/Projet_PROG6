package Modele;

import java.util.Scanner;

/*
Une version ultra basique en console
*/

public class FanoronaConsole {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Partie partie = new Partie();
        partie.joueur = 1;

        int [][] t = {
            {0,0,0,0,2,0,0,0,0},
            {0,0,0,2,1,2,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,2,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        partie.tab = t;


        String cmd;
        Commande cmd1 = new Commande(partie, 1);
        Commande cmd2 = new Commande(partie, 2);

        partie.afficher();
        while(!partie.estFinie()) {
            if(partie.joueur1()) {
                System.out.println("Au joueur 1!");
                do { //Tant que la commande n'est pas valide
                    System.out.print("Entrer une commande: ");
                    cmd = scan.nextLine(); //Récupère la commande de l'utilisateur
                    cmd1.interpreter(cmd);

                } while(!cmd1.estValide());
                cmd1.executer();
            }

            else if(partie.joueur2()) {
                System.out.println("Au joueur 2!");
                do { //Tant que la commande n'est pas valide
                    System.out.print("Entrer une commande: ");
                    cmd = scan.nextLine(); //Récupère la commande de l'utilisateur
                    cmd2.interpreter(cmd);      
                } while(!cmd2.estValide());
                cmd2.executer();
            }

        }

        System.out.println("Le joueur "+partie.gagnant()+" a gagné!");
        scan.close();
    }
}