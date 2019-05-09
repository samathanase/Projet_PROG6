package View;

import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import Modele.*;
public class GridView{

	Partie m_game;
	double tileSizeX,tileSizeY;

	public GridView(){
		m_game = new _Grille/*Partie*/();
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
		GraphicsContext gc = cnv.getGraphicsContext2D();


		tileSizeX = (cnv.getWidth())/m_game.colonne() ;
		tileSizeY = (cnv.getHeight())/m_game.ligne() ;

		gc.clearRect(0,0,cnv.getWidth(),cnv.getHeight());		
		//gc.setFill(Color.RED);


		//Affichage des lignes
		for(int i = 0; i < m_game.colonne(); i++){
			gc.strokeLine((i+0.5)*tileSizeX,0.5*tileSizeY,(i+0.5)*tileSizeX,(m_game.ligne()-0.5)*tileSizeY);
			
		}

		for(int j = 0; j < m_game.ligne(); j++){
			gc.strokeLine(0.5*tileSizeX,(j+0.5)*tileSizeY,(m_game.colonne()-0.5)*tileSizeX,(j+0.5)*tileSizeY);
		}
		for(int k = 0; k <= m_game.colonne()/2; k++){
			for(int m = 0; m < m_game.ligne()/2; m++){
				if(k!=0){
					gc.strokeLine((k*2+0.5)*tileSizeX,(m*2+0.5)*tileSizeY,((k-1)*2+0.5)*tileSizeX,((m+1)*2+0.5)*tileSizeY);
				}
			
				if(k!= m_game.colonne()/2){
					gc.strokeLine((k*2+0.5)*tileSizeX,(m*2+0.5)*tileSizeY,((k+1)*2+0.5)*tileSizeX,((m+1)*2+0.5)*tileSizeY);
				}
			}
			
		}
	
		//Affichage des pions
		for(int x = 0; x < m_game.colonne(); x++){
			for(int y = 0; y < m_game.ligne(); y++){
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

}
