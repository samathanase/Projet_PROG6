package Fanorona_prog06;

import Modele.Partie;
import static Fanorona_prog06.FXMLDocumentController.game;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class IA_lvl {
    public static String lvl="easy";
    public static int ia_lvl=0;
    
    @FXML
    private AnchorPane scene_IA_lvl;

    @FXML
    private ImageView moy_btn;

    @FXML
    private ImageView ez_btn;

    @FXML
    private ImageView hard_btn;

    @FXML
    private ImageView quit_btn;

    @FXML
    private ImageView sortir_btn;
    

    @FXML
    void play_ez(MouseEvent event) throws IOException {
        game = new Partie();
        ia_lvl=1;
        lvl="easy";
        
        Stage stage= new Stage();
        Parent r1 = FXMLLoader.load(getClass().getResource("board_PvA.fxml"));
        
        Scene scene = new Scene(r1);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        /*Stage stage = (Stage)ez_btn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("board_PvA.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.setResizable(false);
        scene.getWindow().centerOnScreen();
        stage.show();*/
    }

    @FXML
    void play_hard(MouseEvent event) throws IOException {
        game = new Partie();
        ia_lvl=3;
        lvl="hard";
        Stage stage= new Stage();
        Parent r1 = FXMLLoader.load(getClass().getResource("board_PvA.fxml"));
        
        Scene scene = new Scene(r1);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        /*Stage stage = (Stage)hard_btn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("board_PvA.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.setResizable(false);
        scene.getWindow().centerOnScreen();
        stage.show();*/
    }

    @FXML
    void play_moy(MouseEvent event) throws IOException {
        game = new Partie();
        ia_lvl=2;
        lvl="meduium";
        Stage stage= new Stage();
        Parent r1 = FXMLLoader.load(getClass().getResource("board_PvA.fxml"));
        
        Scene scene = new Scene(r1);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        /*Stage stage = (Stage)moy_btn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("board_PvA.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.setResizable(false);
        scene.getWindow().centerOnScreen();
        stage.show();*/
    }

    @FXML
    void back_to_main(MouseEvent event) throws IOException {
        Stage stage = (Stage)sortir_btn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}
