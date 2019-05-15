package Modele;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import Configuration.Configuration;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SauvegarderPartie {
    Partie partie;
    String nomFichier;

    //Prend le chemin vers le fichier pour sauvegarder et la partie à sauvegarder
    public SauvegarderPartie(Partie p, String nomFichier) {
        this.partie = p;
        this.nomFichier = nomFichier;
    }

    //Si le chemin n'est pas spécifié sauvegarde dans un fichier dans le répertoire courant/.fanorona/sauvegarde/
    public SauvegarderPartie(Partie p) {
        this.partie = p;
        String chemin = Configuration.instance().repertoireCourant();
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat ("dd_MM_HH:mm:ss");
        this.nomFichier = chemin+"/sauvegarde/"+"save_"+dateFormat.format(date)+".save";
    }

    public void sauvegarder() {
        FileOutputStream fichier;
        ObjectOutputStream fluxObjet;
        try {
            fichier = new FileOutputStream(nomFichier);
            fluxObjet = new ObjectOutputStream(fichier);
            fluxObjet.writeObject(partie);
            Configuration.instance().logger().info("Partie sauvegardée dans: "+nomFichier);
            fichier.close();
        } catch (Exception e) {
            Configuration.instance().logger().warning("Impossible de sauvegarder la partie");
        }
    }
}