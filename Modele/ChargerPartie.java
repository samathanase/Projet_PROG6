package Modele;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import Configuration.Configuration;

public class ChargerPartie {
    String nomFichier;

    //prend le chemin vers la sauvegarde
    public ChargerPartie(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public Partie charger() {
        Partie partie = null;
        FileInputStream fichier;
        ObjectInputStream fluxObjet;
        try {
            fichier = new FileInputStream(nomFichier);
            fluxObjet = new ObjectInputStream(fichier);
            partie = (Partie) fluxObjet.readObject();
            Configuration.instance().logger().info("Partie chargée depuis: "+nomFichier);
            fichier.close();
        } catch (Exception e) {
            Configuration.instance().logger().warning("Impossible de charger la partie: "+nomFichier);
        }
        return  partie;
    }
}