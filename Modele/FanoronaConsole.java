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

        String cmd;
        CommandeSimple cmd1 = new CommandeSimple(partie, 1);
        CommandeSimple cmd2 = new CommandeSimple(partie, 2);

        partie.afficher();

        while(true) {
            if(partie.estFinie()) {
                System.out.println("Le joueur "+partie.gagnant()+" a gagné!");
                System.out.println("Vous pouvez recommencer une partie en tapant recommencer ou quitter!");
            }
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
    }
}
