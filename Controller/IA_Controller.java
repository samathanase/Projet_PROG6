package Controller;
import java.util.Random;
import java.util.*;

import java.lang.reflect.*;
import javafx.util.Pair;
import Modele.*;
import java.lang.Character;
public class IA_Controller extends Controller {

	String m_hname;
	private final int m_depth = 2;
	Random r;

	public IA_Controller(int player,String hname){
		super(player);
		m_hname = hname;
		r = new Random();
	}
	public IA_Controller(int player, Partie game,String hname){
		super(player,game);
		m_hname = hname;
		r = new Random();
	}

	static double h0(int nbPL,int nbADV){
		return 1/Math.exp(nbADV);
	}
	static double h4(int nbPL,int nbADV){
		return 2*h0(nbPL,nbADV);
	}
	static double h10(int nbPL,int nbADV){
		return 4*h0(nbPL,nbADV);
	}
	static double h17(int nbPL,int nbADV){
		return h0(nbPL,nbADV) - 1/Math.exp(nbPL);
	}
	static double h18(int nbPL,int nbADV){
		return h0(nbPL,nbADV) - 1/Math.exp(nbPL-2);
	}
	static double h19(int nbPL,int nbADV){
		return h0(nbPL,nbADV) - 1/Math.exp(nbPL-1);
	}
	static double h20(int nbPL,int nbADV){
		return 5/Math.exp(nbADV)-2/Math.exp(nbPL-1);
	}
	static double h21(int nbPL,int nbADV){
		return 5/Math.exp(nbADV)-3/Math.exp(nbPL-2);
	}
	static double h22(int nbPL,int nbADV){
		return 5*(1/Math.exp(nbADV)) + nbPL; 
	}
	static double h23(int nbPL,int nbADV){
		return 2*h0(nbPL,nbADV) + 2*(nbPL/nbADV);
	}
	static double h24(int nbPL,int nbADV){
		return 20*h0(nbPL,nbADV) + 2*(nbPL/nbADV);
	}
	static double h25(int nbPL,int nbADV){
		return 100*h0(nbPL,nbADV) + 10*(nbPL/nbADV);
	}
	static double h26(int nbPL,int nbADV){
		return 100*h0(nbPL,nbADV) + 10*((nbPL-1.3)/(nbADV+.4));
	}

	static double h27(int nbPL,int nbADV){
		return 100*h0(nbPL,nbADV) + 10*((nbPL-1.3)/(nbADV+0.4)) + 30*(1./(nbPL+nbADV));
	}

	static double h28(int nbPL,int nbADV){
		return 100*(nbPL/nbADV);
	}

	static double h29(int nbPL, int nbADV){
		return 100*h0(nbPL,nbADV) + 10*((nbPL-3.3)/(nbADV+0.4));
	}

	static double easy(int nbPL,int nbADV){
		return h28(nbPL,nbADV); 
	}

	static double medium(int nbPL,int nbADV){
		return h0(nbPL,nbADV); 
	}

	static double hard(int nbPL,int nbADV){
		return h26(nbPL,nbADV);
	}

