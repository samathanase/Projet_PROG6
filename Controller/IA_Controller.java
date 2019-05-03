package Controller;

public class IA_Controller extends Controller {


	public IA_Controller(){
	}


	//retourne l'ensemble des coups possibles	
	public List<Action> getAvailableActions(){
		List<Action> ret = new ArrayList<>();
		for(int i = 0; i < m_grid.sizeX(); i++){
			for(int j = 0; j < m_grid.sizeY(); j++){
				if(m_grid.at(i,j) == m_player){
					ret.addAll(getActionsFromPosition(i,j));
				}
			}
		}
		return ret;
	}


	private List<Action> getActionsFromPosition(int x, int y){
		List<Action> ret = new ArrayList<>();
		if(m_grid.at(x,y) == m_player){
			List<Coordonnees> adjs = adjs(x,y);
			for(int i = 0; i < adjs.size(); i++){
				if(m_grid.at(adjs[i].getKey(),adjs[i].getValue()) == 0){
					Coordonnees delta = new Coordonnees(adjs[i].ligne()-x,adjs[i].colonne()-y);
					if(m_grid.at(x-delta.ligne(),y-delta.colonne()) == -m_player){
						ret.add(new Action(new Coordonnees(x,y),delta,-1));
					}
					if(m_grid.at(adjs[i]+delta.getKey(),adjs[i]+delta.getValue()) == -m_player){
						ret.add(new Action(new Coordonnees(x,y),delta,1));
					}
					ret.add(new Action(new Coordonnees(x,y),delta,0));
				}
			}
		}

		return ret;
	}

	//Note une configuration	
	public double fitness(Configuration config){
		int nbJ1 = 0, nbJ2 = 0, capturableJ1 = 0; capturableJ2 = 0;
		for(int i = 0; i < config.nbL(); i++){
			for(int j =0; j < config.nbC(); j++){
				if(config.at(i,j) == 1){
					nbJ1++;	
				}
				if(config.at(i,j) == -1){
					nbJ2++;
				}
				
			}
		}
	}
}


class _Grille_{
	int[][] grid;

	public _Grille_(){
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

