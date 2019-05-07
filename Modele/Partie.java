package Modele;


import java.util.Random;
import java.util.ArrayList;
import Configuration.Configuration;

/*
    La base du jeu 
*/



public class Partie {
    public Grille grille; //Le tableau: 0:case libre, 1:pion joueur 1, 2:pion joueur 2
    public int [][] tab;
    public int joueur; //Le joueur qui doit jouer
    private Random rand; //Pour le tirage aléatoire du joueur
    private Direction precedenteDirection; //Précédente direction que le joueur a fait dans le tour
    private ArrayList<Coordonnees> casesVisitees; //Cases visitées dans le tour
    private Coordonnees precedentPion; //le précédent pion joué dans le tour
    public Coordonnees pionSelectionne;


    public Partie() {
        grille = new Grille(5,9); //5 lignes, 9 colonnes
        tab = grille.tab();
        rand = new Random();
        precedenteDirection = new Direction(EnumDirection.Inconnue);
        casesVisitees = new ArrayList<Coordonnees>();
        recommencer();
    }

    public Partie(int l,int c) {
        if(l%2==0 || c%2==0) { //Erreur
            System.exit(1);
        }
        grille = new Grille(l,c);
        tab = grille.tab();
        rand = new Random();
        precedenteDirection = new Direction(EnumDirection.Inconnue);
        casesVisitees = new ArrayList<Coordonnees>();
        recommencer();
    }

    //Initialiser la grille
    private void initGrille() {
        for(int i=0;i<ligne()/2;i++) {
            for(int j=0;j<colonne();j++) {
                tab[i][j] = 1;
            }
        }
        int p=1;
        for(int j=0;j<colonne();j++) {
            if(j==colonne()/2) {
                tab[ligne()/2][j] = 0;
            }
            else {
                tab[ligne()/2][j] = p;
                p = p==1 ? 2 : 1;
            } 
        }

        for(int i=ligne()/2+1;i<ligne();i++) {
            for(int j=0;j<colonne();j++) {
                tab[i][j] = 2;
            }
        }
    }
    //Savoir quel joueur joue en premier de manière aléatoire
    private void joueurAleatoire() {
        joueur = rand.nextInt(2)+1;
    }

    //Recommence la partie
    public void recommencer() {
        this.initGrille();
        this.joueurAleatoire();
        precedentPion = null;
        precedenteDirection.changerDirection(EnumDirection.Inconnue);
        casesVisitees.clear();
        pionSelectionne = null;
    }

    //Retourne 0 si la partie n'est pas finie
    public boolean estFinie() {
        if(gagnant()==0) {
            return false;
        }
        return true;
    }

