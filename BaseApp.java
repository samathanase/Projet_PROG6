import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.beans.*;
import javafx.scene.paint.Color;

public class BaseApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

	ResizableCanvas cnv = new ResizableCanvas();
	GridView gv = new GridView();

	Pane root = new Pane();
	root.getChildren().add(cnv);


	cnv.widthProperty().bind(root.widthProperty());
	cnv.heightProperty().bind(root.heightProperty());	
	cnv.widthProperty().addListener((Observable o) -> {gv.draw(cnv);});
	cnv.heightProperty().addListener((Observable o) -> {gv.draw(cnv);});

	gv.draw(cnv);

	//GraphicsContext gc = cnv.getGraphicsContext2D();
	//gc.setFill(Color.RED);
	//gc.fillRect(0,0,10,10);


        primaryStage.setScene(new Scene(root, 800, 600));


	primaryStage.show();
    }
}
