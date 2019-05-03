package View;

import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import Modele.*;
public class GridView{

	_Grille m_game;
	double tileSizeX,tileSizeY;

	public GridView(){
		m_game = new _Grille/*Partie*/();
	}

	public GridView(_Grille/*Partie*/ game){

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
		GraphicsContext gc = cnv.getGraphicsContext2D();


		tileSizeX = (cnv.getWidth())/m_game.nbC() ;
		tileSizeY = (cnv.getHeight())/m_game.nbL() ;

		gc.clearRect(0,0,cnv.getWidth(),cnv.getHeight());		
		//gc.setFill(Color.RED);


		//Affichage des lignes
		for(int i = 0; i < m_game.nbC(); i++){
			gc.strokeLine((i+0.5)*tileSizeX,0.5*tileSizeY,(i+0.5)*tileSizeX,(m_game.nbL()-0.5)*tileSizeY);
			
		}

		for(int j = 0; j < m_game.nbL(); j++){
			gc.strokeLine(0.5*tileSizeX,(j+0.5)*tileSizeY,(m_game.nbC()-0.5)*tileSizeX,(j+0.5)*tileSizeY);
		}
		for(int k = 0; k <= m_game.nbC()/2; k++){
			for(int m = 0; m < m_game.nbL()/2; m++){
				if(k!=0){
					gc.strokeLine((k*2+0.5)*tileSizeX,(m*2+0.5)*tileSizeY,((k-1)*2+0.5)*tileSizeX,((m+1)*2+0.5)*tileSizeY);
				}
			
				if(k!= m_game.nbC()/2){
					gc.strokeLine((k*2+0.5)*tileSizeX,(m*2+0.5)*tileSizeY,((k+1)*2+0.5)*tileSizeX,((m+1)*2+0.5)*tileSizeY);
				}
			}
			
		}
	
		//Affichage des pions
		for(int x = 0; x < m_game.nbC(); x++){
			for(int y = 0; y < m_game.nbL(); y++){
				if(m_game.pionJoueur1(y,x)){
					gc.setFill(Color.RED);
				}
				if(m_game.pionJoueur2(y,x)){
					gc.setFill(Color.BLUE);
				}
				if(!m_game.libre(y,x)){
					gc.fillOval((x+0.25)*tileSizeX,(y+0.25)*tileSizeY,tileSizeX/2,tileSizeY/2);
				}
			}
		}

	}

}

class _Grille extends Partie {

	public _Grille(){
	}

	int nbL(){
		return tab.length;
	}

	int nbC(){
		if(tab[0] != null){
			return tab[0].length;
		}
		else{
			return 0;
		}

	}

	int at(int x, int y){
		return tab[x][y];
	}

}
