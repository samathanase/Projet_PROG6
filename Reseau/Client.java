package Reseau;

import java.net.Socket;
import Configuration.Configuration;
import Modele.*;


//Le client

public class Client {
    Socket socketCliente;
    Communication communication; 

    //Prend l'addresse ip et le port de l'hôte
    public Client(String ip,int port) {
        try {
            socketCliente = new Socket(ip,port);
            Configuration.instance().logger().info("Connection à l'hôte réussie: "+ip+" "+port);
            communication = new Communication(socketCliente);

        } catch (Exception e) {
            Configuration.instance().logger().severe("Impossible de se connecter à l'hôte"+e);
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