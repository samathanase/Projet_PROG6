package Modele;
import java.io.Serializable;


public class Direction implements Serializable{
    private static final long serialVersionUID = -6471455606652848584L;

    EnumDirection direction;
    public Direction(EnumDirection d) {
        direction = d;
    }
	//-------------------------------
	public Direction(Direction copy){
		direction = copy.direction;
	}
	//-------------------------------

    public void changerDirection(EnumDirection dir) {
        direction = dir;
    }

    public EnumDirection nom() {
        return direction;
    }

    //Renvoie la représentation sous forme de coordonnées de la direction
    //Ex Bas:(1,0) car on descend d'une ligne
    public Coordonnees coordonnees (){
        Coordonnees C = new Coordonnees(0,0);
        switch (direction) {
            case Bas:
                C.l(1);C.c(0);
                break;
            case Haut:
                C.l(-1);C.c(0);
                break;
            case Droite:
                C.l(0);C.c(1);   
                break;
            case Gauche:
                C.l(0);C.c(-1);  
                break;
            case BasGauche:
                C.l(1);C.c(-1); 
                break;
            case BasDroite:
                C.l(1);C.c(1);
                break;
            case HautDroite:
                C.l(-1);C.c(1);
                break;
            case HautGauche:
                C.l(-1);C.c(-1);
                break;
            default:
                break;
        }
        return C;
    }

    public boolean equals(Direction D) {
        return direction == D.nom();
    }
    
}
