package application;

import javafx.animation.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.util.Duration;

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
	private int duration = 2;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		// Label (time)
		time = 0;
		time_L = new Label(String.valueOf(time));
		time_L.setTextFill(Color.WHITE);
		time_L.setFont(Font.font("Serif", 30));

		// Label (life)
		life = 3;
		life_L = new Label("[ O O O ]  ");
		life_L.setTextFill(Color.WHITE);
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
		// Top (World  pause  time  life)
		FlowPane fp = new FlowPane();
		fp.getChildren().add(menub);
		fp.getChildren().add(pause);
		BorderPane bpTop = new BorderPane();
		bpTop.setLeft(fp);
		bpTop.setCenter(time_L);
		bpTop.setRight(life_L);
		bpTop.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		// Center
		BorderPane bpGame = new BorderPane();
		gc.heightProperty().bind(bpGame.heightProperty());
		gc.widthProperty().bind(bpGame.widthProperty());
		bpGame.getChildren().add(gc);

		// All
		BorderPane bpMain = new BorderPane();
		bpMain.setTop(bpTop);
		bpMain.setCenter(bpGame);
		///////////////////////////////////////////////
		///////////////////////////////////////////////

		// Scene
		Scene sc = new Scene(bpMain, 600, 600);

		// Stage
		primaryStage.setTitle("GameFX");
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(600);
		primaryStage.setScene(sc);
		primaryStage.show();

		startApp();
		gc.paint();
	}

	private void startApp() {
		timeline = new Timeline(
			new KeyFrame(
				new Duration(duration),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						gc.requestFocus();
						tmpTime += duration;
						if (1000 < tmpTime) {
							tmpTime -= 1000;
							time_L.setText(String.valueOf(++time));
						}
						
						if (tmpTime % 100 == 0) gc.playerFrame = (gc.playerFrame + 1) % 6;///////////////////////////////////////
						
						gc.paint();
					}
				}
			)
		);
		timeline.setCycleCount(Timeline.INDEFINITE);
	}

	@Override
	public void handle(Event event) {
		if (event.getTarget().equals(pause)) {
			if (isRunning) {
				timeline.pause();
				pause.setText("resume");
			} else {
				timeline.play();
				pause.setText("pause");
			}
			isRunning = !isRunning;
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
		private boolean movingUp = false;
		private boolean movingDown = false;
		private boolean movingLeft = false;
		private boolean movingRight = false;

		@Override
		public void handle(Event event) {
			if (!isRunning) return;
			if (!(event instanceof KeyEvent)) {
				System.out.println("Error [GameCanvasEventHandler]");
				return;
			}
			KeyEvent e = (KeyEvent)event;

			if (event.getEventType() == KeyEvent.KEY_PRESSED) {
				switch (e.getCode()) {
					case UP:
					case W:
						if (movingUp) return;
						movingUp = true;
						movingDown = false;
						gc.stopMovingDown();
						gc.moveUp();
						break;
					case DOWN:
					case S:
						if (movingDown) return;
						movingDown = true;
						movingUp = false;
						gc.stopMovingUp();
						gc.moveDown();
						break;
					case LEFT:
					case A:
						if (movingLeft) return;
						movingLeft = true;
						movingRight = false;
						gc.stopMovingRight();
						gc.moveLeft();
						break;
					case RIGHT:
					case D:
						if (movingRight) return;
						movingRight = true;
						movingLeft = false;
						gc.stopMovingLeft();
						gc.moveRight();
						break;
					case SPACE:
						break;
					default:
						System.out.println("Error KeyPress");
						break;
				}
			} else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
				switch (e.getCode()) {
					case UP:
					case W:
						movingUp = false;
						gc.stopMovingUp();
						break;
					case DOWN:
					case S:
						movingDown = false;
						gc.stopMovingDown();
						break;
					case LEFT:
					case A:
						movingLeft = false;
						gc.stopMovingLeft();
						break;
					case RIGHT:
					case D:
						movingRight = false;
						gc.stopMovingRight();
						break;
					case SPACE:
						break;
					default:
						System.out.println("Error KeyPress");
						break;
				}
			}
		}
	}
}
