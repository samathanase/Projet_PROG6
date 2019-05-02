package Modele;


import java.util.Random;
import java.util.ArrayList;

/*
    La base du jeu 
*/

public class Partie {
    public int[][] tab; //Le tableau: 0:case libre, 1:pion joueur 1, 2:pion joueur 2
    public int joueur; //Le joueur qui doit jouer
    private Random rand; //Pour le tirage aléatoire du joueur
    private Direction precedenteDirection; //Précédente direction que le joueur a fait dans le tour
    private ArrayList<Coordonnees> casesVisitees; //Cases visitées dans le tour

    public Partie() {
        tab = new int[5][9]; //5 lignes, 9 colonnes
        rand = new Random();
        precedenteDirection = new Direction(EnumDirection.Inconnue);
        casesVisitees = new ArrayList<Coordonnees>();
        recommencer();
    }

    //Initialiser la grille
    private void initGrille() {
        for(int i=0;i<2;i++) {
            for(int j=0;j<9;j++) {
                tab[i][j] = 1;
            }
        }
        tab[2][0]=1;
        tab[2][1]=2;
        tab[2][2]=1;
        tab[2][3]=2;
        tab[2][4]=0;
        tab[2][5]=1;
        tab[2][6]=2;
        tab[2][7]=1;
        tab[2][8]=2;
        for(int i=3;i<5;i++) {
            for(int j=0;j<9;j++) {
                tab[i][j] = 2;
            }
        }
    }
    //Savoir quel joueur joue en premier de manière aléatoire
    private void joueurAleatoire() {
        joueur = rand.nextInt()%2+1;
    }

    //Recommence la partie
    public void recommencer() {
        this.initGrille();
        this.joueurAleatoire();
        precedenteDirection.changerDirection(EnumDirection.Inconnue);
        casesVisitees.clear();
    }

    //Retourne 0 si la partie n'est pas finie
    // 1 si le joueur 1 a gagné, 2 si le joueur 2 a gagné
    public int estFinie() {
        int j1=0,j2=0;
        for(int i=0;i<ligne();i++) { //Compte le nombre de pion de chaque joueur
            for(int j=0;j<colonne();j++) {
                if(pionJoueur1(i, j)) {
                    j1++;
                }
                else if(pionJoueur2(i, j)) {
                    j2++;
                }
            }
        }
        if(j1==0) { //Joueur 1 a perdu
            return 2;
        }
        else if(j2==0) { //Joueur 2 a perdu
            return 1;
        }
        return 0;
    }

    //Retourne le nombre de ligne
    public int ligne() {
        return 5;
    }
    //Retourne le nombre de colonne
    public int colonne() {
        return 9;
    }

    //Retourne vrai si la case est libre
    public boolean libre (int l,int c) {
        return tab[l][c] == 0;
    }
    //Retourne vrai si la case est un pion du joueur 1
    public boolean pionJoueur1 (int l,int c) {
        return tab[l][c] == 1;
    }
    //Retourne vrai si la case est un pion du joueur 2
    public boolean pionJoueur2 (int l,int c) {
        return tab[l][c] == 2;
    }
    //Renvoie vrai la case existe sur le plateau
    public boolean caseExiste (int l,int c) {
        return l>=0 && l< ligne() && c>=0 && c< colonne();
    }

    //Retourne vrai si c'est le joueur 1 qui a la main
    public boolean joueur1() {
        return joueur==1;
    }
    public boolean joueur2() {
        return joueur==2;
    }
    //Retourne quelle joueur a la main
    public int joueur() {
        return joueur;
    }
    //Change le joueur courant
    public void changerJoueur() {
        if(joueur1()) {
            joueur = 2;
        }
        else if(joueur2()) {
            joueur = 1;
        }
        //Remet à 0 les valeurs suivantes
        precedenteDirection.changerDirection(EnumDirection.Inconnue);
        casesVisitees.clear();
    }



