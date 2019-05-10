package Controller;
import java.util.Random;
import java.util.*;
import javafx.util.Pair;
import Modele.*;
import java.lang.Character;
public class IA_Controller extends Controller {

	double rootFitness;
	
	public IA_Controller(int player){
		super(player);
	}
	public IA_Controller(int player, Partie game){
		super(player,game);
	}
	
	@Override
	public boolean jouer(Coup a){
		if(a != null){
			return m_game.jouer(a);
		}
		else{
			return false;
		}
	}
	//Note une configuration	
	public double fitness(Partie state,int player){
		Grille config = state.grille();
		int nbPL = 0, nbADV = 0, nbCapturablePL = 0,nbCapturableADV = 0;
		for(int i = 0; i < config.ligne(); i++){
			for(int j =0; j < config.colonne(); j++){
				if(config.at(i,j) == (player==1?1:2)){
					nbPL++;	
					List<Coordonnees> adjs = state.casesAccessibles(i,j);

					for(int id = 0; id < adjs.size(); id++){
						Coordonnees dir = new Coordonnees(adjs.get(id).ligne()-i,adjs.get(id).colonne()-j);
						int is,js;
						is = i+2*dir.ligne(); 
						js = j+2*dir.colonne();
						while(config.caseExiste(is,js) && config.at(is,js) == (player==1?2:1)){
							nbCapturablePL++;
							is+= dir.ligne();
							js+= dir.colonne();
						}
						is = i - dir.ligne();
						js = j - dir.colonne();
						while(config.caseExiste(is,js) && config.at(is,js) == (player==1?2:1)){
							nbCapturablePL++;
							is -= dir.ligne();
							js -= dir.colonne();
						}
					}


						
				}
				if(config.at(i,j) == (player==1?2:1)){
					nbADV++;
					List<Coordonnees> adjs = state.casesAccessibles(i,j);

					for(int id = 0; id < adjs.size(); id++){
						Coordonnees dir = new Coordonnees(adjs.get(id).ligne()-i,adjs.get(id).colonne()-j);
						int is,js;
						is = i+2*dir.ligne(); 
						js = j+2*dir.colonne();
						while(config.caseExiste(is,js) && config.at(is,js) == (player==1?1:2)){
							nbCapturableADV++;
							is+= dir.ligne();
							js+= dir.colonne();
						}
						is = i - dir.ligne();
						js = j - dir.colonne();
						while(config.caseExiste(is,js) && config.at(is,js) == (player==1?1:2)){
							nbCapturableADV++;
							is -= dir.ligne();
							js -= dir.colonne();
						}
					}

				}
				
			}
		}
		if(nbADV ==0){
			return Double.MAX_VALUE;
		}
		double fit = (nbCapturablePL)*0.5 + 1*((nbPL*1.0)-(nbADV*1.0)) + 2*((1/Math.exp(nbADV)));

		//return fit + ((player==m_player&&rootFitness!=0)?(5*(fit/rootFitness)):1);
		return fit;
	}

	public Pair<Double,Integer> minimax(Partie state,int horizon,int player,String tree){
		if(state.gagnant() != 0 || horizon == 0){
			return new Pair<Double,Integer>(fitness(state,player),-2);
		}
		List<Coup> actions = state.listeCoupsValides() ;
		double ret;
		if(player == m_player){
			ret = Double.MIN_VALUE;
		}else{
			ret = Double.MAX_VALUE; 
		}
		int id = 0;
		
		for(int i = 0; i < actions.size(); i++){
			double oldret = ret;

			String strtree = tree;
			if(Character.isLetter(tree.charAt(tree.length()-1))){
				strtree += String.valueOf(i); 
			}
			else{
				strtree += (char)('A'+i);
			}
			Partie next = new Partie(state);
			next.jouer(actions.get(i)); 
			//next.afficher();
			if(player == m_player){
				ret = Math.max(ret,minimax(next,horizon-1,next.joueur()==1?1:-1,strtree).getKey());
			}else{
				ret = Math.min(ret,minimax(next,horizon-1,next.joueur()==1?1:-1,strtree).getKey());
			}
			if(ret != oldret){
				System.out.println(strtree);
				System.out.println("FIT: " + ret);
				id = i;
			}
		}
		return new Pair<Double,Integer>(ret,id);
	}

	public Coup think(int level){
		if(level == 0){
			List<Coup> ret = m_game.listeCoupsValides() ;
			Random gen = new Random();
			if(ret.size() > 0){
				return ret.get(gen.nextInt(ret.size()));
			}
			else{
				System.out.println("No actions");
				return null;
			}
		}
		if(level == 1){
			rootFitness = fitness(m_game,m_player);
			List<Coup> ret = m_game.listeCoupsValides() ;
			System.out.println("BMM");
			Pair<Double,Integer> id = minimax(new Partie(m_game),3,m_player,"R");
			System.out.println("EMM " +id.getValue() +  " " + ret.size() );
			return ret.get(id.getValue());
		}
		return null;
	}


}




