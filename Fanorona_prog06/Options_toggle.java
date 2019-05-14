package fanorona_prog06;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Options_toggle {

    @FXML
    private AnchorPane opt_scene;

    @FXML
    private RadioButton j_radio1;

    @FXML
    private RadioButton j_radio2;

    @FXML
    private Text opt_title;

    @FXML
    private Rectangle rect_opt;

    @FXML
    private Text lab_opt1;

    @FXML
    private Text lab_opt2;

    @FXML
    private ImageView color_red;

    @FXML
    private ImageView color_green;

    @FXML
    private ImageView color_blue;

    @FXML
    private ImageView color_white;

    @FXML
    private ImageView color_black;

    @FXML
    private ImageView color_yellow;

    @FXML
    private Text lab_opt21;

    @FXML
    private Rectangle rect_opt1;

    @FXML
    private Button annuler_opt_btn;

    @FXML
    private Button sauvegarder_opt_btn;

    @FXML
    void annuler_opt_btn(ActionEvent event) {
        Stage stage = (Stage)annuler_opt_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void sauvegarder_opt_btn(ActionEvent event) {
        Stage stage = (Stage)sauvegarder_opt_btn.getScene().getWindow();
        stage.close();
    }

}
