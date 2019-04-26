import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
package View;
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
		
		double tileSizeX = (cnv.getWidth()-m_grid.sizeX())/m_grid.sizeX() ;
		double tileSizeY = (cnv.getHeight()-m_grid.sizeY())/m_grid.sizeY() ;
		gc.clearRect(0,0,cnv.getWidth(),cnv.getHeight());		
		gc.setFill(Color.RED);

		for(int i = 0; i < m_grid.sizeX(); i++){
			for(int j = 0; j < m_grid.sizeY(); j++){
				if(m_grid.at(i,j) == 1){
					gc.fillRect(i*(1+tileSizeX),j*(1+tileSizeY),tileSizeX,tileSizeY);
				}
				else if(m_grid.at(i,j) == 2){
					gc.setFill(Color.GREEN);
					gc.fillRect(i*(1+tileSizeX),j*(1+tileSizeY),tileSizeX,tileSizeY);
					gc.setFill(Color.RED);
				}
			}
		}

	}

}

class Grille{
	int[][] grid;

	public Grille(){
		int x = 5;
		int y = 7;
		grid = new int[x][y];
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				grid[i][j] = 1;
			} 
		}
		grid[1][1] = 0;
		grid[0][0] = 2;
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
