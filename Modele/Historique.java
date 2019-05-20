package Modele;

import java.io.Serializable;
import java.util.ArrayList;

public class Historique implements Serializable {
    private static final long serialVersionUID = -4797871433390956257L;

    ArrayList<CoupHistorique> tabAnnuler; // tableau des coups annulés
    ArrayList<CoupHistorique> tabRefaire; //tableau des coups a refaire

    Historique() {
        tabAnnuler = new ArrayList<CoupHistorique>();
        tabRefaire = new ArrayList<CoupHistorique>();
    }

    Historique(Historique copie) { //Copie l'historique
        tabAnnuler = new ArrayList<CoupHistorique>();
        tabRefaire = new ArrayList<CoupHistorique>();
        for(int i=0;i<copie.tabAnnuler.size();i++) {
            tabAnnuler.add(new CoupHistorique(copie.tabAnnuler.get(i)));
        }
        for(int i=0;i<copie.tabRefaire.size();i++) {
            tabRefaire.add(new CoupHistorique(copie.tabRefaire.get(i)));
        }
    }

    public boolean peutAnnuler() {
        return tabAnnuler.size()>0;
    }
    public boolean peutRefaire() {
        return tabRefaire.size()>0;
    }

    //Retourne le coup demandé
    public CoupHistorique accederCoup(int i) {
        CoupHistorique c = null;
        if(tabAnnuler.size()<i) {
            c = tabAnnuler.get(i);
        }
        return c;
    }

    //Retourne le nombe de coup dans l'historique
    public int taille() {
        return this.tabAnnuler.size();
    }

    //Récupère le coup à annuler
    public CoupHistorique coupAnnuler() {
        CoupHistorique c = tabAnnuler.remove(tabAnnuler.size()-1);//Enlève du tableau annuler
        tabRefaire.add(c); //Ajoute au tableau refaire
        return c;
    }

    //Récupère le coup à refaire
    public CoupHistorique coupRefaire() {
        CoupHistorique c = tabRefaire.remove(tabRefaire.size()-1);//Enlève du tableau refaire
        //Ajoute au tableau annuler dans la méthode jouer de la classe partie
        return c;
    }
    
    //Quand on joue un coup on le met dans le tableau annuler et on vide le tableau refaire
    public void ajouter(CoupHistorique coup) {
        tabAnnuler.add(coup);
        tabRefaire.clear();
    }


}