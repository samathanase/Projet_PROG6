
package Modele;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Configuration.Configuration;

/*
    Interprète une chaine de caractères écrite par l'utilisateur
    Base du code utiliser pour les commandes dans la console (sur la même machine ou en réseau)
*/

//TODO afficher message d'erreur
//

public abstract class Commande {
    String cmd;
    Partie partie;
    boolean valide;
    int joueur;

    int indiceAnnuler; boolean parcourtHistorique; Partie partieCopie; int tailleHistorique;

    int typeCmd;
    Coordonnees pion;
    Coordonnees destination;
    int lPion,cPion,lDestination,cDestination,capture;
    Coup coup;

    //On lui passe la partie et le joueur concerné
    public Commande(Partie partie,int joueur) {
        this.partie = partie;
        this.joueur = joueur;
        indiceAnnuler = 0;
        parcourtHistorique = false;
    }

    //Analyse la commande l'exécute
    public void interpreter(String cmd) {
        this.cmd = cmd;
        typeCmd = -1;
        valide = false;
        Matcher m;

        // Commande pour avoir les cases accessibles d'un pion
        if( (m = Pattern.compile("caseAcc +(\\d+) +(\\d+)").matcher(cmd)).find() ) {
            interpreterCaseAcc(Integer.parseInt(m.group(1)),Integer.parseInt(m.group(2)));
        }

        //L'aide
        else if((m = Pattern.compile("aide").matcher(cmd)).find()) {
            interpreterAide();
        }

        //Avoir les cases adjacentes
        else if( (m = Pattern.compile("caseAdj +(\\d+) +(\\d+)").matcher(cmd)).find() ) {
            interpreterCaseAdj(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        }

        //Avoir les coups possibles
        else if( (m = Pattern.compile("coups").matcher(cmd)).find() ) {
            interpreterCoups();
        }

        //Avoir un précédent coup joué
        else if( (m = Pattern.compile("precedentCoup *(\\d*)").matcher(cmd)).find() ) {
            interpreterPrecedentCoup(m);
        }

        //Annuler le coup
        else if( (m = Pattern.compile("annuler").matcher(cmd)).find() ) {
            interpreterAnnuler();
        }

        //Refaire un coup
        else if( (m = Pattern.compile("refaire").matcher(cmd)).find() ) {
            interpreterRefaire();
        }

        //Jouer un coup
        else if( (m = Pattern.compile("(\\d+) +(\\d+) +(\\d+) +(\\d+) *(\\d*)$").matcher(cmd)).find() ) {
            interpreterJouerCoup(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
            Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)),m.group(5).length()==0 ? -1 : Integer.parseInt(m.group(5)) );            
        }

        //Recommencer la partie
        else if( (m = Pattern.compile("recommencer").matcher(cmd)).find() ) {
            interpreterRecommencer();
        }

        //Fin du tour
        else if( (m = Pattern.compile("fin|finTour").matcher(cmd)).find() ) {
            interpreterFinTour();
        }

        //Quitter le jeu
        else if( (m = Pattern.compile("quitter").matcher(cmd)).find() ) {
            interpreterQuitter();
        }

        //Charger une partie
        else if( (m = Pattern.compile("charger").matcher(cmd)).find() ) {
            interpreterCharger();
        }

        //Sauvegarder une partie
        else if( (m = Pattern.compile("sauvegarder").matcher(cmd)).find() ) {
            interpreterSauvegarder();
        }

        //Pour se balader dans l'historique
        //Affiche le plateau un coup avant
        else if( (m = Pattern.compile("^b$").matcher(cmd)).find() ) {
            interpreterPlateauPrecedent();
        }
        //Affiche le plateau un coup après
        else if( (m = Pattern.compile("^n$").matcher(cmd)).find() ) {
            interpreterPlateauSuivant();
        }

