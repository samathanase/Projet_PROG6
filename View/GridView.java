package View;

import Controller.Controller;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import Modele.*;
import fanorona_prog06.PvP_board;
import static fanorona_prog06.PvP_board.en_cours;
import static fanorona_prog06.PvP_board.p1;
import static fanorona_prog06.PvP_board.p2;
import javafx.scene.image.Image;
public class GridView{

	Partie m_game;
	double tileSizeX,tileSizeY;

	public GridView(){
		m_game = new Partie();
	}

	public GridView(Partie game){

		m_game = game;
	}
	
	//retourne les largeurs et longueurs des tiles, les pions sont au centre des tiles
	public double getTileSizeX(){
		return tileSizeX;
	}

	public double getTileSizeY(){
		return tileSizeY;
	}
	
	

	public void draw(Canvas cnv){
            int l_p = 0,l_c = 0;
		GraphicsContext gc = cnv.getGraphicsContext2D();


		tileSizeX = (cnv.getWidth())/m_game.colonne() ;
		tileSizeY = (cnv.getHeight())/m_game.ligne() ;

		gc.clearRect(0,0,cnv.getWidth(),cnv.getHeight());		

                
                //Affichage du nom du joueur qui a la main
                if (m_game.joueur==1){
                    en_cours.setText(p1);
                }else{
                    en_cours.setText(p2);
                }
                
                
		//Affichage des select 
		if(m_game.pionSelectionne() != null){
			for(int x = 0; x < m_game.colonne(); x++){
				for(int y = 0; y < m_game.ligne(); y++){
					if(m_game.pionSelectionne().equals(new Coordonnees(y,x))){
						double thickness = 5;
						//gc.setFill(Color.BLACK);
                                                gc.setFill(Color.web("06ff00"));
						gc.fillOval((x+0.25)*tileSizeX-thickness,(y+0.25)*tileSizeY-thickness,tileSizeX/2+2*thickness,tileSizeY/2+2*thickness);
                                                l_p=y;
                                                l_c=x;
					}
				}
			}
		}
                Coordonnees crd = new Coordonnees(l_p,l_c);
                
                //Affichage des pions qui peuvent etres mangés
                Image mgp = new Image(getClass().getResourceAsStream("pions/mangeable_perc.png"));
                Image mga = new Image(getClass().getResourceAsStream("pions/mangeable_asp.png"));
                
                if(Controller.case_clique==1){
                    for(int x = 0; x < m_game.colonne(); x++){
                        for(int y = 0; y < m_game.ligne(); y++){
                            if(m_game.pionsCapturablesPercussion(new Coup(crd,Controller.cra,-1)).contains(new Coordonnees(y,x))){
                                gc.drawImage(mgp,(x+0.25)*tileSizeX-5,(y+0.25)*tileSizeY-5, tileSizeX/2+2*5, tileSizeY/2+2*5);
                            }
                            if(m_game.pionsCapturablesAspiration(new Coup(crd,Controller.cra,-1)).contains(new Coordonnees(y,x))){
                                gc.drawImage(mga,(x+0.25)*tileSizeX-5,(y+0.25)*tileSizeY-5, tileSizeX/2+2*5, tileSizeY/2+2*5);
                            }
                        }         
                    }
                }
                //Affichages des cases accessibles
                Image ac = new Image(getClass().getResourceAsStream("pions/accessible.png"));
                for(int x = 0; x < m_game.colonne(); x++){
                    for(int y = 0; y < m_game.ligne(); y++){
                        if(m_game.casesAccessibles(crd).contains(new Coordonnees(y,x))){
                                //System.out.println("-----------x: "+x+"    y: "+y);
                                gc.drawImage(ac,(x+0.25)*tileSizeX,(y+0.25)*tileSizeY, tileSizeX/2, tileSizeY/2);
                        }
                    }         
                }
                
                //public ArrayList<Coordonnees> pionsCapturables(int lPion,int cPion, int lDestination, int cDestination)
                

		Image pb = new Image(getClass().getResourceAsStream("pions/pion_b.png"));
                Image pg = new Image(getClass().getResourceAsStream("pions/pion_r.png"));
                

		//Affichage des pions
		for(int x = 0; x < m_game.colonne(); x++){
			for(int y = 0; y < m_game.ligne(); y++){
				if(m_game.pionJoueur1(y,x)){
					gc.drawImage(pg,(x+0.25)*tileSizeX,(y+0.25)*tileSizeY, tileSizeX/2, tileSizeY/2);
					//gc.setFill(Color.RED);
				}
				if(m_game.pionJoueur2(y,x)){
					//gc.setFill(Color.BLUE);
					gc.drawImage(pb,(x+0.25)*tileSizeX,(y+0.25)*tileSizeY, tileSizeX/2, tileSizeY/2);
				}
                                //Renvoie la liste des cases accessibles du pion donnÃ©
    //                          public ArrayList<Coordonnees> casesAccessibles(int l, int c)
			}

		}

	}

}
/*
class _Grille extends Partie {

	public _Grille(){
	}

	@Override
	public int ligne(){
		return tab.length;
	}

	@Override
	public int colonne(){
		if(tab[0] != null){
			return tab[0].length;
		}
		else{
			return 0;
		}

	}

	public int at(int x, int y){
		return tab[x][y];
	}

}*/
