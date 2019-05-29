package Fanorona_prog06;

import Controller.Controller;
import Controller.IA_Controller;
import Modele.ChargerPartie;
import Modele.Coup;
import Modele.Partie;
import Modele.SauvegarderPartie;
import View.GridView;
import View.ResizableCanvas;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class PvP_board implements Initializable {
    Partie game;
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
    void sauvegarder_pvp(MouseEvent event) throws IOException, InterruptedException {
        FXMLDocumentController.charge_game=1;
        SauvegarderPartie s = new SauvegarderPartie(game,"testSave.txt");
        s.sauvegarder();
        Stage stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("save.fxml"));
        //FXMLDocumentController.charge_bool=1;
        //System.out.println("root");
        //stage.initStyle(StageStyle.UNDECORATED);
        //stage.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
        sleep(2000);
        stage.hide();
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
    private ImageView img_scene;
    
    
    @FXML
    private Button ext_yes;

    @FXML
    private Button ext_no;
    
    @FXML
    private VBox v_box1;
    
    @FXML
    private VBox v_box2;
    
    @FXML
    void return_to_main(MouseEvent event) throws IOException {

        Stage stage = (Stage)return_to_main.getScene().getWindow();
        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        veil.setVisible(false);
        ButtonType Oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType Non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert al = new Alert(AlertType.NONE,
        "etes vous sur de vouloir quitter la partie ?",
        Non,
        Oui);
        veil.visibleProperty().bind(al.showingProperty());
        al.setTitle("attention");
        al.setX(stage.getWidth()/1.5); 
        al.setY(stage.getHeight()/1.5);
        Optional<ButtonType> res = al.showAndWait();
        if(!res.isPresent()){
        
        }else if(res.get() == Oui){
            stage= new Stage();
            Parent r1 = FXMLLoader.load(getClass().getResource("FXMLDocument_resisable.fxml"));
            
            Scene scene = new Scene(r1);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            String image = Fanorona_prog06.class.getResource("buttons/fanorona_bg.png").toExternalForm();
            r1.setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-size: 950 788 ; "
                + "-fx-background-position: center center; "
                + "-fx-background-size: cover, auto;"
                + "-fx-background-repeat: stretch;");
            
        }  
    }
    
    @FXML
    private ImageView music_btn;
    
    @FXML
    void music_on_off(MouseEvent event){
        
        Image btn_off = new Image(getClass().getResourceAsStream("buttons/music_off.png"));
        Image btn_on = new Image(getClass().getResourceAsStream("buttons/music_on.png"));
        //music_mode = 1 -> on ... music_mode = 2 -> off
        switch(Fanorona_prog06.music_mode){
            case 1:
                music_btn.setImage(btn_off);
                Fanorona_prog06.st.stop();
                Fanorona_prog06.music_mode=2;
                break;
            case 2:
                music_btn.setImage(btn_on);
                Fanorona_prog06.st.play();
                Fanorona_prog06.music_mode=1;
                break;
            
        }
        
        
    }
   
    @FXML
    void toggle_help(MouseEvent event) throws IOException {
        Stage stage= new Stage();
        Parent r1 = FXMLLoader.load(getClass().getResource("tuto1_toggle.fxml"));
        //System.out.println("root");
        Scene scene = new Scene(r1);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        //stage.show();
    }
    

    @FXML
    private AnchorPane exit_scene;

    
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
        //Image icon1 = new Image(getClass().getResourceAsStream("icnes/profile1.jpg"));
        //Image icon2 = new Image(getClass().getResourceAsStream("icnes/profile2.jpg"));
        //icone_j1.setImage(icon1);
        //icone_j2.setImage(icon2);
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
    
    @FXML
    public static Text en_cours = new Text();
    
    @FXML
    private ImageView pion_encours;
    
    
    public void set_turn (){
        //Image selected = new Image(getClass().getResourceAsStream("board_pics/turn.png"));
        //Image pion1 = new Image(getClass().getResourceAsStream("pions/pion_r.png"));
        
        if (game.joueur==1){
            en_cours.setText(p1);
            pion_encours1=null;//.setImage(selected);
            pion_encours2=null;
            //pion_encours.setImage(pion1);
        }else{
            en_cours.setText(p2);
            pion_encours1=null;
            pion_encours2=null;//.setImage(selected);
            //pion_encours.setImage(pion2);
        }
    }
    
    
    
    public static int gagn=0;
    public void start_cnv() throws IOException{
        /*ImageView im1 = null;
        im1.setImage(GridView.selected1);
        ImageView im2 = null;
        im2.setImage(GridView.selected2);
        v_box1.getChildren().add(im1);
        v_box2.getChildren().add(im2);*/
        en_cours.setStyle("-fx-font-size: 22");
        en_cours.setLayoutX(600);
        en_cours.setLayoutY(0);
        //Stage stage = (Stage)options_board.getScene().getWindow();
        //game = new Partie();
        ResizableCanvas cnv = new ResizableCanvas();
        GridView gv = new GridView(game);
        set_turn();
       
        Controller ctrl = new Controller(game.joueur,game);
	//IA_Controller ia = new IA_Controller(-1,game);
        System.out.println(game.joueur());
	
        r.getChildren().addAll(en_cours,cnv); 
        cnv.widthProperty().bind(r.widthProperty());
	cnv.heightProperty().bind(r.heightProperty());	
	cnv.widthProperty().addListener((Observable o) -> {gv.draw(cnv);});
	cnv.heightProperty().addListener((Observable o) -> {gv.draw(cnv);});  
	gv.draw(cnv);
        
        cnv.setOnMouseClicked((MouseEvent e) -> {
            ctrl.click(e,gv);
            gv.draw(cnv);
            if(game.estFinie()){
                gagn=game.gagnant();
                Stage s= new Stage();
                Parent r1 = null;
                try {
                    r1 = FXMLLoader.load(getClass().getResource("victoire.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(PvP_board.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                Scene scene = new Scene(r1);
                s.setScene(scene);
                s.setResizable(false);
                s.show();
                ((Node)(e.getSource())).getScene().getWindow().hide();
            }
        });
        

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(FXMLDocumentController.charge_game!=1){
            game=new Partie();
        }else{
            //Partie partieCharge = null;
            ChargerPartie load = new ChargerPartie("testSave.txt");
            game = load.charger();
            game.afficher();
            
        }
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
    
    @FXML
    private ImageView pass_turn_btn;
    
    @FXML
    void pass_turn(MouseEvent event){
        game.changerJoueur();
    }
    
    

}