    //Placer le pion pour le joueur
    private void placerPionJouer1(int l,int c) {
        tab[l][c] = 1;
    }
    private void placerPionJouer2(int l,int c) {
        tab[l][c] = 2;
    }
    //Placer pion pour le joueur donné
    private void placerPion(int joueur,int l,int c) {
        if(joueur==1) {
            placerPionJouer1(l, c);
        }
        else if(joueur==2) {
            placerPionJouer2(l, c);            
        }
    }
    //Enlève le pion
    private void enleverPion(int l,int c) {
        tab[l][c] = 0;
    }

    //TODO: prendre en compte les coordonnées attention aux diagonales
    //Retourne la liste des cases adjacentes de la case demandée
    public ArrayList<Coordonnees> casesAdjacentes(int l, int c) {
        ArrayList<Coordonnees> listCases = new ArrayList<Coordonnees>();

        if(caseExiste(l+1,c-1)) {
            listCases.add(new Coordonnees(l+1,c-1));
        }
        if(caseExiste(l+1,c)) {
            listCases.add(new Coordonnees(l+1,c));
        }
        if(caseExiste(l+1,c+1)) {
            listCases.add(new Coordonnees(l+1,c+1));
        }
        if(caseExiste(l,c-1)) {
            listCases.add(new Coordonnees(l,c-1));
        }
        if(caseExiste(l,c+1)) {
            listCases.add(new Coordonnees(l,c+1));
        }
        if(caseExiste(l-1,c-1)) {
            listCases.add(new Coordonnees(l,c-1));
        }
        if(caseExiste(l-1,c)) {
            listCases.add(new Coordonnees(l,c));
        }
        if(caseExiste(l-1,c+1)) {
            listCases.add(new Coordonnees(l,c+1));
        }
        return listCases;
    }
    public ArrayList<Coordonnees> casesAdjacentes(Coordonnees C) {
        return casesAdjacentes(C.ligne(), C.colonne());
    }

    //Renvoie la liste des cases accessibles du pion donné
    public ArrayList<Coordonnees> casesAccessibles(int l, int c) {
        ArrayList<Coordonnees> listCases = new ArrayList<Coordonnees>();
        ArrayList<Coordonnees> listCasesAdj = casesAdjacentes(l,c); //Récupère les cases adjacentes
        int lN,cN;
        for(int i=0;i<listCasesAdj.size();i++) { //Pour chaque case adjacente
            lN = listCasesAdj.get(i).ligne();
            cN = listCasesAdj.get(i).colonne();
            if( libre(lN,cN) ) {
                listCases.add(new Coordonnees(lN,cN));
            }
        }
        return listCases;
    }
    public ArrayList<Coordonnees> casesAccessibles(Coordonnees C) {
        return casesAccessibles(C.ligne(), C.colonne());
    }

    //Retourne vrai si le coup joué est valide
    public boolean coupValide(int lPion,int cPion, int lDestination, int cDestination) {
        if( (joueur1() && pionJoueur2(lPion, cPion)) || (joueur2() && pionJoueur1(lPion, cPion)) || libre(cPion,lPion) )  { //Pion incorrecte
            System.out.println("Mouvement erreur");
            return false;
        }
        ArrayList<Coordonnees> listCoord = casesAccessibles(lPion,cPion);
        if(!listCoord.contains(new Coordonnees(lDestination,cDestination))) { //Destination incorrecte
            System.out.println("Destination erreur");
            System.out.println(listCoord);
            return false;
        }
        Direction dir = directionCoup(lPion, cPion, lDestination, cDestination); //Différente du coup précédent
        if(dir.equals(precedenteDirection)){
            System.out.println("Direction erreur");
            return false;
        }
        if(casesVisitees.contains(new Coordonnees(lDestination,cDestination))){
            System.out.println("Case déjà visitée erreur");
            return false;
        }
        return true;
    }

