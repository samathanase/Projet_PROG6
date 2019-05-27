package Reseau;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

import Modele.Partie;

// Joueur: client est le joueur 2, l'hote est le joueur 1

public class ReseauHote {

    public static void main(String args[]) {
        Hote hote = new Hote(49153); //Création du serveur
        System.out.println("Bienvenue sur le jeu Fanorona!");
        System.out.println("L'adresse ip: "+adresseIp()+" et le port: 49153");
        System.out.println("En attente de la connexion de l'adversaire!");

        hote.ouvrir(); //Met le serveur en attente d'une connexion
        System.out.println("Pour savoir la liste des commandes: tapez aide");
        System.out.println("Adversaire connecté!");
        System.out.println("Vous jouez les pions: ●");
        
        Partie partie = new Partie();
        partie.joueur = 1;
        hote.envoyerAction(new ActionReseau(partie.joueur())); //On envoie au client le joueur qui commence

        partie.afficher();
        System.out.println(partie.joueur1() ? "C'est à vous de commencer" : "C'est à l'adversaire de commencer");                

        
        Scanner scan = new Scanner(System.in);
        CommandeReseau cmd1 = new CommandeReseau(partie, 1,hote.communication()); //Interpréteur de commande
        
        //Thread réception des commandes de l'adversaire
        ReceptionCommande receptionCommande = new ReceptionCommande(hote.communication(), partie, 1);
        Thread receptionCommandeThread = new Thread(receptionCommande);
        receptionCommandeThread.start();

        //Thread lecture des commandes
        LectureCommande lectureCommande = new LectureCommande(partie, scan,cmd1);
        Thread lectureCommandeThread = new Thread(lectureCommande);
        lectureCommandeThread.start();


    }

    //Récupérer l'adresse IP de la machine
    public static String adresseIp() {
        String ip="";
        String bonneIp="";
        try {
            // Énumération de toutes les cartes réseau. 
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); 
            while (interfaces.hasMoreElements()) { 
                NetworkInterface interfaceN = (NetworkInterface) interfaces.nextElement(); 
                List<InterfaceAddress> iaList= interfaceN.getInterfaceAddresses(); 
                for (InterfaceAddress interfaceAddress : iaList) {
                    ip = interfaceAddress.getAddress().getHostAddress();
                    if(!ip.equals("127.0.0.1") && ip.length()<16) { //Cherche l'adresse qui n'est pas localhost et est en ipv4
                        bonneIp = ip;
                    }
                } 
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bonneIp;
    }
}