	public double heuristique(String hname,int nbPL, int nbADV){
		Method m;
		double ret = -1;
		try{
			m = IA_Controller.class.getDeclaredMethod(hname,int.class,int.class);
			ret= (double)m.invoke(this,nbPL,nbADV);
		}
		catch(NoSuchMethodException e){
		}
		catch(IllegalAccessException e){
		}
		catch(InvocationTargetException e){
		}
		return ret;
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
	public double fitness(Partie state,int player,int nbCoups){
		Grille config = state.grille();
		int nbPL = 0, nbADV = 0;

		for(int i = 0; i < config.ligne(); i++){
			for(int j =0; j < config.colonne(); j++){
				if(config.at(i,j) == (player==1?1:2)){
					nbPL++;	
				}
				if(config.at(i,j) == (player==1?2:1)){
					nbADV++;
				}				
			}
		}
		if(nbADV ==0 && nbCoups < 2 && player == m_player){
			return Double.MAX_VALUE-1;
		}
		if(nbPL == 0){
			return -Double.MAX_VALUE+1;
		}

		double fit = heuristique(m_hname,nbPL,nbADV);
		return fit;
	}

	public Pair<Double,Integer> minimax(Partie state,int horizon,int player){
		return minimax(state,horizon,player,"R",-Double.MAX_VALUE,Double.MAX_VALUE,0);
	}

	private Pair<Double,Integer> minimax(Partie state,int horizon,int player,String tree,double f_alpha, double f_beta,int nbCoups){

		double alpha = f_alpha;
		double beta = f_beta;
		boolean printTree =false;
		if(state.gagnant() != 0 || horizon == 0){
			double ret = fitness(state,player,nbCoups);
			if(printTree){
				System.out.println(tree +"("+(player==m_player?"MAX":"MIN")+" - LEAF) = " + ret  + "\tA : " + alpha + "\tB : " + beta);
				//state.afficher();
			}
			return new Pair<Double,Integer>(ret,-2);
		}
		List<Coup> actions = state.listeCoupsValides() ;
		if(actions.size() == 0){
			return new Pair<Double,Integer>(-Double.MAX_VALUE+1,-3);
		}
		
		double ret;
		if(player == m_player){
			ret = -Double.MAX_VALUE;
		}else{
			ret = Double.MAX_VALUE; 
		}
		int id = 0;
		List<Integer> possibilities = new ArrayList<Integer>();		
		String strtree ="";

		for(int i = 0; i < actions.size(); i++){
			
			double oldret = ret;

			state.jouer(actions.get(i));
			int nextPlayer = (state.joueur()==1?1:-1);

			int nbc = nbCoups + ((nextPlayer != player && player == m_player)?1:0);
			strtree = tree;
			if(Character.isLetter(tree.charAt(tree.length()-1))){
				if(nextPlayer != player){
					strtree += "/";
					strtree += String.valueOf(i);
				}else{
					strtree += (char)('A'+i);
				} 
			}
			else{
				if(nextPlayer != player){
					strtree += "/";
					strtree += (char)('A'+i);
				}else{
					strtree += String.valueOf(i);
				}
			}

			double value = minimax(state,(nextPlayer==player?horizon:horizon-1),nextPlayer,strtree,alpha,beta,nbc).getKey();
			state.annuler();
			if(player == m_player){
				ret = Math.max(ret,value);
				if(nextPlayer != player){
					alpha = Math.max(alpha,value);
					if(alpha > beta){
						break;
					}
					if(value > beta){
						id = i;
						possibilities.clear();
						break;
					}
				}
			}else{
				ret = Math.min(ret,value);
				if(nextPlayer != player){
					beta = Math.min(beta,value);
					if(alpha > beta){
						break;
					}
					if(value < alpha){
						id = i;
						possibilities.clear();
						break;
					}
				}
			}
			if(ret != oldret){
				id = i;
				possibilities.clear();
				possibilities.add(id);
			}
			else if(ret == value){
				possibilities.add(i);
			}
		}
		if(possibilities.size() > 1){
			id = possibilities.get(r.nextInt(possibilities.size()));
		}
		if(printTree){
			System.out.println(tree +"("+(player==m_player?"MAX":"MIN")+") = " + ret  + "\tA : " + alpha + "\tB : " + beta);
			//state.afficher();
		}
		return new Pair<Double,Integer>(ret,id);
	}

	public Coup think(){
		return think(1);
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
				m_game.finTour();
				return null;
			}
		}
		if(level == 1){
			List<Coup> ret = m_game.listeCoupsValides() ;
			Pair<Double,Integer> id = minimax(new Partie(m_game),m_depth,m_player);
			if(id.getValue() <0){
				System.out.println("IA : No actions");
			}
				
			if(m_hname.charAt(0) != 'h' && m_game.historique().taille() > 0  && m_game.historique().accederCoup(m_game.historique().taille()-1).joueur() == (m_player==1?1:2) && r.nextInt(100) < (m_hname=="easy"?60:20)){
				Coordonnees co = new Coordonnees(0,0);
				return new Coup(co,co,0);
			}
			if(m_hname.charAt(0) != 'h' && r.nextInt(100) < (m_hname=="easy"?20:10)){//RANDOM : easy, 35% ; med., 15%
				return ret.get(r.nextInt(ret.size()));
			}
			return ret.get(id.getValue());
		}
		return null;
	}


}
