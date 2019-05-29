package Fanorona_prog06;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class victoire implements Initializable{

    @FXML
    private Text gagnant;

    @FXML
    private ImageView return_to_main;

    @FXML
    void return_to_main(MouseEvent event) throws IOException {
            Stage stage= new Stage();
            Parent r1 = FXMLLoader.load(getClass().getResource("FXMLDocument_resisable.fxml"));
            
            Scene scene = new Scene(r1);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            String image = Fanorona_prog06.class.getResource("buttons/fanorona_bg.png").toExternalForm();
            r1.setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-size: 950 788 ; "
                + "-fx-background-position: center center; "
                + "-fx-background-size: cover, auto;"
                + "-fx-background-repeat: stretch;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(PvP_board.gagn==1){
            gagnant.setText(PvP_board.p1);
        }
        if(PvP_board.gagn==2){
            gagnant.setText(PvP_board.p2);
        }
    }

}
