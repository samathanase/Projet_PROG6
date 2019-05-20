import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.beans.*;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import Modele.*;
import View.*;
import Controller.*;
public class IAStats {


	public static void main(String[] args) {
		Partie game = new Partie(5,9);
		IA_Controller tia = new IA_Controller(1,game,"h27");
		IA_Controller ia = new IA_Controller(-1,game,"h26");

		int vict = 0, total = 0;
		int maxP = 40;
		while(total != maxP){
			while(game.gagnant() == 0){
				if(game.joueur() == 1){
					tia.jouer(tia.think(1));
				}
				else{
					ia.jouer(ia.think(1));
				}
			}
			total++;
			if(game.gagnant() == 1){
				vict++;
			}
			System.out.print("\r                                                                        ");
			System.out.print("\rstats temp. :\t" + (vict*100.)/total + " %\t" + (maxP-total) + " parties restantes");
			game.recommencer();
		}


			System.out.print("\r                                                                        ");
		System.out.print("\rSTATS FIN : " + (vict*100.)/total + "%");
	}

}