    //Retourne la direction du coup
    public Direction directionCoup(int lPion,int cPion, int lDestination, int cDestination) {
        Direction dir = new Direction(EnumDirection.Inconnue);
        if (coupValide(lPion, cPion, lDestination, cDestination)) {
            if(lPion==lDestination-1 && cPion==cDestination-1) {
                dir.changerDirection(EnumDirection.HautGauche);
            }
            else if(lPion==lDestination-1 && cPion==cDestination) {
                dir.changerDirection(EnumDirection.Haut);
            }
            else if(lPion==lDestination-1 && cPion==cDestination+1) {
                dir.changerDirection(EnumDirection.HautDroite);
            }
            else if(lPion==lDestination && cPion==cDestination-1) {
                dir.changerDirection(EnumDirection.Gauche);
            }
            else if(lPion==lDestination && cPion==cDestination+1) {
                dir.changerDirection(EnumDirection.Droite);
            }
            else if(lPion==lDestination+1 && cPion==cDestination-1) {
                dir.changerDirection(EnumDirection.BasGauche);
            }
            else if(lPion==lDestination+1 && cPion==cDestination) {
                dir.changerDirection(EnumDirection.Bas);
            }
            else if(lPion==lDestination+1 && cPion==cDestination+1) {
                dir.changerDirection(EnumDirection.BasDroite);
            }
        }
        return dir;
    }

    //Retourne vrai si le coup est une percussion
    public boolean percussion(int lPion,int cPion, int lDestination, int cDestination) {
        Direction dir = directionCoup(lPion, cPion, lDestination, cDestination); //La direction du coup
        Coordonnees C = dir.coordonnees();
        //On regarde si dans la case suivante est un pion adverse
        if (coupValide(lPion, cPion, lDestination, cDestination)) {
            if(joueur1() && pionJoueur2(lDestination+C.ligne(), cDestination+C.colonne())) {
                return true;
            }
            else if(joueur2() && pionJoueur1(lDestination+C.ligne(), cDestination+C.colonne())) {
                return true;
            }
        }
        return false;
    }

    //Retourne vrai si le coup est une aspiration
    public boolean aspiration(int lPion,int cPion, int lDestination, int cDestination) {
        Direction dir = directionCoup(lPion, cPion, lDestination, cDestination); //La direction du coup
        Coordonnees C = dir.coordonnees();
        C.oppose();
        if (coupValide(lPion, cPion, lDestination, cDestination)) {
            if(joueur1() && pionJoueur2(lDestination+C.ligne(), cDestination+C.colonne())) {
                return true;
            }
            else if(joueur2() && pionJoueur1(lDestination+C.ligne(), cDestination+C.colonne())) {
                return true;
            }
        }
        return false;
    }
    //Renvoie vrai s'il y a une aspiration et une percussion
    public boolean aspirationPercution(int lPion,int cPion, int lDestination, int cDestination) {
        return percussion(lPion, cPion, lDestination, cDestination) && aspiration(lPion, cPion, lDestination, cDestination);
    }

    // Retourne la liste des pions capturables avec le coup donné
    public ArrayList<Coordonnees> pionsCapturables(int lPion,int cPion, int lDestination, int cDestination) {
        if(aspirationPercution(lPion, cPion, lDestination, cDestination)) { //Erreur, il faut choisir une des 2 captures
            return new ArrayList<Coordonnees>();
        }
        else if(percussion(lPion, cPion, lDestination, cDestination)) {
            return pionsCapturablesPercussion(lPion, cPion, lDestination, cDestination);
        }
        else if(aspiration(lPion, cPion, lDestination, cDestination)) {
            return pionsCapturablesAspiration(lPion, cPion, lDestination, cDestination);
        }
        return new ArrayList<Coordonnees>();
    }

    // Retourne la liste des pions capturables avec le coup donné
    public ArrayList<Coordonnees> pionsCapturablesPercussion(int lPion,int cPion, int lDestination, int cDestination) {
        ArrayList<Coordonnees> listePions =  new ArrayList<Coordonnees>();

        if(!percussion(lPion, cPion, lDestination, cDestination)) { //Erreur
            return listePions;
        }
        Direction dir = directionCoup(lPion, cPion, lDestination, cDestination); //La direction du coup
        Coordonnees cDep = dir.coordonnees(); //La direction sous forme de coordonnées
        return pionsCapturablesParcours(lDestination, cDestination, cDep);        
    }

