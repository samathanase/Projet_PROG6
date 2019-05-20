package Modele;


import java.util.Random;
import java.io.Serializable;
import java.util.ArrayList;
import Configuration.Configuration;

/*
    La base du jeu 
*/

/*
    TODO: ne plus changer de joueur automatique à la fin du tour, demander au joueur de mettre fin au tour
    Pouvoir annuler les coups qu'on fait uniquement durant son tour
*/

public class Partie implements Serializable {
    private static final long serialVersionUID = 696479903953286766L;

    private Grille grille;
    public int [][] tab; //Le tableau: 0:case libre, 1:pion joueur 1, 2:pion joueur 2
    public int joueur; //Le joueur qui doit jouer
    private Random rand; //Pour le tirage aléatoire du joueur
    private Direction precedenteDirection; //Précédente direction que le joueur a fait dans le tour
    private ArrayList<Coordonnees> casesVisitees; //Cases visitées dans le tour
    private Coordonnees precedentPion; //le précédent pion joué dans le tour
    private Coordonnees pionSelectionne;
	private Partie parent;

    private Historique historique;

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

	//-------------------------------
	public Partie(Partie copy){
		grille = new Grille(copy.grille);
	 	tab = grille.tab();
		parent = copy;
    		joueur = copy.joueur; 
    		rand = new Random(); 
    		precedenteDirection = new Direction(copy.precedenteDirection); 
   		casesVisitees = new ArrayList<Coordonnees>();
		for(int i = 0; i < copy.casesVisitees.size(); i++){
			casesVisitees.add(new Coordonnees(copy.casesVisitees.get(i)));
		} 
		if(copy.precedentPion != null){
    			precedentPion = new Coordonnees(copy.precedentPion); 
		}
		if(copy.pionSelectionne != null){
    			pionSelectionne = new Coordonnees(copy.pionSelectionne);
        }
        historique = new Historique(copy.historique());
	}
	
	public Partie getParent(){
		return parent;
	}

	public void setGrille(Grille grid){
		grille = grid;
		tab = grille.tab();
	}

	public Grille getUniqueState(){
		Grille ret = new Grille(grille);
		for(int i = 0; i < ret.ligne(); i++){
			for(int j = 0; j < ret.colonne(); j++){
				if(ret.at(i,j) == joueur){
					ret.set(1,i,j);
					if(precedentPion != null && pionJoueur(joueur,precedentPion)){
						ret.set(2,i,j);
						ArrayList<Coordonnees> adjs = casesAccessibles(i,j);
						for(int k = 0; k < adjs.size(); k++){
							if(casesVisitees.contains(adjs.get(k))){
								ret.set(3,adjs.get(k));
							}
						}
					}
				}
				else if(ret.at(i,j) != 0){
					ret.set(-1,i,j);
				}	
			}
		}
		return ret;
	}
	//-------------------------------

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
        historique = new Historique();
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

    //Retourne l'historique
    public Historique historique() {
        return historique;
    }

    public void mettreHistorique(Historique hist) {
        historique = hist;
    }

