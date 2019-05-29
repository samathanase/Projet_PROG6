package Reseau;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import Configuration.Configuration;


//Communication entre 2 joueurs (client hôte)
public class Communication {
    ObjectInputStream entree;
    ObjectOutputStream sortie;

    public Communication(Socket socket) {
        try {
            sortie = new ObjectOutputStream(socket.getOutputStream());
            entree = new ObjectInputStream(socket.getInputStream()); 
        } catch (Exception e) {
            Configuration.instance().logger().severe("Impossible d'instancier la communication");
        }
    }

    // Envoyer une action
    public void envoyer(ActionReseau action) {
        try {
            sortie.writeObject((Object) action);
        } catch (IOException e) {
            Configuration.instance().logger().severe("Impossible d'envoyer l'action!");
        }
    }

    // Récupère une action
    public ActionReseau recevoir() {
        ActionReseau action =null;
        try {
            action = (ActionReseau) entree.readObject();
        } catch (ClassNotFoundException e) {
            Configuration.instance().logger().severe("Impossible de recevoir l'action! Classe inconnue");
        } catch (IOException e) {
            Configuration.instance().logger().severe("Impossible de recevoir l'action!");
        }
        return action;
    }
}