    // Retourne la liste des pions capturées avec le coup donné
    public ArrayList<Coordonnees> pionsCapturablesAspiration(int lPion,int cPion, int lDestination, int cDestination) {
        ArrayList<Coordonnees> listePions =  new ArrayList<Coordonnees>();
        if(!aspiration(lPion, cPion, lDestination, cDestination)) { //Erreur
            return listePions;
        }
        Direction dir = directionCoup(lPion, cPion, lDestination, cDestination); //La direction du coup
        Coordonnees cDep = dir.coordonnees();
        cDep.oppose();
        
        return pionsCapturablesParcours(lPion, cPion, cDep);
    }

    //Méthode utiliser par les 2 méthodes au dessus pour savoir les pions capturables
    private ArrayList<Coordonnees> pionsCapturablesParcours(int lDestination, int cDestination,Coordonnees cDep) {
        ArrayList<Coordonnees> listePions =  new ArrayList<Coordonnees>();
        boolean suivant = true;
        int lSuivant = lDestination;
        int cSuivant = cDestination;
        while(suivant) {//Tant qu'il a des pions dans la direction on continue
            lSuivant += cDep.ligne();
            cSuivant += cDep.colonne();
            //Si le pion est un pion de l'adversaire on l'ajoute
            if(caseExiste(lDestination, cDestination) && ((joueur1() && pionJoueur2(lSuivant, cSuivant))||(joueur2() && pionJoueur1(lSuivant, cSuivant)))) {
                listePions.add(new Coordonnees(lSuivant,cSuivant));
            }
            else { //Plus de pions à explorer
                suivant = false;
            }
        }
        return listePions;
    }


    //TODO : finir jouer
    //Joue un pion vers une case, renvoie vrai si le coup s'est fait correctement, faux s'il y a eu un problème
    //Cpature: 0 si pas de capture, 1 si percussion, 2 si aspiration
    public boolean jouer(int lPion,int cPion, int lDestination, int cDestination,int capture) {
        if(!coupValide(lPion, cPion, lDestination, cDestination)) { //Coup invalide
            return false;
        }
        enleverPion(lPion, cPion); //On enlève le pion joué
        placerPion(joueur, lDestination, cDestination); //On le place à l'endroit voulu

        if(capture==0) {//pas de capture
            changerJoueur(); //C'est au joueur suivant
        }
        else { //Capture
            ArrayList<Coordonnees> listePions = new ArrayList<Coordonnees>();

            if(capture==1) { //percussion
                listePions =  pionsCapturablesPercussion(lPion, cPion, lDestination, cDestination);
            }
            else if(capture==2) { //aspiration
                listePions =  pionsCapturablesAspiration(lPion, cPion, lDestination, cDestination);
            }

            for (Coordonnees pionAEnlever : listePions) { //On enlève les pions du joueur adverse
                enleverPion(pionAEnlever.l, pionAEnlever.c);
            }

            precedenteDirection = directionCoup(lPion, cPion, lDestination, cDestination); //On sauvegarde la direction
            casesVisitees.add(new Coordonnees(lDestination,cDestination));
        }
        return true;
    }


    //Affiche la grille dans la console
    public void afficher() {
        int l=0;
        System.out.print("  ");
        for(int i=0;i<colonne();i++) { 
            System.out.print(i+" ");
        }
        System.out.print("\n");
        for(int i=0;i<ligne();i++) { 
            System.out.print(l+" ");
            l++;
            for(int j=0;j<colonne();j++) {
                if(pionJoueur1(i, j)) {
                    System.out.print("● ");
                }
                else if(pionJoueur2(i, j)) {
                    System.out.print("○ ");
                }
                else {
                    System.out.print(". ");
                } 
            }
            System.out.println();
        }
    }
}