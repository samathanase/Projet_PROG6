package Reseau;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import Configuration.Configuration;

//Le serveur
public class Hote {
    ServerSocket socketHote;
    Socket socketCliente;
    Communication communication;

    public Hote(int port) {
        try {
            socketHote = new ServerSocket(port); //Création du serveur sur le port donné
        } catch (Exception e) {
            Configuration.instance().logger().severe("Impossible de lancer le serveur: " + e);
        }
    }

    // Ouvre la connexion
    public void ouvrir() {
        try {
            socketCliente = socketHote.accept(); //Attend la connexion d'un client
            Configuration.instance().logger().info("Connexion client réussie");
            communication = new Communication(socketCliente); //Création de la communication entre le serveur et le client
        } catch (IOException e) {
            Configuration.instance().logger().severe("Impossible de se connecter au client: " + e);
        }
    }

    //Envoye une action
    public void envoyerAction(ActionReseau action) {
        communication.envoyer(action);
    }

    //Récupère une action
    public ActionReseau recevoirAction() {
        return communication.recevoir();
    }

    //Retourne le canal de communication
    public Communication communication() {
        return communication;
    }
}