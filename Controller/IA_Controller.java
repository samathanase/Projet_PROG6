package Controller;
import java.util.Random;
import java.util.*;
import Modele.*;
public class IA_Controller extends Controller {


	public IA_Controller(int player){
		super(player);
	}
	public IA_Controller(int player, Partie game){
		super(player,game);
	}


	//retourne l'ensemble des coups possibles	
	public List<Action> getAvailableActions(){
		List<Action> ret = new ArrayList<>();
		for(int i = 0; i < m_game.ligne(); i++){
			for(int j = 0; j < m_game.colonne(); j++){
				if(canPlay()){
					ret.addAll(getActionsFromPosition(i,j));
				}
			}
		}
		return ret;
	}


	private List<Action> getActionsFromPosition(int l, int c){
		List<Action> ret = new ArrayList<>();
		if(this.at(l,c)){
			List<Coordonnees> adjs = m_game.casesAdjacentes(l,c);
			for(int i = 0; i < adjs.size(); i++){
				if(m_game.libre(adjs.get(i).ligne(),adjs.get(i).colonne())){
					Coordonnees delta = new Coordonnees(adjs.get(i).ligne()-l,adjs.get(i).colonne()-c);
					if(m_game.caseExiste(l-delta.ligne(),c-delta.colonne()) && this.nat(l-delta.ligne(),c-delta.colonne())){
						ret.add(new Action(new Coordonnees(l,c),delta,-1));
					}
					if(m_game.caseExiste(adjs.get(i).ligne()+delta.ligne(),adjs.get(i).colonne()+delta.colonne()) && this.nat(adjs.get(i).ligne()+delta.ligne(),adjs.get(i).colonne()+delta.colonne())){
						ret.add(new Action(new Coordonnees(l,c),delta,1));
					}
					ret.add(new Action(new Coordonnees(l,c),delta,0));
				}
			}
		}

		return ret;
	}

	//Note une configuration	
	/*public double fitness(Configuration config){
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
		if(nbJ2 ==0){
			return -1;
		}
		return (nbJ1*1.0)/nbJ2;
	}*/

	public Coup think(){
		List<Coup> ret = m_game.listeCoupsValides() ; //La liste des coups valides
		Random gen = new Random();
		if(ret.size() > 0){
			return ret.get(gen.nextInt(ret.size()));
		}
		else{
			System.out.println("No actions");
			return null;
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