        else { //Commande non reconnue
            valide = false;
            System.out.println("Commande inconnue");
        }
    }

	public void interpreterCaseAcc(int l,int c) {
		typeCmd = 1;
		lPion = l;
		cPion = c;
		pion = new Coordonnees(lPion,cPion);

		if (partie.caseExiste(pion) && partie.pionJoueur(joueur, pion)) {
            valide = true;
            ArrayList<Coordonnees> listAcc = partie.casesAccessibles(pion);
            StringBuffer strAcc = new StringBuffer(partie.toString());
            for(Coordonnees cA : listAcc) {
                caseStr(strAcc,cA.ligne(),cA.colonne(),'◎');
            }
            if(listAcc.size()==0) {
                System.out.println("Aucune case accessible pour le pion" +pion);
            }
            System.out.print(strAcc);
        }
        else {
            System.out.println("Pion: " +pion+" invalide!");
        }
    }
    
    public void interpreterAide() {
        typeCmd = 2;
        valide = true;
    }

    public void interpreterCaseAdj(int l,int c) {
        typeCmd = 3;
        lPion = l;
        cPion = c;
        pion = new Coordonnees(lPion,cPion);
        if (partie.caseExiste(pion)) {
            valide = true;
            ArrayList<Coordonnees> listAdj = partie.casesAdjacentes(pion);
            StringBuffer strAdj = new StringBuffer(partie.toString());
            for(Coordonnees cA : listAdj) {
                caseStr(strAdj,cA.ligne(),cA.colonne(),'◎');
            }
            if(listAdj.size()==0) {
                System.out.println("Aucune case adjacente pour le pion" +pion);
            }
            System.out.print(strAdj);
        }
        else {
            System.out.println("Pion: " +pion+" invalide!");
        }
    }

    public void interpreterCoups() {
        typeCmd = 4;
        valide = true;
        ArrayList<Coup> listCoups = partie.listeCoupsValides();
        System.out.println(listCoups);
    }

    public void interpreterPrecedentCoup(Matcher m) {
        typeCmd = 5;
        valide = true;
        if(m.group(1).length()!=0 && partie.historique().taille()>=Integer.parseInt(m.group(1)) )
            System.out.println(partie.historique().accederCoup( partie.historique().taille()-Integer.parseInt(m.group(1))));
        else if( (m.group(1).length()==0 && partie.historique().taille()>0 )) //Affiche le dernier coup joué
            System.out.println(partie.historique().accederCoup( partie.historique().taille()-1));

        else {
            System.out.println("Impossible d'accéder au coup demandé");
            valide = false;
        }
    }

    public void interpreterAnnuler() {
        if(partie.peutAnnuler()){
            typeCmd = 6;
            valide = true;
            partie.annuler();
            System.out.print(partie);
        }
    }

    public void interpreterRefaire() {
        if(partie.peutRefaire()){
            typeCmd = 7;
            valide = true;
            partie.refaire();
            System.out.print(partie);
        }
    }

    public void interpreterJouerCoup(int l,int c,int lD,int cD,int action) {
        System.out.println(cmd);
        typeCmd = 8;
        pion = new Coordonnees(l,c);
        destination = new Coordonnees(lD,cD);
        capture = action;
        coup = new Coup(pion,destination);
        if(partie.coupValide(coup)) {
            if(partie.aspirationPercution(coup) && capture!=1 && capture!=2) { //Choix entre aspiration/percussion et le joueur n'a rien mis 
                valide = false;
                System.out.println("Vous devez indiquer la capture: Percussion:1 , Aspiration:2");
            }
            else {
                if(partie.aspirationPercution(coup)) {
                    coup.changerAction(capture);
                }
                else if(partie.aspiration(coup)) {
                    coup.actionAspiration();
                }
                else if(partie.percussion(coup)) {
                    coup.actionPercussion();
                }
                else {
                    coup.actionPasCapture();
                }
                valide = true;
                parcourtHistorique = false; //On ne parcourt plus l'historique
                partie.jouerSansFinTour(coup);
                System.out.print(partie);
            }
        }
        else {
            System.out.println("Coup impossible!");
        }
    }

    public void interpreterRecommencer() {
        typeCmd = 9;
        valide = true;
        partie.recommencer();
        System.out.println("Partie recommencée!");
        System.out.print(partie);
    }

    public void interpreterFinTour() {
        typeCmd = 10;
        valide = true;
        if(partie.peutFinTour()) {
            partie.finTour();
        }
        else {
            System.out.println("Vous ne pouvez mettre fin au tour!");
        }
    }

    public void interpreterQuitter() {
        typeCmd = 11;
        valide = true;
        System.exit(1);
    }

    //TODO à faire
    public void interpreterCharger() {
        typeCmd = 12;
        valide = true;
    }
    public void interpreterSauvegarder() {
        typeCmd = 13;
        valide = true;
    }


    public void interpreterPlateauPrecedent() {
        typeCmd = 14;
        if (parcourtHistorique==false) {
            parcourtHistorique = true; //On parcourt l'historique
            partieCopie = new Partie(partie); //La partie sur laquelle on parcourt
            tailleHistorique = partieCopie.historique().taille(); //le nombre de coups faits
        }
        if(partieCopie.historique().peutAnnuler()) {
            indiceAnnuler+=1; //Indice du coup courant
            System.out.println(indiceAnnuler);
            partieCopie.annuler();
            System.out.println("Coups:"+(tailleHistorique-indiceAnnuler)+"|"+tailleHistorique);
            System.out.println("Joueur: "+partieCopie.joueur());
            partieCopie.afficher();
        }
        valide = true;
    }

    public void interpreterPlateauSuivant() {
        typeCmd = 15;
        if(partieCopie!=null && partieCopie.historique().peutRefaire()) {
            indiceAnnuler-=1;
            partieCopie.refaire();
            System.out.println("Coups:"+(tailleHistorique-indiceAnnuler)+"|"+tailleHistorique);
            System.out.println("Joueur: "+partieCopie.joueur());
            partieCopie.afficher();
        }
        valide = true;
    }





    //Accés au tableau de la chaine de caractères représentant le plateau et modifie la case avec le caractère donné
    public void caseStr(StringBuffer str,int l,int c, Character cara ) {
        if (Configuration.instance().lis("affichageConsole").equals("complet")) {
            str.setCharAt((l*2+1)*(partie.colonne()*2+2) +(c)*2+1, cara);
        }
        else { //Affichage simple
            str.setCharAt((l+1)*(partie.colonne()+1)*2 +(c)*2+1, cara);
        }
    }
    
    //Retourne vrai si la chaine de caractères interprétée est correcte
    public boolean estValide() {
        return valide;
    }

}