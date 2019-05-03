package Controller;

import Modele.*;
import View.*; 
import java.util.*;
import javafx.scene.input.MouseEvent;
public class Controller{

	int m_player;	
	Partie m_game;
	List<Coordonnees> clickHist;	


	public Controller(){
		m_player = 1;
		m_game = new Partie();
		clickHist = new ArrayList<>();
	}


	public boolean jouer(Action a){
		Coordonnees pos = a.getPos();
		return m_game.jouer(pos.ligne(),pos.colonne(),pos.ligne() + a.getDir().ligne(),pos.colonne() + a.getDir().colonne(),a.getAction());

	}
	
	//Gestion du click dans la zone de jeu
	public void click(MouseEvent e, GridView gv){
		Coordonnees coord = new Coordonnees((int)(e.getSceneY()/gv.getTileSizeY()), (int)(e.getSceneX()/gv.getTileSizeX()));
		System.out.println("X: " + coord.colonne() + "\tY: " + coord.ligne() + "\tJ: " + _at(coord.ligne(),coord.colonne()));
		clickHist.add(coord);
		if(clickHist.size() == 3){
			Action a = new Action(clickHist.get(0),new Coordonnees(clickHist.get(1).ligne() - clickHist.get(0).ligne(), clickHist.get(1).colonne() - clickHist.get(0).colonne()), getCapture(clickHist));
			jouer(a);
			clickHist.clear();
		}
	}

	//determine la capture adequat 
	protected int getCapture(List<Coordonnees> cHist){
		
		return 0;
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

	public void changePlayer(){
		m_player *= -1;
	}

	

}


