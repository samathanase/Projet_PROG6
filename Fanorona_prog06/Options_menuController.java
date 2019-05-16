package fanorona_prog06;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Options_menuController implements Initializable {
    public int j1=1,j2;
    public int i1,i2,i3,i4,i5,i6;
    
    @FXML
    private AnchorPane opt_scene;

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
    void annuler_opt_btn(ActionEvent event) throws IOException {
        
        Stage stage = (Stage)annuler_opt_btn.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private TextField text_bar;
    
    @FXML
    void sauvegarder_opt_btn(ActionEvent event) throws IOException {
        
        System.out.println("joueur1: "+j1+"joueur2: "+j2);
        if(text_bar.getText().trim().isEmpty()){
            System.out.println("null");
        }else{
            System.out.println(text_bar.getText());
        }
        System.out.println("j1:"+i1+"-j2:"+i2+"-j3:"+i3+"-j4:"+i4+"-j5:"+i5+"-j6:-"+i5);
        
        /*
        switch(expression) {
            case x:
                // code block
            break;
            case y:
                // code block
            break;
            default:
            // code block
        }
        */
        
    }
    
    @FXML
    private RadioButton j_radio1;

    @FXML
    private RadioButton j_radio2;
    
    @FXML
    public void j1_select(ActionEvent event) {
        j1=1;
        j2=0;
    }

    @FXML
    public void j2_select(ActionEvent event) {
        j1=0;
        j2=1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // Group
        ToggleGroup group = new ToggleGroup();
        // Radio 1:
        j_radio1.setToggleGroup(group);
        j_radio1.setSelected(true);
 
        // Radio 2:
        j_radio2.setToggleGroup(group);
    
    }
    
    @FXML
    private ImageView ic1;

    @FXML
    private ImageView ic2;

    @FXML
    private ImageView ic3;

    @FXML
    private ImageView ic4;

    @FXML
    private ImageView ic5;

    @FXML
    private ImageView ic6;
    
    @FXML
    public void ic1_selected(MouseEvent event) {
        i1=1;
        i2=0;
        i3=0;
        i4=0;
        i5=0;
        i6=0;
    }

    @FXML
    public void ic2_selected(MouseEvent event) {
        i1=0;
        i2=1;
        i3=0;
        i4=0;
        i5=0;
        i6=0;
    }

    @FXML
    public void ic3_selected(MouseEvent event) {
        i1=0;
        i2=0;
        i3=1;
        i4=0;
        i5=0;
        i6=0;
    }

    @FXML
    public void ic4_selected(MouseEvent event) {
        i1=0;
        i2=0;
        i3=0;
        i4=1;
        i5=0;
        i6=0;
    }

    @FXML
    public void ic5_selected(MouseEvent event) {
        i1=0;
        i2=0;
        i3=0;
        i4=0;
        i5=1;
        i6=0;
    }

    @FXML
    public void ic6_selected(MouseEvent event) {
        i1=0;
        i2=0;
        i3=0;
        i4=0;
        i5=0;
        i6=1;
    }

}
