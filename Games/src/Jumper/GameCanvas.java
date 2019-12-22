package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.scene.layout.*;
public class GameCanvas extends Canvas {
	
	public GameCanvas() {
		widthProperty().addListener(evt -> paint());
		widthProperty().addListener(evt -> paint());
	}
	
	public void paint() {
		GraphicsContext gc = getGraphicsContext2D();
		double width = this.getWidth();
		double height= this.getHeight();
		System.out.println(width+" "+height);
		
		gc.clearRect(0, 0, width, height);
		gc.setStroke(new Color(0.5, 0.5, 0.5, 1));

		gc.strokeLine(0, 0, width, height);
		gc.strokeLine(0, 0, 100, 100);
		
		gc.strokeRect(10, 10, width-20, height-20);
		
		System.out.println("EEE");
		

		gc.strokeRect(50, 50, width-100, height-100);
		gc.strokeRect(51, 51, width-102, height-102);
		gc.strokeRect(52, 52, width-104, height-104);
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
