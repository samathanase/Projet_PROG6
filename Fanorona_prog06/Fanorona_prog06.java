/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fanorona_prog06;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Sami
 */
public class Fanorona_prog06 extends Application {
    public static AudioClip st;
    public static int music_mode;

    @Override
    public void start(Stage stage) throws Exception {
        
        
        //FXMLDocumentController x = new FXMLDocumentController();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument_resisable.fxml"));
        Scene scene = new Scene(root);
        
        //ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("red.png")));
        //imageView.setPreserveRatio(true);  //uncomment to keep image ratio.
        //imageView.fitHeightProperty().bind(stage.heightProperty());
        //imageView.fitWidthProperty().bind(stage.widthProperty());
        
        stage.setScene(scene);
        //stage.setResizable(false);
        scene.getWindow().centerOnScreen();
        stage.initStyle(StageStyle.DECORATED.UNDECORATED);
        //stage.setMaximized(true);
        stage.show();
        st = new AudioClip(this.getClass().getResource("board_pics/fanorona_OST.wav").toString());
        st.setCycleCount(500);
        st.play();
        music_mode=1;
        String image = Fanorona_prog06.class.getResource("buttons/fanorona_bg.png").toExternalForm();
        root.setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-size: 950 788 ; "
                + "-fx-background-position: center center; "
                + "-fx-background-size: cover, auto;"
                + "-fx-background-repeat: stretch;");
        



    
        
        

    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
