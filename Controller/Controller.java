package Controller;

import Modele.*;
import View.*; 
import java.util.*;
import javafx.scene.input.MouseEvent;
public class Controller{
        protected int i=0;
	protected Partie m_game;
	protected List<Coordonnees> clickHist;	
	protected int m_player;


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


	public boolean jouer(Coup coup){
		if(coup != null){
			return m_game.jouer(coup);
		}
		else{
			return false;
		}
	}
	
	//Gestion du click dans la zone de jeu
	public void click(MouseEvent e, GridView gv){
                Coordonnees coord;
                coord = new Coordonnees((int)(e.getSceneY()/gv.getTileSizeY()), (int)(e.getSceneX()/gv.getTileSizeX()));
                System.out.println(m_game.joueur());
		if(m_game.grille().at(coord) == (m_player==1?1:2)){
			clickHist.clear();
			clickHist.add(coord);
			m_game.selectionnerPion(coord);
		}
		else{
			clickHist.add(coord);
			if(clickHist.size() == 2){
				int capt = getCapture(clickHist);
				if(capt!=-1) { //Il n'y a pas le choix entre percution aspiration
					Coup coup = new Coup(clickHist.get(0),clickHist.get(1), capt);
					jouer(coup);
					
					clickHist.clear();
					m_game.selectionnerPion(null);
				}
			}
			if(clickHist.size() == 3){
				Coup coup = new Coup(clickHist.get(0),clickHist.get(1), getCapture(clickHist));
				jouer(coup);
				
				clickHist.clear();
				m_game.selectionnerPion(null);
			}
		}
	}

	//determine la capture adequat 
	protected int getCapture(List<Coordonnees> cHist){
		Coup c = new Coup(cHist.get(0),cHist.get(1));
		if(m_game.aspirationPercution(c)&& cHist.size()==3){
			if(cHist.get(2).equals(cHist.get(1).somme(c.direction().coordonnees())))
				return 1;
			else
				return 2;
		}
		else if (m_game.aspirationPercution(c)&& cHist.size()==2) { //Besoin de savoir percussion ou aspiration
			return -1;
		}
		else if(m_game.aspiration(c)){
			return 2;
		}
		else if(m_game.percussion(c)){
			return 1;
		}
		return 0;
	}

}


