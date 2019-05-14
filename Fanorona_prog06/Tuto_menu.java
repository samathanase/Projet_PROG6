package Fanorona_prog06;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tuto_menu {

    @FXML
    private AnchorPane tuto_menu;

    @FXML
    private Text tuto_title;

    @FXML
    private Button return_to_main;

    @FXML
    private Button rules_btn;

    @FXML
    private Button how_to_btn;

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
    void rules_btn(ActionEvent event) throws IOException {
        Stage stage = (Stage)rules_btn.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("tuto_one.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

}
