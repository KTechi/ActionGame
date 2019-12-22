package application;
	
import javafx.animation.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.event.*;

public class Main extends Application implements EventHandler {
	// Components
	private Label time_L, life_L;
	private int time, life;
	private Button pause;
	private MenuItem[] world = new MenuItem[3];
	private GameCanvas gc;
	
	private boolean isRunning = false;
	private int tmpTime = 0;
	
	// Animation
	private Timeline timeline;
	private int duration = 100;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		// Label (time)
		time = 0;
		time_L = new Label(String.valueOf(time));
		time_L.setTextFill(Color.BLACK);
		time_L.setFont(Font.font("Serif", 30));
		
		// Label (life)
		life = 3;
		life_L = new Label("[ O O X ]  ");
		life_L.setTextFill(Color.BLACK);
		life_L.setFont(Font.font("Serif", 30));
		
		// Button (pause)
		pause = new Button("Start");
		pause.setFont(Font.font("Serif", 15));
		pause.setOnAction(this);
		
		// MenuBar (world)
		MenuBar menub = new MenuBar();
		Menu menu = new Menu("World");
		for (int i = 0; i < world.length; i++) {
			world[i] = new MenuItem("W-" + String.valueOf(i+1));
			world[i].setOnAction(this);
		}
		menu.getItems().addAll(world);
		menub.getMenus().addAll(menu);
		
		// Canvas (GameCanvas)
		gc = new GameCanvas();
		gc.addEventHandler(KeyEvent.ANY, new GameCanvasEventHandler());
		
		///////////////////////////////////////////////
		//////////////// Pane (layout) ////////////////
		
		// Top
		FlowPane fp = new FlowPane();
		fp.getChildren().add(menub);
		fp.getChildren().add(pause);
		BorderPane bpTop = new BorderPane();
		bpTop.setLeft(fp);
		bpTop.setCenter(time_L);
		bpTop.setRight(life_L);
		
		// Center
		BorderPane bpGame = new BorderPane();
		gc.heightProperty().bind(bpGame.heightProperty());
		gc.widthProperty().bind(bpGame.widthProperty());
		bpGame.getChildren().add(gc);
		
		// All
		BorderPane bpMain = new BorderPane();
		bpMain.setTop(bpTop);
		bpMain.setCenter(bpGame);
		
		//////////////// Pane (layout) ////////////////
		///////////////////////////////////////////////
		
		// Scene
		Scene sc = new Scene(bpMain, 300, 200);
		
		// Stage
		primaryStage.setTitle("GameFX");
		primaryStage.setScene(sc);
		primaryStage.show();
		startApp();
	}
	
	private void startApp() {
		KeyFrame kf = new KeyFrame(
				new Duration(duration),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						gc.requestFocus();
						if (isRunning) {
							// 1 second
							tmpTime += duration;
							if (1000 < tmpTime) {
								tmpTime -= 1000;
								time_L.setText(String.valueOf(++time));
							}
							gc.paint();
						}
					}
				}
			);
		
		timeline = new Timeline(kf);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	@Override
	public void handle(Event event) {
		if (event.getTarget().equals(pause)) {
			if (isRunning) pause.setText("resume");
			else pause.setText("pause");
			isRunning = !isRunning;
			System.out.println("Button Push");
		}
		else if (((MenuItem)event.getSource()).equals(world[0])) {
			System.out.println("World 1");
		}
		else if (((MenuItem)event.getSource()).equals(world[1])) {
			System.out.println("World 2");
		}
		else if (((MenuItem)event.getSource()).equals(world[2])) {
			System.out.println("World 3");
		}
		
		gc.paint();
	}
	
	class GameCanvasEventHandler implements EventHandler {
		@Override
		public void handle(Event event) {
			if (!(event instanceof KeyEvent)) {
				System.out.println("Error [GameCanvasEventHandler]");
				return;
			}
			
			if (event.getEventType() == KeyEvent.KEY_PRESSED) {
				System.out.println("押した");
			}
			else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
				System.out.println("離した");
			}
		}
	}
}