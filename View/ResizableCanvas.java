package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class ResizableCanvas extends Canvas{
	public ResizableCanvas(){

	}

    	@Override
    	public boolean isResizable() {
      		return true;
    	}

   	@Override
    	public double prefWidth(double height) {
      		return getWidth();
    	}

    	@Override
    	public double prefHeight(double width) {
      		return getHeight();
    	}
}
