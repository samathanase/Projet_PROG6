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
    
    Partie game=new Partie();
    public static int annuler=0;
    public static String p1="joueur 1",p2="joueur 2";
        
    @FXML
    private AnchorPane pvp_scene;

    @FXML
    private Text player1_txt;

    @FXML
    private Text player2_txt;

    @FXML
    private ImageView restart_board;

    @FXML
    private ImageView save_board;
    
    @FXML
    void sauvegarder_pvp(MouseEvent event) {

    }

    @FXML
    private ImageView return_to_main;

    @FXML
    private ImageView toggle_help;

    @FXML
    private Text pvp_title;
    
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
        game.recommencer();
        Stage stage = (Stage)restart_board.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("board_PvP.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void return_to_main(MouseEvent event) throws IOException {
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
   
    
    public void set_icons(){
        //if(j1==j2){}
        Image icon1 = new Image(getClass().getResourceAsStream("icônes/profile1.jpg"));
        Image icon2 = new Image(getClass().getResourceAsStream("icônes/profile2.jpg"));
        icone_j1.setImage(icon1);
        icone_j2.setImage(icon2);
    }
    
    @FXML
    public ImageView pion_encours1;

    @FXML
    public ImageView pion_encours2;
    
    @FXML
    private Pane r;
    
    @FXML
    private Canvas canv;
    
    @FXML
    private Pane encours_pane;
   
    public static Text en_cours = new Text();
    
    @FXML
    private ImageView pion_encours;
    
    public void start_cnv() throws IOException{
        en_cours.setStyle("-fx-font-size: 30");
        //Stage stage = (Stage)options_board.getScene().getWindow();
        //game = new Partie();
        ResizableCanvas cnv = new ResizableCanvas();
        GridView gv = new GridView(game);
        
        Image selected = new Image(getClass().getResourceAsStream("board_pics/turn.png"));
        //Image pion1 = new Image(getClass().getResourceAsStream("pions/pion_r.png"));
        
        if (game.joueur==1){
            en_cours.setText(p1);
            pion_encours1.setImage(selected);
            pion_encours2=null;
            //pion_encours.setImage(pion1);
        }else{
            en_cours.setText(p2);
            pion_encours1=null;
            pion_encours2.setImage(selected);
            
            //pion_encours.setImage(pion2);
        }
        Controller ctrl = new Controller(game.joueur,game);
	//IA_Controller ia = new IA_Controller(-1,game);
        System.out.println(game.joueur());
	
        r.getChildren().addAll(cnv);
        cnv.widthProperty().bind(r.widthProperty());
	cnv.heightProperty().bind(r.heightProperty());	
	cnv.widthProperty().addListener((Observable o) -> {gv.draw(cnv);});
	cnv.heightProperty().addListener((Observable o) -> {gv.draw(cnv);});  
	gv.draw(cnv);
        
        cnv.setOnMouseClicked((MouseEvent e) -> {
            ctrl.click(e,gv);
            gv.draw(cnv);
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        player1_txt.setText(p1);
        player2_txt.setText(p2);
                

        //set_pion();
        set_icons();
        try {
            start_cnv();
        } catch (IOException ex) {
            Logger.getLogger(PvP_board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
