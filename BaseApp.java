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
public class BaseApp extends Application {


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hello World!");


		Partie game = new Partie(5,9);
		Grille grid = new Grille(game.grille());
		for(int i =1; i < grid.ligne()-1; i++){
			for(int j = 1; j < grid.colonne()-1; j++){
			//	grid._set(0,i,j);
			}
		}
	/*	
		grid.set(0,0,0);
		grid.set(0,1,0);
		grid.set(0,2,0);
		grid.set(0,2,2);
		grid.set(0,1,5);
		grid.set(2,0,4);
		grid.set(1,1,1);
		grid.set(1,1,2);
		grid.set(1,1,3);
	*/		
		game.setGrille(grid);
		ResizableCanvas cnv = new ResizableCanvas();
		GridView gv = new GridView(game);
		Controller ctrl = new Controller(1,game);
		IA_Controller tia = new IA_Controller(1,game,"h27");
		IA_Controller ia = new IA_Controller(-1,game,"h26");

		System.out.println(game.joueur());
		Pane root = new Pane();
		root.getChildren().add(cnv);


		cnv.widthProperty().bind(root.widthProperty());
		cnv.heightProperty().bind(root.heightProperty());	
		cnv.widthProperty().addListener((Observable o) -> {gv.draw(cnv);});
		cnv.heightProperty().addListener((Observable o) -> {gv.draw(cnv);});
		gv.draw(cnv);

		cnv.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				if(game.gagnant() == 0){
					if(game.joueur1()){
						ctrl.click(e,gv);
						//tia.jouer(tia.think(1));
					}
					else{
						ia.jouer(ia.think(1));
					}		
					gv.draw(cnv);
				}
			}
		});

		//GraphicsContext gc = cnv.getGraphicsContext2D();
		//gc.setFill(Color.RED);
		//gc.fillRect(0,0,10,10);


		primaryStage.setScene(new Scene(root, 800, 600));


		primaryStage.show();
	}
}
