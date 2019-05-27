package Modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import Configuration.Configuration;

public class ChargerPartie {
    String nomFichier;

    // prend le nom du fichier à charger
    // La sauvegarde se trouve dans répertoire courant/.fanorona/sauvegarde/
    public ChargerPartie(String nomFichier) {
        String chemin = Configuration.instance().repertoireCourant();
        this.nomFichier = chemin + "/sauvegarde/" + nomFichier;
    }

    //Renvoie la partie chargée ou null si le chargement a échoué
    public Partie charger() {
        Partie partie = null;
        FileInputStream fichier;
        ObjectInputStream fluxObjet;
        try {
            fichier = new FileInputStream(nomFichier);
            fluxObjet = new ObjectInputStream(fichier);
            partie = (Partie) fluxObjet.readObject(); //On lit l'objet
            Configuration.instance().logger().info("Partie chargée depuis: " + nomFichier);
            fichier.close();
        } catch (Exception e) {
            Configuration.instance().logger().warning("Impossible de charger la partie: " + nomFichier);
        }
        return partie;
    }

    // Retourne la liste des noms des parties sauvegardées
    public static String [] listeSauvegarde() {
        String dossierNom = Configuration.instance().repertoireCourant() + "/sauvegarde/";
        File dossier = new File(dossierNom);

        File[] listeFichier = dossier.listFiles(); //la liste des fichiers
        String [] nomFichiers = new String [listeFichier.length]; //la liste des noms de fichier
        int i = 0;
        for (File f : listeFichier) {
            nomFichiers [i] = f.getName();
            i++;
        }
        return nomFichiers;
    }

}