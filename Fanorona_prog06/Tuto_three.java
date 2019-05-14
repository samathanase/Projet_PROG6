package Fanorona_prog06;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tuto_three {

    @FXML
    private AnchorPane tuto_scene3;

    @FXML
    private Text tuto_title;

    @FXML
    private Rectangle rect_tuto2;

    @FXML
    private Button return_to_tuto;

    @FXML
    private Button next_btn;

    @FXML
    private Text tuto2_r1;

    @FXML
    private Text tutor4;

    @FXML
    private Button prec_btn;

    @FXML
    private Text tuto2_r4;

    @FXML
    private Rectangle rect_tuto21;

    @FXML
    private ImageView tuto3_img;

    @FXML
    private Text page_num;

    @FXML
    void prec_btn(ActionEvent event) throws IOException {
        Stage stage = (Stage)prec_btn.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("tuto_two.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void return_to_tuto(ActionEvent event) throws IOException {
        Stage stage = (Stage)return_to_tuto.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

}
