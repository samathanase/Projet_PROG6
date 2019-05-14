package fanorona_prog06;

import Controller.Controller;
import Controller.IA_Controller;
import Modele.Partie;
import View.GridView;
import View.ResizableCanvas;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PvA_board implements Initializable {

    @FXML
    private AnchorPane pva_scene;

    @FXML
    private Rectangle board_rect1;

    @FXML
    private Rectangle board_rect11;

    @FXML
    private Text player1_txt;

    @FXML
    private Text player2_txt;

    @FXML
    private Text score_txt;

    @FXML
    private Rectangle icone1;

    @FXML
    private Rectangle icone2;

    @FXML
    private Rectangle board_rect12;

    @FXML
    private Rectangle board_rect111;

    @FXML
    private Text turn_txt;

    @FXML
    private Text player_turn_txt;

    @FXML
    private Rectangle icone3;

    @FXML
    private Button restart_board;

    @FXML
    private Button save_board;

    @FXML
    private Button options_board;

    @FXML
    private Button return_to_main;

    @FXML
    private Text pvp_title;

    @FXML
    private ImageView omg_board;

    @FXML
    private ImageView help_img;
    
    @FXML
    private Pane r;
    
    @FXML
    void restart_btn(ActionEvent event) throws IOException {
        Stage stage = (Stage)restart_board.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("board_PvA.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    void return_to_main(ActionEvent event) throws IOException {
        Stage stage = (Stage)return_to_main.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
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
        stage.show();
    }

    @FXML
    void toggle_opt(ActionEvent event) throws IOException {
        Stage stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Options_toggle.fxml"));
        //System.out.println("root");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
    
    public void start_cnv() throws IOException{

        Partie game = new Partie();
	ResizableCanvas cnv = new ResizableCanvas();
	GridView gv = new GridView(game);
	Controller ctrl = new Controller(1,game);
	IA_Controller ia = new IA_Controller(-1,game);

	System.out.println(game.joueur());
	//Pane root = new Pane();
	r.getChildren().add(cnv);
	cnv.widthProperty().bind(r.widthProperty());
	cnv.heightProperty().bind(r.heightProperty());	
	cnv.widthProperty().addListener((Observable o) -> {gv.draw(cnv);});
	cnv.heightProperty().addListener((Observable o) -> {gv.draw(cnv);});
	gv.draw(cnv);
        
	cnv.setOnMouseClicked((MouseEvent e) -> {
            if(game.joueur1()){
                ctrl.click(e,gv);
                //ia.jouer(ia.think());
            }else{
                ia.jouer(ia.think());
                //ctrl.click(e,gv);
            }
            gv.draw(cnv);
        });
        
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            start_cnv();
        } catch (IOException ex) {
            Logger.getLogger(PvA_board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
