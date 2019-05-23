package Modele;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import Configuration.Configuration;
import java.text.SimpleDateFormat;
import java.util.Date;

//Sauvegarde une partie dans répertoire courant/.fanorona/sauvegarde/
public class SauvegarderPartie {
    Partie partie;
    String nomFichier;

    //Prend le nom du fichier
    public SauvegarderPartie(Partie p, String nomFichier) {
        this.partie = p;
        String chemin = Configuration.instance().repertoireCourant();
        this.nomFichier = chemin+"/sauvegarde/"+nomFichier;
    }

    //Si le nom du fichier n'est pas donné, utilise la date à la quelle le fichier est sauvegardé
    public SauvegarderPartie(Partie p) {
        this.partie = p;
        String chemin = Configuration.instance().repertoireCourant();
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat ("dd_MM_HH:mm:ss");
        this.nomFichier = chemin+"/sauvegarde/"+"save_"+dateFormat.format(date);
    }

    public boolean sauvegarder() {
        FileOutputStream fichier;
        ObjectOutputStream fluxObjet;
        boolean b = false;
        try {
            fichier = new FileOutputStream(nomFichier);
            fluxObjet = new ObjectOutputStream(fichier);
            fluxObjet.writeObject(partie);
            Configuration.instance().logger().info("Partie sauvegardée dans: "+nomFichier);
            b = true;
            fichier.close();
        } catch (Exception e) {
            Configuration.instance().logger().warning("Impossible de sauvegarder la partie");
        }
        return b;
    }
}