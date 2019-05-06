package Controller;

import Modele.*;
import View.*; 
import java.util.*;
import javafx.scene.input.MouseEvent;
public class Controller{

	Partie m_game;
	List<Coordonnees> clickHist;	
	int m_player;


	public Controller(int player){
		m_game = new Partie();
		m_player = player;
		clickHist = new ArrayList<>();
	}

	public Controller(int player, Partie game){
		m_game = game;
		m_player = player;
		clickHist = new ArrayList<>();

	}


	public boolean jouer(Action a){
		Coordonnees pos = a.getPos();
		return m_game.jouer(pos.ligne(),pos.colonne(),pos.ligne() + a.getDir().ligne(),pos.colonne() + a.getDir().colonne(),a.getAction());

	}
	
	//Gestion du click dans la zone de jeu
	public void click(MouseEvent e, GridView gv){
		Coordonnees coord = new Coordonnees((int)(e.getSceneY()/gv.getTileSizeY()), (int)(e.getSceneX()/gv.getTileSizeX()));
		System.out.println(m_game.joueur());
		//printCoord(coord);
		clickHist.add(coord);
		if(clickHist.size() == 3){
			Action a = new Action(clickHist.get(0),new Coordonnees(clickHist.get(1).ligne() - clickHist.get(0).ligne(), clickHist.get(1).colonne() - clickHist.get(0).colonne()), getCapture(clickHist));
			jouer(a);
				
			System.out.println(getCapture(clickHist));
			m_game.afficher();
			clickHist.clear();

		}
	}

	private void printCoord(Coordonnees coord){
		System.out.println("X: " + coord.colonne() + "\tY: " + coord.ligne() + "\tJ: " + _at(coord.ligne(),coord.colonne()));
	}

	//determine la capture adequat 
	protected int getCapture(List<Coordonnees> cHist){
		int dx1,dy1, dx2,dy2;
		dx1 = cHist.get(1).colonne() - cHist.get(0).colonne();
		dy1 = cHist.get(1).ligne() - cHist.get(0).ligne();
		dx2 = cHist.get(2).colonne() - cHist.get(0).colonne();
		dy2 = cHist.get(2).ligne() - cHist.get(0).ligne();

		if(cHist.get(1) == cHist.get(2)){
			return 0;
		}
		else if((dx1 > 0)==(dx2 > 0) && (dy1 >0)==(dy2>0)){
			return 1;
		}
		else{
			return 2;
		}
		
	}

	public int _at(int l, int c){
		int r = 10;
		if( m_game.libre(l,c)){
			r = 0;
		}
		if(m_game.pionJoueur1(l,c)){
			r = 1;
		}
		if(m_game.pionJoueur2(l,c)){
			r = -1;
		}
		return r;
	}

	protected void changePlayer(){
		m_player *= -1;
	}

	public boolean canPlay(){
		return (m_game.joueur1() && m_player == 1) || (m_game.joueur2() && m_player == -1);
	}

	public boolean at(int l, int c){
		return (m_game.pionJoueur1(l,c) && m_player == 1) || (m_game.pionJoueur2(l,c) && m_player == -1);
	}
	public boolean nat(int l, int c){
		return (m_game.pionJoueur1(l,c) && m_player == -1) || (m_game.pionJoueur2(l,c) && m_player == 1);
	}

}


