import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
public class GridView{

	Grille m_grid;

	public GridView(){
		m_grid = new Grille();
	}

	public GridView(Grille grid){

		m_grid = grid;
	}

	public void draw(Canvas cnv){
		GraphicsContext gc = cnv.getGraphicsContext2D();


		double tileSizeX = (cnv.getWidth())/m_grid.sizeX() ;
		double tileSizeY = (cnv.getHeight())/m_grid.sizeY() ;

		gc.clearRect(0,0,cnv.getWidth(),cnv.getHeight());		
		//gc.setFill(Color.RED);

		for(int i = 0; i < m_grid.sizeX(); i++){
			gc.strokeLine((i+0.5)*tileSizeX,0.5*tileSizeY,(i+0.5)*tileSizeX,(m_grid.sizeY()-0.5)*tileSizeY);
			
		}

		for(int j = 0; j < m_grid.sizeY(); j++){
			gc.strokeLine(0.5*tileSizeX,(j+0.5)*tileSizeY,(m_grid.sizeX()-0.5)*tileSizeX,(j+0.5)*tileSizeY);
		}
		for(int k = 0; k <= m_grid.sizeX()/2; k++){
			for(int m = 0; m < m_grid.sizeY()/2; m++){
				if(k!=0){
					gc.strokeLine((k*2+0.5)*tileSizeX,(m*2+0.5)*tileSizeY,((k-1)*2+0.5)*tileSizeX,((m+1)*2+0.5)*tileSizeY);
				}
			
				if(k!= m_grid.sizeX()/2){
					gc.strokeLine((k*2+0.5)*tileSizeX,(m*2+0.5)*tileSizeY,((k+1)*2+0.5)*tileSizeX,((m+1)*2+0.5)*tileSizeY);
				}
			}
			
		}
	
		for(int x = 0; x < m_grid.sizeX(); x++){
			for(int y = 0; y < m_grid.sizeY(); y++){
				if(m_grid.at(x,y) == 1){
					gc.setFill(Color.RED);
				}
				if(m_grid.at(x,y) == -1){
					gc.setFill(Color.BLUE);
				}
				if(m_grid.at(x,y) != 0){
					gc.fillOval((x+0.25)*tileSizeX,(y+0.25)*tileSizeY,tileSizeX/2,tileSizeY/2);
				}
			}
		}

	}

}

class Grille{
	int[][] grid;

	public Grille(){
		int x = 9;
		int y = 5;
		grid = new int[x][y];
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				grid[i][j] = 0;
			} 
		}
		grid[1][1] = -1;
		grid[0][0] = 1;
	}

	int sizeX(){
		return grid.length;
	}

	int sizeY(){
		if(grid[0] != null){
			return grid[0].length;
		}
		else{
			return 0;
		}

	}

	int at(int x, int y){
		return grid[x][y];
	}

}
