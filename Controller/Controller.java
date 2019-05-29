package Controller;

import Modele.*;
import View.*; 
import java.util.*;
import javafx.scene.input.MouseEvent;
public class Controller{
        protected int i=1;
	protected Partie m_game;
	protected List<Coordonnees> clickHist;	
	protected int m_player;

        public static Coordonnees cra;
        public static int case_clique=0;
        
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
                case_clique=0;
                Coordonnees coord;
                coord = new Coordonnees((int)(e.getSceneY()/gv.getTileSizeY())-1, (int)(e.getSceneX()/gv.getTileSizeX())-1);
                System.out.println(m_game.joueur());
		//printCoord(coord);
                
		if(m_game.grille().at(coord) == m_game.joueur()){
			clickHist.clear();
			clickHist.add(coord);
			m_game.selectionnerPion(coord);
                        case_clique=0;
		}
		else{
                        case_clique=0;
			clickHist.add(coord);
                        if(m_game.libre(coord)){
                            case_clique=1;
                            cra=coord;
                        }
                	//System.out.println("***************");
                	//printCoord(coord);
			if(clickHist.size() == 3){
                            case_clique=0;
				Coup coup = new Coup(clickHist.get(0),clickHist.get(1), getCapture(clickHist));
				jouer(coup);
				
				//System.out.println(getCapture(clickHist));
				//m_game.afficher();

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


