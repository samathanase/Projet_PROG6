/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */





package Fanorona_prog06;

import Controller.Controller;
import Controller.IA_Controller;
import Modele.Partie;
import View.GridView;
import View.ResizableCanvas;
//import static Fanorona_prog06.PvP_board.canv;
//import static Fanorona_prog06.PvP_board.r;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    @FXML
    private AnchorPane main_menu;

    @FXML
    private Button quit_btn;

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
        Stage stage = (Stage)PvA_btn.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("board_PvA.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        //stage.setResizable(false);
        scene.getWindow().centerOnScreen();
        stage.show();
    }

    @FXML
    void PvP_btn(MouseEvent event) throws IOException {
        Stage stage = (Stage)PvP_btn.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("board_PvP.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.setResizable(false);
        scene.getWindow().centerOnScreen();
        stage.show();
        
        //start_cnv();
        
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
    void quit_btn(ActionEvent event) {
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

    
   
