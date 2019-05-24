package Reseau;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;
import Modele.Partie;

//Utilisé dans un thread pour lire les commandes

public class LectureCommande implements Runnable {
    Partie partie;
    Scanner scan;
    CommandeReseau cmd;

    // Besoin de la partie, d'un scanner pour lire les commande, et de
    // l'interpréteur de commandeReseau
    public LectureCommande(Partie partie, Scanner scan, CommandeReseau cmd) {
        this.partie = partie;
        this.scan = scan;
        this.cmd = cmd;

    }

    @Override
    public void run() { // lance la lecture et l'interprétation des commandes
        String str;
        while(true) {
            do { //Tant que la commande n'est pas valide
                str = scan.nextLine(); //Récupère la commande de l'utilisateur
                cmd.interpreter(str);  //L'interprète
            } while(!cmd.estValide());
        }
    }
}