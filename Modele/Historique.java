package Modele;

import java.util.ArrayList;

public class Historique {

    ArrayList<CoupHistorique> tabAnnuler; //tableau des coups annulés
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

    //Récupère le coup à annuler
    public CoupHistorique coupAnnuler() {
        tabRefaire.clear(); //Vide le tableau refaire
        CoupHistorique c = tabAnnuler.remove(tabAnnuler.size()-1);//Enlève du tableau annuler
        tabRefaire.add(c); //Ajoute au tableau refaire
        return c;
    }

    //Récupère le coup à refaire
    public CoupHistorique coupRefaire() {
        CoupHistorique c = tabRefaire.remove(tabRefaire.size()-1);//Enlève du tableau refaire
        tabAnnuler.add(c); //Ajoute au tableau annuler
        return c;
    }
    
    public void ajouter(CoupHistorique coup) {
        tabAnnuler.add(coup);
    }


}