    //0 si la partie n'est pas finie, 1 si le joueur 1 a gagné, 2 si le joueur 2 a gagné
    public int gagnant() {
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
        return grille.ligne();
    }
    //Retourne le nombre de colonne
    public int colonne() {
        return grille.colonne();
    }
    //Retourne la grille
    public Grille grille() {
        return grille;
    }
    //Retourne la valeur de la case de la grille (de préférence utiliser les méthodes ci dessous)
    public int at(int l, int c) {
        return grille.at(l,c);
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
    public boolean pionJoueur (int joueur,int l,int c) {
        if(joueur==1) {
            return pionJoueur1(l, c);
        }
        else if(joueur==2) {
            return pionJoueur2(l, c);
        }
        return false;
    }
    public boolean pionJoueurAdverse(int l,int c) {
        if(joueur1()) {
            return pionJoueur2(l, c);
        }
        else if(joueur2()) {
            return pionJoueur1(l, c);
        }
        return false;
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
        //Remet à 0 les valeurs de controle
        precedenteDirection.changerDirection(EnumDirection.Inconnue);
        casesVisitees.clear();
        precedentPion = null;
    }

    //Pour mettre fin au tour
    // Le joueur peut-il passer son tour sans avoir joué?
    public void finTour() {
        changerJoueur();
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

    //Retourne le pion selectionne
    public Coordonnees pionSelectionne() {
        return pionSelectionne;
    }
    //Selection d'un pion
    public void selectionnerPion (Coordonnees C) {
        pionSelectionne = C;
    }

    //Renvoie la liste des pions du joueur 1
    public ArrayList<Coordonnees> listePionsJoueur1() {
        ArrayList<Coordonnees> pions = new ArrayList<Coordonnees>();
        for(int i=0;i<ligne();i++) { //Compte le nombre de pion de chaque joueur
            for(int j=0;j<colonne();j++) {
                if(pionJoueur1(i, j)) {
                    pions.add(new Coordonnees(i,j));
                }
            }
        }
        return pions;
    }
    //Renvoie la liste des pions du joueur 2
    public ArrayList<Coordonnees> listePionsJoueur2() {
        ArrayList<Coordonnees> pions = new ArrayList<Coordonnees>();
        for(int i=0;i<ligne();i++) { //Compte le nombre de pion de chaque joueur
            for(int j=0;j<colonne();j++) {
                if(pionJoueur2(i, j)) {
                    pions.add(new Coordonnees(i,j));
                }
            }
        }
        return pions;
    }


    //Retourne la liste des cases adjacentes de la case demandée
    public ArrayList<Coordonnees> casesAdjacentes(int l, int c) {
        ArrayList<Coordonnees> listCases = new ArrayList<Coordonnees>();
        //Les mouvement en haut, bas ,drotie, gauche
        if(caseExiste(l+1,c)) {
            listCases.add(new Coordonnees(l+1,c));
        }
        if(caseExiste(l,c-1)) {
            listCases.add(new Coordonnees(l,c-1));
        }
        if(caseExiste(l-1,c)) {
            listCases.add(new Coordonnees(l-1,c));
        }
        if(caseExiste(l,c+1)) {
            listCases.add(new Coordonnees(l,c+1));
        }
        if((l+c)%2==0) { //Cases où les déplacements en diagonales sont possibles
            if(caseExiste(l+1,c-1)) {
                listCases.add(new Coordonnees(l+1,c-1));
            }
            if(caseExiste(l+1,c+1)) {
                listCases.add(new Coordonnees(l+1,c+1));
            }
            if(caseExiste(l-1,c-1)) {
                listCases.add(new Coordonnees(l-1,c-1));
            }
            if(caseExiste(l-1,c+1)) {
                listCases.add(new Coordonnees(l-1,c+1));
            }
        }
        return listCases;
    }
    public ArrayList<Coordonnees> casesAdjacentes(Coordonnees C) {
        return casesAdjacentes(C.ligne(), C.colonne());
    }

    //Renvoie la liste des cases accessibles du pion donné:
    //Une case est accessible pour un pion si cette case est:
    //adjacente et libre et pas déjà visitée et dans une autre direction que le coup précédent
    //Et si un coup avec capture a déjà été joué, on peut rejouer seulement ce même pion
    public ArrayList<Coordonnees> casesAccessibles(int l, int c) {
        ArrayList<Coordonnees> listCases = new ArrayList<Coordonnees>();
        ArrayList<Coordonnees> listCasesAdj = casesAdjacentes(l,c); //Récupère les cases adjacentes
        int lN,cN;
        Direction dir;
        for(int i=0;i<listCasesAdj.size();i++) { //Pour chaque case adjacente
            lN = listCasesAdj.get(i).ligne(); //Coordonnées des cases adjacentes
            cN = listCasesAdj.get(i).colonne();
            dir = directionCoup(l,c,lN,cN);
            if( libre(lN,cN) && !dir.equals(precedenteDirection) && !casesVisitees.contains(listCasesAdj.get(i))
             && (precedentPion==null || (precedentPion.equals(new Coordonnees(l,c)) && 
             ( aspiration(l,c,lN,cN) && pionsCapturablesAspiration(l, c, lN, cN).size()>0 || percussion(l,c,lN,cN) && pionsCapturablesPercussion(l, c, lN, cN).size()>0    )))) {
                listCases.add(new Coordonnees(lN,cN));
            }            
        }
        return listCases;
    }
    public ArrayList<Coordonnees> casesAccessibles(Coordonnees C) {
        return casesAccessibles(C.ligne(), C.colonne());
    }

    //Retourne vrai si le coup est valide
    public boolean coupValide(int lPion,int cPion, int lDestination, int cDestination) {
        if( !caseExiste(lPion, cPion) ||(joueur1() && pionJoueur2(lPion, cPion)) || (joueur2() && pionJoueur1(lPion, cPion)) || libre(lPion,cPion) )  { //Pion incorrecte
            Configuration.instance().logger().info("Partie:coupValide - Le coup "+lPion+","+cPion+"->"+lDestination+","+cDestination+", pas un pion valide");
            return false;
        }
        ArrayList<Coordonnees> listCoord = casesAccessibles(lPion,cPion);
        if(!listCoord.contains(new Coordonnees(lDestination,cDestination))) { //Destination incorrecte
            //Code ci dessous seulement pour avoir des informations
            Direction dir = directionCoup(lPion,cPion,lDestination,cDestination);
            if(dir.equals(precedenteDirection)) {
                Configuration.instance().logger().info("Partie:coupValide - Le coup "+lPion+","+cPion+"->"+lDestination+","+cDestination+", même direction que le coup précédent");
            }
            else if(casesVisitees.contains(new Coordonnees(lDestination, cDestination))) {
                Configuration.instance().logger().info("Partie:coupValide - Le coup "+lPion+","+cPion+"->"+lDestination+","+cDestination+", case déjà visitée");
            }
            else if( precedentPion!=null && !precedentPion.equals(new Coordonnees(lPion,cPion))) {
                Configuration.instance().logger().info("Partie:coupValide - Le coup "+lPion+","+cPion+"->"+lDestination+","+cDestination+", vous devez rejouer le même pion ou finir le tour");
            }
            else if( (precedentPion!=null && (precedentPion.equals(new Coordonnees(lPion,cPion)) && 
            ( !percussion(lPion,cPion,lDestination,cDestination) ||  !aspiration(lPion, cPion, lDestination, cDestination))))) {
                Configuration.instance().logger().info("Partie:coupValide - Le coup "+lPion+","+cPion+"->"+lDestination+","+cDestination+", le coup doit être une capture, ou vous pouvez finir le tour");
            }
            return false;
        }
        return true;
    }

    //Retourne la direction du coup
    public Direction directionCoup(int lPion,int cPion, int lDestination, int cDestination) {
        Direction dir = new Direction(EnumDirection.Inconnue);
        if(lPion-1==lDestination && cPion-1==cDestination) {
            dir.changerDirection(EnumDirection.HautGauche);
        }
        else if(lPion-1==lDestination && cPion==cDestination) {
            dir.changerDirection(EnumDirection.Haut);
        }
        else if(lPion-1==lDestination && cPion+1==cDestination) {
            dir.changerDirection(EnumDirection.HautDroite);
        }
        else if(lPion==lDestination && cPion-1==cDestination) {
            dir.changerDirection(EnumDirection.Gauche);
        }
        else if(lPion==lDestination && cPion+1==cDestination) {
            dir.changerDirection(EnumDirection.Droite);
        }
        else if(lPion+1==lDestination && cPion-1==cDestination) {
            dir.changerDirection(EnumDirection.BasGauche);
        }
        else if(lPion+1==lDestination && cPion==cDestination) {
            dir.changerDirection(EnumDirection.Bas);
        }
        else if(lPion+1==lDestination && cPion+1==cDestination) {
            dir.changerDirection(EnumDirection.BasDroite);
        }
        return dir;
    }

    //Retourne vrai si le coup est une percussion
    public boolean percussion(int lPion,int cPion, int lDestination, int cDestination) {
        Direction dir = directionCoup(lPion, cPion, lDestination, cDestination); //La direction du coup
        Coordonnees C = dir.coordonnees();
        //On regarde si dans la case suivante est un pion adverse
        if(caseExiste(lDestination+C.ligne(), cDestination+C.colonne()) && pionJoueurAdverse(lDestination+C.ligne(), cDestination+C.colonne())) {
            return true;
        }
        return false;
    }

    //Retourne vrai si le coup est une aspiration
    public boolean aspiration(int lPion,int cPion, int lDestination, int cDestination) {
        Direction dir = directionCoup(lPion, cPion, lDestination, cDestination); //La direction du coup
        Coordonnees C = dir.coordonnees();
        C.oppose();
        if(caseExiste(lPion+C.ligne(), cPion+C.colonne()) && pionJoueurAdverse(lPion+C.ligne(), cPion+C.colonne())) {
            return true;
        }
        return false;
    }
    //Renvoie vrai s'il y a une aspiration et une percussion
    public boolean aspirationPercution(int lPion,int cPion, int lDestination, int cDestination) {
        return percussion(lPion, cPion, lDestination, cDestination) && aspiration(lPion, cPion, lDestination, cDestination);
    }

    //Retourne vrai si le pion peut capturer des pions adverses
    public boolean peutCapturer(int lPion,int cPion) {
        boolean b = false;
        ArrayList<Coordonnees> listeDest;
        listeDest = casesAdjacentes(lPion, cPion);
        for (Coordonnees cDest: listeDest) { //Pour chaque destination
            if(percussion(lPion, cPion, cDest.ligne(), cDest.colonne()) && pionsCapturablesPercussion(lPion, cPion, cDest.ligne(), cDest.colonne()).size()>0) {
                b = true;
            }
            else if (aspiration(lPion, cPion, cDest.ligne(), cDest.colonne()) && pionsCapturablesAspiration(lPion, cPion, cDest.ligne(), cDest.colonne()).size()>0) {
                b = true;
            }
        }
        return b;
    }    

    // Retourne la liste des pions capturables avec le coup donné
    public ArrayList<Coordonnees> pionsCapturables(int lPion,int cPion, int lDestination, int cDestination) {
        if(aspirationPercution(lPion, cPion, lDestination, cDestination)) { //Erreur, il faut choisir une des 2 captures
            Configuration.instance().logger().warning("Partie:pionsCapturables - Le coup est un aspiration et une percution, il faut choisir un des deux");
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
            Configuration.instance().logger().warning("Partie:pionsCapturablesPercussion - pas de percussion");
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
            Configuration.instance().logger().warning("Partie:pionsCapturablesPercussion - pas d'aspiration");
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
            if(caseExiste(lSuivant, cSuivant) && ((joueur1() && pionJoueur2(lSuivant, cSuivant))||(joueur2() && pionJoueur1(lSuivant, cSuivant)))) {
                listePions.add(new Coordonnees(lSuivant,cSuivant));
            }
            else { //Plus de pions à explorer
                suivant = false;
            }
        }
        return listePions;
    }


    //Joue un pion vers une case, renvoie vrai si le coup s'est fait correctement, faux s'il y a eu un problème
    //Cpature: 0 si pas de capture, 1 si percussion, 2 si aspiration
    public boolean jouer(int lPion,int cPion, int lDestination, int cDestination,int capture) {
        if(!coupValide(lPion, cPion, lDestination, cDestination)) { //Coup invalide
            // Configuration.instance().logger().warning("Partie:jouer - Coup impossible: "+lPion+","+cPion+"->"+lDestination+","+cDestination);
            return false;
        }
        Configuration.instance().logger().info("Partie:jouer - Coup effectué: "+lPion+","+cPion+"->"+lDestination+","+cDestination);
        if(capture==0) {//pas de capture
            enleverPion(lPion, cPion); //On enlève le pion joué
            placerPion(joueur(), lDestination, cDestination); //On le place à l'endroit voulu
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
                Configuration.instance().logger().info("Partie:jouer - Pion tué: "+pionAEnlever.l+" "+pionAEnlever.c);
            }

            enleverPion(lPion, cPion); //On enlève le pion joué
            placerPion(joueur(), lDestination, cDestination); //On le place à l'endroit voulu

            precedentPion = new Coordonnees(lDestination,cDestination); //Sauvegarde le pion qu'on vient de jouer
            precedenteDirection = directionCoup(lPion, cPion, lDestination, cDestination); //On sauvegarde la direction
            casesVisitees.add(new Coordonnees(lPion,cPion));
            if(casesAccessibles(lDestination, cDestination).size()==0) { //On regarde si le joueur peut encore jouer le pion
                changerJoueur(); //On change de joueur si aucun coup n'est possible
            }
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
        System.out.println();
    }
}