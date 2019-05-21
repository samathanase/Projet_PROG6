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


        Grille g = new Grille(5, 9);
        int [][] t = {
            {0,0,0,0,2,0,0,0,0},
            {0,0,0,2,0,2,0,0,0},
            {0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
        };
        g.tab = t;
        partie.setGrille(g);
        partie.tab = t;

        String cmd;
        CommandeSimple cmd1 = new CommandeSimple(partie, 1);
        CommandeSimple cmd2 = new CommandeSimple(partie, 2);

        partie.afficher();
        cmd1.interpreter("2 4 3 4");
        cmd1.interpreter("fin");
        cmd2.interpreter("1 3 1 2");
        cmd2.interpreter("fin");
        cmd1.interpreter("3 4 3 5");
        cmd1.interpreter("fin");
        cmd2.interpreter("1 2 1 1");
        cmd2.interpreter("fin");

        while(!partie.estFinie()) {
            if(partie.joueur1()) {
                System.out.println("Au joueur 1!");
                do { //Tant que la commande n'est pas valide
                    System.out.print("Entrer une commande: ");
                    cmd = scan.nextLine(); //Récupère la commande de l'utilisateur
                    cmd1.interpreter(cmd); //L'interprète

                } while(!cmd1.estValide());
            }

            else if(partie.joueur2()) {
                System.out.println("Au joueur 2!");
                do { //Tant que la commande n'est pas valide
                    System.out.print("Entrer une commande: ");
                    cmd = scan.nextLine(); //Récupère la commande de l'utilisateur
                    cmd2.interpreter(cmd);      
                } while(!cmd2.estValide());
            }
        }

        System.out.println("Le joueur "+partie.gagnant()+" a gagné!");
        scan.close();
    }
}