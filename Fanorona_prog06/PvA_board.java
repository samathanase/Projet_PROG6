package Fanorona_prog06;

import Controller.Controller;
import Controller.IA_Controller;
import Modele.ChargerPartie;
import Modele.Grille;
import Modele.Partie;
import Modele.SauvegarderPartie;
import View.GridView;
import View.ResizableCanvas;
import static Fanorona_prog06.PvP_board.annuler;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PvA_board implements Initializable {
    Partie game = new Partie(5,9);
    
    public static String p1="joueur 1",p2="joueur 2";

    @FXML
    private AnchorPane pva_scene;

    @FXML
    private Text player1_txt;

    @FXML
    private Text player2_txt;

    @FXML
    private Text player_turn_txt;

    @FXML
    private Rectangle icone3;

    @FXML
    private ImageView restart_board;

    @FXML
    private ImageView save_board;
    
    @FXML
    void save_game(MouseEvent event) throws IOException, InterruptedException {
        SauvegarderPartie s = new SauvegarderPartie(FXMLDocumentController.game,"testSave.txt");
        s.sauvegarder();
        Stage stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("save.fxml"));
        //System.out.println("root");
        //stage.initStyle(StageStyle.UNDECORATED);
        //stage.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
        sleep(2400);
        stage.hide();
    }

    @FXML
    private ImageView return_to_main;

    @FXML
    private Text pvp_title;

    @FXML
    private ImageView omg_board;

    @FXML
    private ImageView help_img;
    
    @FXML
    private Pane r;
    
    @FXML
    private ImageView annuler_btn;

    @FXML
    void annuler_coup(MouseEvent event) throws IOException {
        //game.annuler();
        annuler=1;
        game.annuler();

    }
    
    @FXML
    void restart_btn(MouseEvent event) throws IOException {
        
        FXMLDocumentController.game.recommencer();
        Stage stage = (Stage)restart_board.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("board_PvA.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    void return_to_main(MouseEvent event) throws IOException {
        Stage stage = (Stage)return_to_main.getScene().getWindow();
        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        veil.setVisible(false);
        ButtonType Oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType Non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.WARNING,
        "etes vous sur de vouloir quitter la partie ?",
        Oui,
        Non);
        veil.visibleProperty().bind(a.showingProperty());
        a.setTitle("attention");
        a.setX(stage.getWidth()/1.5);
        a.setY(stage.getHeight()/2);
        Optional<ButtonType> result = a.showAndWait();
        if(!result.isPresent()){
        
        }else if(result.get() == Oui){
            stage= new Stage();
            Parent r1 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            
            Scene scene = new Scene(r1);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
        }
    }

    @FXML
    void toggle_help(MouseEvent event) throws IOException {
        Stage stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("tuto1_toggle.fxml"));
        //System.out.println("root");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

  
    
    public void start_cnv() throws IOException{
        
        String lvl="easy";
        if(IA_lvl.ia_lvl==1){
            lvl="easy";
        }else if(IA_lvl.ia_lvl==2){
            lvl="easy";
        }else if(IA_lvl.ia_lvl==3){
            lvl="hard";
        }
        
        
		Grille grid = new Grille(game.grille());
		for(int i =1; i < grid.ligne()-1; i++){
			for(int j = 1; j < grid.colonne()-1; j++){
			//	grid._set(0,i,j);
			}
		}
        game.setGrille(grid);
	ResizableCanvas cnv = new ResizableCanvas();
		GridView gv = new GridView(game);
		Controller ctrl = new Controller(1,game);
		IA_Controller tia = new IA_Controller(1,game,"h27");
		IA_Controller ia = new IA_Controller(-1,game,lvl);

	System.out.println(FXMLDocumentController.game.joueur());
	//Pane root = new Pane();
	r.getChildren().add(cnv);
	cnv.widthProperty().bind(r.widthProperty());
	cnv.heightProperty().bind(r.heightProperty());	
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
        
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        player1_txt.setText(p1);
        player2_txt.setText(p2);
        try {
            start_cnv();
        } catch (IOException ex) {
            Logger.getLogger(PvA_board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private ImageView return_IA_choice;
    
    @FXML
    void return_IA(MouseEvent event) throws IOException {
        Stage stage = (Stage)return_IA_choice.getScene().getWindow();
        
        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        veil.setVisible(false);
        ButtonType Oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType Non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.WARNING,
        "etes vous sur de vouloir quitter la partie ?",
        Oui,
        Non);
        veil.visibleProperty().bind(a.showingProperty());
        a.setTitle("attention");
        a.setX(stage.getWidth()/1.5); 
        a.setY(stage.getHeight()/2);
        Optional<ButtonType> result = a.showAndWait();
        if(!result.isPresent()){
        
        }else if(result.get() == Oui){
            stage= new Stage();
            Parent r1 = FXMLLoader.load(getClass().getResource("IA_lvl.fxml"));
            
            Scene scene = new Scene(r1);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
        }  
        
    }
    
}
