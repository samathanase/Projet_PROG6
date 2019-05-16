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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PvP_board implements Initializable {

    
        
    @FXML
    private AnchorPane pvp_scene;

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
    private ImageView toggle_help;

    @FXML
    private Text pvp_title;

    
    @FXML
    void restart_btn(ActionEvent event) throws IOException {
        Stage stage = (Stage)restart_board.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("board_PvP.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void return_to_main(ActionEvent event) throws IOException {
        Stage stage = (Stage)return_to_main.getScene().getWindow();
        
        Parent r1 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(r1);
        
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void toggle_help(MouseEvent event) throws IOException {
        Stage stage= new Stage();
        Parent r1 = FXMLLoader.load(getClass().getResource("tuto1_toggle.fxml"));
        //System.out.println("root");
        Scene scene = new Scene(r1);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
    }

    @FXML
    void toggle_opt(ActionEvent event) throws IOException {
        Stage stage= new Stage();
        Parent r = FXMLLoader.load(getClass().getResource("Options_toggle.fxml"));
        //System.out.println("root");
        Scene scene = new Scene(r);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
        //start_cnv();
    }
    
    @FXML
    private AnchorPane exit_scene;

    @FXML
    private Button ext_yes;

    @FXML
    private Button ext_no;
    
    @FXML
    void ext_no(ActionEvent event) throws IOException {
        Stage stage = (Stage)ext_no.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void ext_yes(ActionEvent event) throws IOException {
        
        Stage st = (Stage)ext_yes.getScene().getWindow();
        st.close();
        //st.setOnCloseRequest().handle(new WindowEvent(st, WindowEvent.WINDOW_CLOSE_REQUEST));

    }
    
    @FXML
    private ImageView icone_j1;

    @FXML
    private ImageView icone_j2;
    
    @FXML
    private ImageView icone_j_encours;
    
    public void set_icons(){
        //if(j1==j2){}
        Image icon1 = new Image(getClass().getResourceAsStream("icônes/profile1.jpg"));
        Image icon2 = new Image(getClass().getResourceAsStream("icônes/profile2.jpg"));
        Image icon3 = new Image(getClass().getResourceAsStream("icônes/profile1.jpg"));
        icone_j1.setImage(icon1);
        icone_j2.setImage(icon2);
        icone_j_encours.setImage(icon3);
    }
    
    @FXML
    private Pane r;
    
    @FXML
    private Canvas canv;
   

    
    public void start_cnv() throws IOException{

        //Stage stage = (Stage)options_board.getScene().getWindow();
        Partie game = new Partie();
        ResizableCanvas cnv = new ResizableCanvas();
        GridView gv = new GridView(game);
        Controller ctrl = new Controller(1,game);
	//IA_Controller ia = new IA_Controller(-1,game);
        System.out.println(game.joueur());
		
        r.getChildren().add(cnv);
        cnv.widthProperty().bind(r.widthProperty());
	cnv.heightProperty().bind(r.heightProperty());	
	cnv.widthProperty().addListener((Observable o) -> {gv.draw(cnv);});
	cnv.heightProperty().addListener((Observable o) -> {gv.draw(cnv);});  
	gv.draw(cnv);
        
        cnv.setOnMouseClicked((MouseEvent e) -> {
            ctrl.click(e,gv);
            //ia.jouer(ia.think());
            gv.draw(cnv);
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        set_icons();
        try {
            start_cnv();
        } catch (IOException ex) {
            Logger.getLogger(PvP_board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
