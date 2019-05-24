package Reseau;
import java.util.Scanner;


import Modele.*;

    // Joueur: client est le joueur 2, l'hote est le joueur 1


public class TestReseauClient {
    public static void main(String args[]) {
        String ip;
        String portS; int port=49153;

        Scanner scan = new Scanner(System.in); //Lecture des commandes de l'utilisateur

        System.out.print("Entrer l'adresse ip: "); //Récupère l'adresse IP
        ip = scan.nextLine();
        if(ip.length()==0) {
            ip = "localhost";
        }
        System.out.print("Entrer le port: ");
        portS = scan.nextLine();
        if(portS.length()>0) {
            port = Integer.parseInt(portS);
        }
        
        Client client = new Client(ip,port); 
        Partie partie = new Partie();

        System.out.println("Bienvenue sur le jeu Fanorona!");
        System.out.println("Pour savoir la liste des commandes: taper aide!");
        System.out.println("Vous jouez les pions: ○");
        partie.afficher();


        CommandeReseau cmd2 = new CommandeReseau(partie, 2, client.communication());



        //Thread réception des commandes de l'adversaire
        ReceptionCommande receptionCommande = new ReceptionCommande(client.communication(), partie, 2);
        Thread receptionCommandeThread = new Thread(receptionCommande);
        receptionCommandeThread.start();

        
        //Thread lecture des commandes
        LectureCommande lectureCommande = new LectureCommande(partie, scan,cmd2);
        Thread lectureCommandeThread = new Thread(lectureCommande);
        lectureCommandeThread.start();
        

    }
}