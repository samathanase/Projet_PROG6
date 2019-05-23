package fanorona_prog06;

import Controller.Controller;
import Controller.IA_Controller;
import Modele.ChargerPartie;
import Modele.Partie;
import Modele.SauvegarderPartie;
import View.GridView;
import View.ResizableCanvas;
import java.io.IOException;
import static java.lang.Thread.sleep;
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
import javafx.stage.StageStyle;

public class PvA_board implements Initializable {
    
    
    public static String p1="joueur 1",p2="joueur 2";

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
    void save_game(ActionEvent event) throws IOException, InterruptedException {
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
        Partie partieCharge = null;
        ChargerPartie load = new ChargerPartie("testSave.txt");
        partieCharge = load.charger();
        FXMLDocumentController.game = partieCharge;
        
        Stage stage = (Stage)options_board.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("PvA_board.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
        
       
        /*Stage stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Options_toggle.fxml"));
        //System.out.println("root");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();*/
    }
    
    public void start_cnv() throws IOException{
        
        
	ResizableCanvas cnv = new ResizableCanvas();
	GridView gv = new GridView(FXMLDocumentController.game);
	Controller ctrl = new Controller(1,FXMLDocumentController.game);
	//IA_Controller ia = new IA_Controller(-1,game);
        IA_Controller tia = new IA_Controller(1,FXMLDocumentController.game,"h22");

	System.out.println(FXMLDocumentController.game.joueur());
	//Pane root = new Pane();
	r.getChildren().add(cnv);
	cnv.widthProperty().bind(r.widthProperty());
	cnv.heightProperty().bind(r.heightProperty());	
	cnv.widthProperty().addListener((Observable o) -> {gv.draw(cnv);});
	cnv.heightProperty().addListener((Observable o) -> {gv.draw(cnv);});
	gv.draw(cnv);
        
	cnv.setOnMouseClicked((MouseEvent e) -> {
            if(FXMLDocumentController.game.joueur1()){
                ctrl.click(e,gv);
                //tia.jouer(tia.think(1));
            }else{
                tia.jouer(tia.think(1));
                //ctrl.click(e,gv);
            }
            gv.draw(cnv);
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
    private Button return_IA_choice;
    
    @FXML
    void return_IA(ActionEvent event) throws IOException {
        Stage stage = (Stage)return_IA_choice.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("IA_lvl.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
    
}
