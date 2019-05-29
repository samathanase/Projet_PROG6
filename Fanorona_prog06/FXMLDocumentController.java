/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */





package Fanorona_prog06;

import Controller.Controller;
import Controller.IA_Controller;
import Modele.ChargerPartie;
import Modele.Coup;
import Modele.Partie;
import View.GridView;
import View.ResizableCanvas;
//import static Fanorona_prog06.PvP_board.canv;
//import static Fanorona_prog06.PvP_board.r;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    public static int g_mode=0;
    public static Partie game;
    public static int charge_game=0;
    
    @FXML
    private Slider volume_slider;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
    private AnchorPane main_menu;

    @FXML
    private ImageView quit_btn;

    @FXML
    private ImageView game_name;

    @FXML
    private ImageView PvP_btn;

    @FXML
    private ImageView PvA_btn;

    @FXML
    private ImageView opt_btn;

    @FXML
    private ImageView tuto_btn;

    @FXML
    void PvA_btn(MouseEvent event) throws IOException {
        g_mode=2;
        game = new Partie();
        Stage stage= new Stage();
        Parent r1 = FXMLLoader.load(getClass().getResource("IA_lvl.fxml"));
            
        Scene scene = new Scene(r1);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void PvP_btn(MouseEvent event) throws IOException {
        g_mode=1;
        Stage stage= new Stage();
        Parent r1 = FXMLLoader.load(getClass().getResource("board_PvP.fxml"));
        
        Scene scene = new Scene(r1);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        

    }

    @FXML
    void opt_btn(MouseEvent event) throws IOException {
        Stage stage = (Stage)opt_btn.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("Options_menu.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private ImageView load_btn;
    
    @FXML
    void load_game(MouseEvent event) throws IOException{

        if(charge_game==1){
            Stage stage= new Stage();
            Parent r1 = FXMLLoader.load(getClass().getResource("board_PvP.fxml"));
        
            Scene scene = new Scene(r1);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }else{
            Stage stage= new Stage();
            Parent r1 = FXMLLoader.load(getClass().getResource("board_PvA.fxml"));
        
            Scene scene = new Scene(r1);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
    }
    
    
    @FXML
    void quit_btn(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void tuto_btn(MouseEvent event) throws IOException {
        Stage stage = (Stage)tuto_btn.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("tuto_zero.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}

    
   