    //Retourne vrai si la case est libre
    public boolean libre (int l,int c) {
        return tab[l][c] == 0;
    }
    public boolean libre (Coordonnees C) {
        return libre(C.ligne(),C.colonne());
    }
    //Retourne vrai si la case est un pion du joueur 1
    public boolean pionJoueur1 (int l,int c) {
        return tab[l][c] == 1;
    }
    public boolean pionJoueur1 (Coordonnees C) {
        return pionJoueur1(C.ligne(),C.colonne());
    }
    //Retourne vrai si la case est un pion du joueur 2
    public boolean pionJoueur2 (int l,int c) {
        return tab[l][c] == 2;
    }
    public boolean pionJoueur2 (Coordonnees C) {
        return pionJoueur2(C.ligne(),C.colonne());
    }
    public boolean pionJoueur(int joueur,int l,int c) {
        if(joueur==1) {
            return pionJoueur1(l, c);
        }
        else if(joueur==2) {
            return pionJoueur2(l, c);
        }
        return false;
    }
    public boolean pionJoueur (int joueur,Coordonnees C) {
        return pionJoueur(joueur,C.ligne(),C.colonne());
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
    public boolean pionJoueurAdverse (Coordonnees C) {
        return pionJoueurAdverse(C.ligne(),C.colonne());
    }
    //Renvoie vrai la case existe sur le plateau
    public boolean caseExiste (int l,int c) {
        return l>=0 && l< ligne() && c>=0 && c< colonne();
    }
    public boolean caseExiste (Coordonnees C) {
        return caseExiste(C.ligne(),C.colonne());
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

    //retourne vrai si le joueur peut mettre fin à son tour
    public boolean peutFinTour() {
        boolean b=false;
        if(listeCoupsValides().size()==0) { //Plus de coup possibles
            b = true;
        }
        else if(precedentPion!=null) { //Le joueur arrète un enchainement de coups
            b = true;
        }
        return b;
    }

    //Placer le pion pour le joueur
    private void placerPionJouer1(int l,int c) {
        tab[l][c] = 1;
    }
    private void placerPionJouer1(Coordonnees C) {
        placerPionJouer1(C.ligne(),C.colonne());
    }
    private void placerPionJouer2(int l,int c) {
        tab[l][c] = 2;
    }
    private void placerPionJouer2(Coordonnees C) {
        placerPionJouer2(C.ligne(),C.colonne());
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
    private void placerPion(int joueur,Coordonnees C) {
        placerPion(joueur,C.ligne(),C.colonne());
    }
    //Enlève le pion
    private void enleverPion(int l,int c) {
        tab[l][c] = 0;
    }
    private void enleverPion(Coordonnees C) {
        enleverPion(C.ligne(),C.colonne());
    }

    //Retourne le pion selectionne
    public Coordonnees pionSelectionne() {
        return pionSelectionne;
    }
    //Selection d'un pion
    public void selectionnerPion(Coordonnees C) {
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
    public ArrayList<Coordonnees> casesAccessibles(Coordonnees C) {
        ArrayList<Coordonnees> listCases = new ArrayList<Coordonnees>();
        ArrayList<Coordonnees> listCasesAdj = casesAdjacentes(C); //Récupère les cases adjacentes
        int lN,cN;
        Coup coup; 
        Coordonnees cSuivant;
        Direction dir;
        for(int i=0;i<listCasesAdj.size();i++) { //Pour chaque case adjacente
            lN = listCasesAdj.get(i).ligne(); //Coordonnées des cases adjacentes
            cN = listCasesAdj.get(i).colonne();
            cSuivant = new Coordonnees(lN,cN);
            coup = new Coup(C,cSuivant);
            dir = coup.direction();
            if( libre(cSuivant) && !dir.equals(precedenteDirection) && !casesVisitees.contains(listCasesAdj.get(i))
             && (precedentPion==null || (precedentPion.equals(C) && ( aspiration(coup) || percussion(coup) )))) {
                listCases.add(new Coordonnees(lN,cN));
            }            
        }
        return listCases;
    }
    public ArrayList<Coordonnees> casesAccessibles(int lPion,int cPion) {
        return casesAccessibles(new Coordonnees(lPion,cPion));
    }

    //Retourne vrai si le coup est valide
    public boolean coupValide(Coup coup) {
        if( !caseExiste(coup.pion()) ||(joueur1() && pionJoueur2(coup.pion())) || (joueur2() && pionJoueur1(coup.pion())) || libre(coup.pion()) )  { //Pion incorrecte
            Configuration.instance().logger().info("Partie:coupValide - Le coup "+coup+", pas un pion valide");
            return false;
        }
        ArrayList<Coordonnees> listCoord = casesAccessibles(coup.pion());
        if(!listCoord.contains(coup.destination())){ //Destination incorrecte
            //Code ci dessous seulement pour avoir des informations
            Direction dir = coup.direction();
            if(dir.equals(precedenteDirection)) {
                Configuration.instance().logger().info("Partie:coupValide - Le coup "+coup+", même direction que le coup précédent");
            }
            else if(casesVisitees.contains(coup.destination())){
                Configuration.instance().logger().info("Partie:coupValide - Le coup "+coup+", case déjà visitée");
            }
            else if( precedentPion!=null && !precedentPion.equals(coup.pion())) {
                Configuration.instance().logger().info("Partie:coupValide - Le coup "+coup+", vous devez rejouer le même pion ou finir le tour");
            }
            else if( (precedentPion!=null && (precedentPion.equals(coup.pion()) && 
            ( !percussion(coup) ||  !aspiration(coup))))) {
                Configuration.instance().logger().info("Partie:coupValide - Le coup "+coup+", le coup doit être une capture, ou vous pouvez finir le tour");
            }
            return false;
        }
        return true;
    }

    //Retourne vrai s'il n'y a pas de capture
    public boolean pasCapture(Coup coup) {
        return !percussion(coup) && !aspiration(coup);
    }

    //Retourne vrai si le coup est une percussion
    public boolean percussion(Coup coup) {
        Direction dir = coup.direction(); //La direction du coup
        Coordonnees C = dir.coordonnees();
        //On regarde si dans la case suivante est un pion adverse
        if(caseExiste(coup.destination().somme(C)) && pionJoueurAdverse(coup.destination().somme(C))){
            return true;
        }
        return false;
    }

    //Retourne vrai si le coup est une aspiration
    public boolean aspiration(Coup coup) {
        Direction dir = coup.direction(); //La direction du coup
        Coordonnees C = dir.coordonnees();
        C.oppose();
        if(caseExiste(coup.pion().somme(C)) && pionJoueurAdverse(coup.pion().somme(C))) {
            return true;
        }
        return false;
    }
    //Renvoie vrai s'il y a une aspiration et une percussion
    public boolean aspirationPercution(Coup coup) {
        return percussion(coup) && aspiration(coup);
    }

    // // Retourne vrai si le pion peut capturer des pions adverses
    // public boolean peutCapturer(int lPion,int cPion) {
    //     boolean b = false;
    //     ArrayList<Coordonnees> listeDest;
    //     listeDest = casesAdjacentes(lPion, cPion);
    //     for (Coordonnees cDest: listeDest) { //Pour chaque destination
    //         if(percussion(lPion, cPion, cDest.ligne(), cDest.colonne())) {
    //             b = true;
    //         }
    //         else if (aspiration(lPion, cPion, cDest.ligne(), cDest.colonne()) {
    //             b = true;
    //         }
    //     }
    //     return b;
    // }

    // Retourne la liste des pions capturables avec le coup donné
    public ArrayList<Coordonnees> pionsCapturables(Coup coup) {
        if(aspirationPercution(coup)) { //Erreur, il faut choisir une des 2 captures
            Configuration.instance().logger().warning("Partie:pionsCapturables - Le coup est une aspiration et une percution, il faut choisir un des deux");
            return new ArrayList<Coordonnees>();
        }
        else if(percussion(coup)) {
            return pionsCapturablesPercussion(coup);
        }
        else if(aspiration(coup)) {
            return pionsCapturablesAspiration(coup);
        }
        return new ArrayList<Coordonnees>();
    }

    // Retourne la liste des pions capturables avec le coup donné
    public ArrayList<Coordonnees> pionsCapturablesPercussion(Coup coup) {
        ArrayList<Coordonnees> listePions =  new ArrayList<Coordonnees>();
        if(!percussion(coup)) { //Erreur
            Configuration.instance().logger().warning("Partie:pionsCapturablesPercussion - pas de percussion");
            return listePions;
        }
        Direction dir = coup.direction(); //La direction du coup
        Coordonnees cDep = dir.coordonnees(); //La direction sous forme de coordonnées
        return pionsCapturablesParcours(coup.destination(), cDep);        
    }

    // Retourne la liste des pions capturées avec le coup donné
    public ArrayList<Coordonnees> pionsCapturablesAspiration(Coup coup) {
        ArrayList<Coordonnees> listePions =  new ArrayList<Coordonnees>();
        if(!aspiration(coup)) { //Erreur
            Configuration.instance().logger().warning("Partie:pionsCapturablesPercussion - pas d'aspiration");
            return listePions;
        }
        Direction dir = coup.direction(); //La direction du coup
        Coordonnees cDep = dir.coordonnees();
        cDep.oppose(); //On va dans le sens opposé
        return pionsCapturablesParcours(coup.pion(), cDep);
    }

    //Méthode utiliser par les 2 méthodes au dessus pour savoir les pions capturables
    private ArrayList<Coordonnees> pionsCapturablesParcours(Coordonnees pion,Coordonnees cDep) {
        ArrayList<Coordonnees> listePions =  new ArrayList<Coordonnees>();
        boolean suivant = true;
        int lSuivant = pion.ligne();
        int cSuivant = pion.colonne();
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

    // Retourne la liste des coups possibles pour le joueur courant
    public ArrayList<Coup> listeCoupsValides() {
        ArrayList<Coordonnees> pions;
        ArrayList<Coup> coups = new ArrayList<Coup>(); //La liste des coups
        Coup coup ; 
        ArrayList<Coordonnees> casesAccess;
        if(joueur1()) {
            pions = listePionsJoueur1(); //Récupère la liste des pions du joueur courant
        }
        else {
            pions = listePionsJoueur2();
        }

        for(Coordonnees p : pions) { //Pour tous les pions
            casesAccess = casesAccessibles(p); //On récupère les cases accessibles
            for(Coordonnees caseS : casesAccess) { //Pour chaque case accessible
                coup = new Coup(p,caseS);
                if(percussion(coup)) {
                    coups.add(new Coup(p,caseS,1));
                }
                if(aspiration(coup)) {
                    coups.add(new Coup(p,caseS,2));
                }
                if(pasCapture(coup)) {
                    coups.add(new Coup(p,caseS,0));
                }
            }
        }
        return coups;
    }

    //Joue un pion vers une case, renvoie vrai si le coup s'est fait correctement, faux s'il y a eu un problème
    public boolean jouer(Coup coup) {
        if(!coupValide(coup)) { //Coup invalide
            Configuration.instance().logger().warning("Partie:jouer - Coup impossible: "+coup);
            return false;
        }
        Configuration.instance().logger().info("Partie:jouer - Coup effectué: "+coup);
        if(pasCapture(coup)) {//pas de capture
            historique.ajouter(new CoupHistorique(coup,0,joueur,precedenteDirection,precedentPion));
            enleverPion(coup.pion()); //On enlève le pion joué
            placerPion(joueur(), coup.destination()); //On le place à l'endroit voulu
            changerJoueur(); //C'est au joueur suivant
        }
        else { //Capture
            ArrayList<Coordonnees> listePions = new ArrayList<Coordonnees>();

            if(coup.percussion()) { //percussion
                listePions = pionsCapturablesPercussion(coup);
            }
            else if(coup.aspiration()) { //aspiration
                listePions = pionsCapturablesAspiration(coup);
            }
            else { //Laisse la partie déterminer si c'est une percussion ou une aspiration (Attention ça ne doit pas être les 2 à la fois )
                listePions = pionsCapturables(coup);
            }

            for (Coordonnees pionAEnlever : listePions) { //On enlève les pions du joueur adverse
                enleverPion(pionAEnlever.l, pionAEnlever.c);
                Configuration.instance().logger().info("Partie:jouer - Pion tué: "+pionAEnlever.l+" "+pionAEnlever.c);
            }
            historique.ajouter(new CoupHistorique(coup,listePions.size(),joueur,precedenteDirection,precedentPion)); //Ajout du coup dans l'historique

            enleverPion(coup.pion()); //On enlève le pion joué
            placerPion(joueur(), coup.destination()); //On le place à l'endroit voulu

            precedentPion = coup.destination(); //Sauvegarde le pion qu'on vient de jouer
            precedenteDirection = coup.direction(); //On sauvegarde la direction

            casesVisitees.add(coup.pion());
            if(casesAccessibles(coup.destination()).size()==0) { //On regarde si le joueur peut encore jouer le pion
		if(gagnant() == 0){
               		changerJoueur(); //On change de joueur si aucun coup n'est possible
		}
            }

        }
        return true;
    }

    // retourne vrai si le joueur peut annuler son coup
    public boolean peutAnnuler() {
        return historique.peutAnnuler();
    }

    public boolean peutRefaire() {
        return historique.peutRefaire();
    }

    //Annule le dernier coup joué
    //Modifie directement la partie
    public void annuler(){
        if (historique.peutAnnuler()) {
            CoupHistorique coup = historique.coupAnnuler(); //On récupère le coup
            Configuration.instance().logger().info("Annulation du coup: "+coup);

            enleverPion(coup.destination()); //On déplace le pion joué
            placerPion(coup.joueur(), coup.pion());

            //Change le joueur si besoin
            if( joueur()!=coup.joueur()) {
                joueur = coup.joueur();
            }
            else { //C'était le même joueur
                precedentPion = coup.precedentPion();
                precedenteDirection = coup.precedenteDirection();
                casesVisitees.remove(coup.pion()); //Enlève la case visitée
            }

            Direction dir = coup.direction();
            Coordonnees coor = dir.coordonnees();
            Coordonnees coorPion = null;
            if(coup.aspiration()) {
                coorPion = new Coordonnees(coup.pion());
                coor.oppose();
            }
            else {
                coorPion = new Coordonnees(coup.destination());
            }
            for(int i=0;i<coup.pionsCaptures();i++) { //Pour chaque pion capturé
                coorPion.plus(coor); //Les coordonnées du pion à placer
                placerPion(joueur==1 ? 2:1, coorPion); //On replace le pion adverse
            }
        }
        else {
            Configuration.instance().logger().warning("Impossible d'annuler le coup");
        }
    }

    //Refaire le dernier coup annulé
    //Modifie directement la partie
    public void refaire() {
        if (historique.peutRefaire()) {
            Coup coup = historique.coupRefaire();
            Configuration.instance().logger().info("Refait le coup: "+coup);
            @SuppressWarnings("unchecked") //TODO régler plus proprement la gestion de refaire
            ArrayList<CoupHistorique> c = (ArrayList<CoupHistorique>) historique.tabRefaire.clone(); //Alerte c'est du code dégueulasse
            jouer(coup); //Joue le coup a refaire
            historique().tabRefaire = c;
        }
        else {
            Configuration.instance().logger().warning("Impossible de refaire le coup");
        }
    }




    public String toString() {
        if(Configuration.instance().lis("affichageConsole").equals("simple")) {
            return toStringSimple();
        }
        else {
            return toStringComplet();
        }
    }

    //Convertit la grille en chaine de caractère
    public String toStringSimple() {
        String str = new String();
        int l=0;
        System.out.print("  ");
        for(int i=0;i<colonne();i++) { 
            str+=i+" ";
        }
        str+="\n";
        for(int i=0;i<ligne();i++) { 
            str+=l+" ";
            l++;
            for(int j=0;j<colonne();j++) {
                if(pionJoueur1(i, j)) {
                    str+="● ";
                }
                else if(pionJoueur2(i, j)) {
                    str+="○ ";
                }
                else {
                    str+=". ";
                } 
            }
            str+="\n";
        }
        str+="\n";        
        return str;
    }

    //Affiche la grille dans la console
    public void afficher() {
        System.out.print(toString());
    }

    //Convertit la grille en chaine de caractère plus complexe
    public String toStringComplet() {
        String str = new String();
        int l=0;
        System.out.print("  ");
        for(int i=0;i<colonne();i++) { 
            str+=i+" ";
        }
        str+="\n";
        for(int i=0;i<ligne();i++) { 
            str+=l+" ";
            l++;
            for(int j=0;j<colonne();j++) {
                if(pionJoueur1(i, j)) {
                    str+="●";
                    if(j<colonne()-1)
                        str+="─";
                }
                else if(pionJoueur2(i, j)) {
                    str+="○";
                    if(j!=colonne()-1)
                        str+="─";
                }
                else { //case vide
                    if(i==0 && j==0)
                        str+="┌─";
                    else if(i==0 && j==colonne()-1)
                        str+="┐";
                    else if(i==ligne()-1 && j==0)
                        str+="└─";
                    else if(i==ligne()-1 && j==colonne()-1)
                        str+="┘";
                    else if(i==0)
                        str+="┬─";
                    else if(i==ligne()-1)
                        str+="┴─";
                    else if(j==colonne()-1)
                        str+="┤";
                    else if(j==0)
                        str+="├─";
                    else
                        str+="┼─";

                } 
            }
            if(i<ligne()-1) {//Affichage de l'interligne
                str+="\n  ";
                for(int j=0;j<colonne();j++) {
                    if(i%2==0) {
                        if(j%2==0 && j<colonne()-1)
                            str+="│\\";
                        else if(j%2==1 && j<colonne()-1)
                            str+="│/";
                        else
                            str+="│";
                    }
                    else {
                        if(j%2==0 && j<colonne()-1)
                            str+="│/";
                        else if(j%2==1 && j<colonne()-1)
                            str+="│\\";
                        else
                            str+="│";
                    }
                }
            }
            str+="\n";        
        }
        str+="\n";        
        return str;
    }
}