
package Modele;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Configuration.Configuration;

/*
    Interprète une chaine de caractères écrite par l'utilisateur
    Utiliser uniquement pour l'interface en ligne de commande
*/

//TODO afficher message d'erreur
//

public class Commande {
    String cmd;
    Partie partie;
    boolean valide;
    int joueur;

    int typeCmd;
    int valeur;
    Coordonnees pion;
    Coordonnees destination;
    int lPion,cPion,lDestination,cDestination,capture;
    Coup coup;


    //On lui passe la partie et le joueur concerné
    public Commande(Partie partie,int joueur) {
        this.partie = partie;
        this.joueur = joueur;
    }

    //Analyse la commande et regarde si elle est correcte
    public void interpreter(String cmd) {
        this.cmd = cmd;
        typeCmd = -1;
        valide = false;
        Matcher m;

        // Commande pour avoir les cases accessibles d'un pion
        if( (m = Pattern.compile("caseAcc +(\\d+) +(\\d+)").matcher(cmd)).find() ) {
            typeCmd = 1;
            lPion = Integer.parseInt(m.group(1));
            cPion = Integer.parseInt( m.group(2) );
            pion = new Coordonnees(lPion,cPion);

            if (partie.caseExiste(pion) && partie.pionJoueur(joueur, pion)) {
                valide = true;
            }
        }

        //L'aide
        else if((m = Pattern.compile("aide").matcher(cmd)).find()) {
            typeCmd = 2;
            valide = true;
        }

        //Avoir les cases adjacentes
        else if( (m = Pattern.compile("caseAdj +(\\d+) +(\\d+)").matcher(cmd)).find() ) {
            typeCmd = 3;
            lPion = Integer.parseInt( m.group(1) );
            cPion = Integer.parseInt( m.group(2) );
            pion = new Coordonnees(lPion,cPion);
            if (partie.caseExiste(pion)) {
                valide = true;
            }
        }

        //Avoir les coups possibles
        else if( (m = Pattern.compile("coups").matcher(cmd)).find() ) {
            typeCmd = 4;
            valide = true;
        }

        //Avoir un précédent coup joué
        else if( (m = Pattern.compile("precedentCoup +(\\d*)").matcher(cmd)).find() ) {
            if((m.groupCount()==2 && partie.historique().taille()<Integer.parseInt(m.group(1))) || (m.groupCount()==1 && partie.historique().peutAnnuler() )) {
                typeCmd = 5;
                if(m.groupCount()==2)
                    valeur = Integer.parseInt(m.group(1));
                else
                    valeur = -1;
                valide = true;
            }
        }

        //TODO à modifier
        //Annuler le coup
        else if( (m = Pattern.compile("annuler").matcher(cmd)).find() ) {
            if(partie.peutAnnuler()){
                typeCmd = 6;
                valide = true;
            }
        }

        //Refaire un coup
        else if( (m = Pattern.compile("refaire").matcher(cmd)).find() ) {
            if(partie.peutRefaire()){
                typeCmd = 7;
                valide = true;
            }
        }

        //Jouer un coup
        else if( (m = Pattern.compile("(\\d+) +(\\d+) +(\\d+) +(\\d+) *(\\d*)$").matcher(cmd)).find() ) {
            System.out.println(cmd);
            typeCmd = 8;
            pion = new Coordonnees(Integer.parseInt(m.group(1)),Integer.parseInt(m.group(2)));
            destination = new Coordonnees(Integer.parseInt(m.group(3)),Integer.parseInt(m.group(4)));
            capture = m.group(5).length()>0 ? Integer.parseInt(m.group(5)) : -1;
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
                }
            }
        }

        //Recommencer la partie
        else if( (m = Pattern.compile("recommencer").matcher(cmd)).find() ) {
            typeCmd = 9;
            valide = true;
        }

        //Fin du tour
        else if( (m = Pattern.compile("fin|finTour").matcher(cmd)).find() ) {
            typeCmd = 10;
            valide = true;
        }

        //Quitter le jeu
        else if( (m = Pattern.compile("quitter").matcher(cmd)).find() ) {
            typeCmd = 11;
            valide = true;
        }

         //Charger une partie
         else if( (m = Pattern.compile("charger").matcher(cmd)).find() ) {
            typeCmd = 12;
            valide = true;
        }

         //Sauvegarder une partie
         else if( (m = Pattern.compile("sauvegarder").matcher(cmd)).find() ) {
            typeCmd = 13;
            valide = true;
        }


        else { //Commande non reconnue
            valide = false;
            System.out.println("Commande inconnue");
        }

    }

    //Accés au tableau de la chaine de caractères représentant le plateau et modifie la case avec le caractère donné
    public void caseStr(StringBuffer str,int l,int c, Character cara ) {
        if (Configuration.instance().lis("affichageConsole").equals("complet")) {
            str.setCharAt((l*2+1)*(partie.colonne()*2+2) +(c)*2+1, cara);
        }
        else { //Affichage simple
            str.setCharAt((l+1)*(partie.colonne()+1)*2 +(c+1)*2 +1, cara);
        }
    }

    //Exécuter la commande, à utiliser après la méthode interpréter
    public void executer() {
        if(estValide()) {
            switch (typeCmd) {
                case 1: //case accessible
                    ArrayList<Coordonnees> listAcc = partie.casesAccessibles(pion);
                    StringBuffer strAcc = new StringBuffer(partie.toString());
                    for(Coordonnees c : listAcc) {
                        caseStr(strAcc,c.ligne(),c.colonne(),'◽');
                    }
                    if(listAcc.size()==0) {
                        System.out.println("Aucune case accessible pour le pion" +pion);
                    }
                    System.out.print(strAcc);
                    break;

                case 2: //Affichage de l'aide
                    System.out.println("L'aide c'est important");
                    break;

                case 3: //Liste les cases adjacentes (pas très utile)
                    ArrayList<Coordonnees> listAdj = partie.casesAdjacentes(pion);
                    StringBuffer strAdj = new StringBuffer(partie.toString());
                    for(Coordonnees c : listAdj) {
                        caseStr(strAdj,c.ligne(),c.colonne(),'◽');
                        // System.out.println(c);
                    }
                    if(listAdj.size()==0) {
                        System.out.println("Aucune case adjacente pour le pion" +pion);
                    }
                    System.out.print(strAdj);
                    break;

                case 4: //Liste des coups possibles
                    ArrayList<Coup> listCoups = partie.listeCoupsValides();
                    // StringBuffer strAdj = new StringBuffer(partie.toString());
                    System.out.println(listCoups);
                
                    break;

                case 5: //Affiche un un coup précédent
                    CoupHistorique coupH = partie.historique().accederCoup(valeur == -1 ? partie.historique().taille() : partie.historique().taille()-valeur );
                    System.out.println(coupH);
                    break;

                case 6: //Annuler un coup
                    partie.annuler();
                    System.out.print(partie);
                    break;
                case 7: //Refaire un coup
                    partie.refaire();
                    System.out.print(partie);
                    break;

                case 8: //jouer un coup
                    partie.jouer(coup);
                    System.out.print(partie);
                    break;

                case 9: //recommencer la partie
                    partie.recommencer();
                    System.out.println("Partie recommencée!");
                    System.out.print(partie);
                    break;

                case 10: //met fin au tour
                    if(partie.peutFinTour()) {
                        partie.finTour();
                    }
                    else {
                        System.out.println("Vous ne pouvez mettre fin au tour!");
                    }
                    break;

                case 11: //Quitter le jeu
                    System.exit(1);
                    break;
            
                // case 12: //Charger une partie
                //     break;

                // case 13: //Sauvegarder une partie
                //     break;

                default:
                    break;
            }
        }
    }

    //Retourne vrai si c'est un coup
    // public boolean estCoup() {

    // }
    // public Coup coup() {

    // }

    //Retourne vrai si la chaine de caractères interprétée est correcte
    public boolean estValide() {
        return valide;
    }

}