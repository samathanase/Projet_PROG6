package Fanorona_prog06;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tuto1_toggle {

    @FXML
    private AnchorPane tuto_menu_toggle;

    @FXML
    private Text tuto_title_toggle;

    @FXML
    private Button return_to_main_toggle;

    @FXML
    private ImageView img_tuto_toggle1;

    @FXML
    private ImageView prec_toggle_disabled;

    @FXML
    private ImageView next_toggle;

    @FXML
    void next_toggle(MouseEvent event) throws IOException {
        Stage stage = (Stage)next_toggle.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("tuto2_toggle.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void return_to_main_toggle(ActionEvent event) throws IOException {
        PvP_board pvp = new PvP_board() ;
        Stage stage = (Stage)return_to_main_toggle.getScene().getWindow();
        stage.close();
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
        ((Node)(event.getSource())).getScene().getWindow().hide();
        //pvp.fermer_scene();
    }

}
