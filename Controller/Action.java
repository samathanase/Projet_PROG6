package Controller;

import Modele.*;
public class Action{
	Coordonnees m_pos;
	Coordonnees m_dir;
	int m_action; //1=perc, -1=asp., 0=null

	public Action(Coordonnees pos, Coordonnees dir, int action){
		m_pos = pos;
		m_dir = dir;
		m_action = action;	
	}

	public int getAction(){
		return m_action;
	}

	public Coordonnees getPos(){
		return m_pos;
	}

	public Coordonnees getDir(){
		return m_dir;
	}


}
