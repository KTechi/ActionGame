package application;

import javafx.animation.*;
import javafx.event.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import characters.*;

public class GameCanvas extends Canvas {
	GraphicsContext gc = getGraphicsContext2D();
	private static int cellSize = 50;
	public int fieldWidth = 20 * 50;
	public int fieldLeft = 0;////////////////////////////////->private
	int playerFrame = 0;

	private static int[][] field = Field.field;

	// Animation
	private Timeline timelineUp, timelineDown, timelineL, timelineR;
	private int duration = 3;
	
	// Move Object
	private Player player;
	
	public GameCanvas() {
		widthProperty().addListener(evt -> paint());
		widthProperty().addListener(evt -> paint());

		timelineMoveUp();
		timelineMoveDown();
		timelineMoveL();
		timelineMoveR();
		
		player = new Player(this);
		player.start();
	}

	public void paint() {
		double w = this.getWidth();
		double h = this.getHeight();
		if (w < 100 || h < 100) return;

		gc.clearRect(0, 0, w, h);

		// Print field
		for (int y = 0; y < field.length; y++)
		for (int x = -fieldLeft / cellSize; x < (w - fieldLeft) / cellSize; x++) {
			if (x < 0 || field[y].length <= x) continue;
			if (field[y][x] == 0) continue;

			int tmpX = fieldLeft + x * cellSize;
			int tmpY = (int)(h - (field.length - y) * cellSize);

			switch (field[y][x]) {
				case -1: printBlock1(tmpX, tmpY); break;
				case  1: printBlock1(tmpX, tmpY); break;
				case  2: printBlock2(tmpX, tmpY); break;
				case  3: printBlock3(tmpX, tmpY); break;
				case  4: printBlock4(tmpX, tmpY); break;
				case  5: printBlock2(tmpX, tmpY); break;
				case  6: printElse(tmpX, tmpY); break;
				case  7: printElse(tmpX, tmpY); break;
				case  8: printElse(tmpX, tmpY); break;
				case  9: printElse(tmpX, tmpY); break;
				default: System.out.println("Error Block");
			}
		}

		// Print frame
		gc.setStroke(new Color(0.1, 0.1, 0.1, 1));
		gc.setLineWidth(cellSize / 2);
		gc.strokeRect(0, 0, w, h);

		printPlayer(player.x, player.y);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////// P R I N T   B L O C K S ////////////////////////////////
	private void printBlock1(int x, int y) {
		gc.setFill(new Color(0.3, 0.15, 0.05, 1));
		gc.fillRect(x, y, cellSize, cellSize);
		gc.setFill(new Color(0.5, 0.3, 0.2, 1));
		gc.fillRect(x+4, y+4, cellSize-8, cellSize-8);
	}
	private void printBlock2(int x, int y) {
		gc.setFill(new Color(0.7, 0.7, 0, 1));
		gc.fillRect(x, y, cellSize, cellSize);
		gc.setFill(new Color(0.9, 0.9, 0, 1));
		gc.fillRect(x+4, y+4, cellSize-8, cellSize-8);
	}
	private void printBlock3(int x, int y) {
		gc.setFill(new Color(0.7, 0.7, 0, 1));
		gc.fillRect(x, y, cellSize, cellSize);
		gc.setFill(new Color(0.9, 0.9, 0, 1));
		gc.fillRect(x+4, y+4, cellSize-8, cellSize-8);
	}
	private void printBlock4(int x, int y) {
		gc.setFill(new Color(0.1, 0.1, 0.1, 1));
		gc.fillRect(x, y, cellSize, cellSize);
		gc.setFill(new Color(0.4, 0.4, 0.4, 1));
		gc.fillRect(x+4, y+4, cellSize-8, cellSize-8);
	}
	private void printElse(int x, int y) {
		gc.setFill(new Color(0.4, 0.0, 0.0, 1));
		gc.fillRect(x, y, cellSize, cellSize);
		gc.setFill(new Color(0.5, 0.1, 0.1, 1));
		gc.fillRect(x+4, y+4, cellSize-8, cellSize-8);
	}
	private void printPlayer(int x, int y ) {
		double[][][] points = {
			{// Frame 0
				{0.513, 0.342, 0.176, 0.122, 0.142, 0.198, 0.231, 0.258, 0.347, 0.358, 0.347, 0.307, 0.262, 0.187, 0.044, 0.018, 0.040, 0.138, 0.220, 0.313, 0.387, 0.436, 0.520, 0.582, 0.653, 0.691, 0.729, 0.769, 0.751, 0.700, 0.647, 0.582, 0.582, 0.584, 0.578, 0.647, 0.691, 0.731, 0.787, 0.816, 0.798, 0.749, 0.693, 0.671, 0.582, 0.531},
				{0.280, 0.284, 0.360, 0.509, 0.556, 0.562, 0.482, 0.409, 0.364, 0.460, 0.567, 0.673, 0.764, 0.822, 0.838, 0.869, 0.911, 0.907, 0.884, 0.822, 0.736, 0.664, 0.724, 0.780, 0.858, 0.947, 0.973, 0.944, 0.871, 0.778, 0.682, 0.636, 0.571, 0.493, 0.453, 0.498, 0.520, 0.498, 0.436, 0.384, 0.329, 0.318, 0.391, 0.422, 0.347, 0.296}
			},{// Frame 1
				{0.504, 0.376, 0.298, 0.213, 0.191, 0.204, 0.258, 0.282, 0.273, 0.269, 0.302, 0.362, 0.411, 0.409, 0.389, 0.378, 0.349, 0.309, 0.216, 0.158, 0.131, 0.144, 0.233, 0.358, 0.413, 0.447, 0.460, 0.498, 0.520, 0.540, 0.542, 0.527, 0.587, 0.631, 0.611, 0.576, 0.542, 0.549, 0.560, 0.598, 0.664, 0.731, 0.813, 0.842, 0.827, 0.796, 0.753, 0.662, 0.607, 0.562, 0.529},
				{0.260, 0.264, 0.322, 0.442, 0.527, 0.609, 0.620, 0.609, 0.556, 0.504, 0.427, 0.356, 0.351, 0.444, 0.580, 0.689, 0.791, 0.824, 0.813, 0.800, 0.829, 0.869, 0.882, 0.878, 0.838, 0.749, 0.658, 0.693, 0.749, 0.807, 0.871, 0.944, 0.958, 0.880, 0.796, 0.682, 0.631, 0.551, 0.511, 0.531, 0.571, 0.569, 0.549, 0.509, 0.462, 0.462, 0.480, 0.469, 0.440, 0.400, 0.271}
			},{// Frame 2
				{0.493, 0.449, 0.373, 0.353, 0.324, 0.309, 0.327, 0.373, 0.393, 0.373, 0.389, 0.407, 0.413, 0.413, 0.413, 0.409, 0.416, 0.424, 0.353, 0.287, 0.202, 0.178, 0.198, 0.269, 0.358, 0.456, 0.513, 0.496, 0.473, 0.536, 0.536, 0.511, 0.480, 0.496, 0.564, 0.611, 0.604, 0.547, 0.542, 0.542, 0.569, 0.624, 0.693, 0.691, 0.618, 0.571, 0.564, 0.582, 0.580, 0.527},
				{0.269, 0.269, 0.289, 0.362, 0.460, 0.529, 0.631, 0.662, 0.627, 0.538, 0.458, 0.413, 0.440, 0.538, 0.596, 0.673, 0.758, 0.813, 0.816, 0.813, 0.811, 0.856, 0.896, 0.907, 0.911, 0.909, 0.856, 0.787, 0.709, 0.780, 0.851, 0.904, 0.956, 0.987, 0.987, 0.878, 0.780, 0.678, 0.642, 0.584, 0.611, 0.638, 0.638, 0.569, 0.522, 0.500, 0.449, 0.367, 0.296, 0.264}
			},{// Frame 3
				{0.489, 0.418, 0.398, 0.378, 0.364, 0.369, 0.400, 0.444, 0.462, 0.462, 0.456, 0.404, 0.353, 0.340, 0.351, 0.433, 0.431, 0.431, 0.456, 0.484, 0.511, 0.604, 0.622, 0.591, 0.578, 0.564, 0.547, 0.580, 0.602, 0.602, 0.591, 0.584, 0.584, 0.584, 0.582, 0.580, 0.533},
				{0.242, 0.269, 0.336, 0.404, 0.518, 0.564, 0.651, 0.669, 0.720, 0.749, 0.809, 0.811, 0.811, 0.844, 0.887, 0.887, 0.927, 0.953, 0.964, 0.964, 0.889, 0.862, 0.818, 0.778, 0.742, 0.696, 0.667, 0.658, 0.656, 0.604, 0.582, 0.567, 0.504, 0.431, 0.353, 0.289, 0.238}
			},{// Frame 4
				{0.473, 0.413, 0.347, 0.316, 0.313, 0.356, 0.329, 0.342, 0.382, 0.378, 0.329, 0.289, 0.296, 0.344, 0.407, 0.476, 0.509, 0.509, 0.500, 0.496, 0.536, 0.562, 0.587, 0.587, 0.558, 0.522, 0.531, 0.538, 0.578, 0.622, 0.673, 0.678, 0.647, 0.587, 0.549, 0.529, 0.536, 0.531, 0.522},
				{0.216, 0.216, 0.280, 0.364, 0.484, 0.549, 0.611, 0.633, 0.658, 0.729, 0.864, 0.951, 0.976, 0.982, 0.900, 0.727, 0.740, 0.807, 0.891, 0.922, 0.956, 0.924, 0.827, 0.736, 0.680, 0.642, 0.569, 0.551, 0.580, 0.609, 0.609, 0.569, 0.522, 0.493, 0.471, 0.431, 0.360, 0.282, 0.240}
			},{// Frame 5
				{0.482, 0.407, 0.333, 0.278, 0.249, 0.229, 0.233, 0.269, 0.304, 0.309, 0.309, 0.320, 0.351, 0.411, 0.409, 0.384, 0.371, 0.356, 0.329, 0.296, 0.224, 0.147, 0.109, 0.111, 0.149, 0.262, 0.360, 0.416, 0.433, 0.453, 0.473, 0.513, 0.547, 0.580, 0.604, 0.618, 0.644, 0.680, 0.691, 0.669, 0.627, 0.580, 0.529, 0.542, 0.576, 0.609, 0.662, 0.700, 0.749, 0.760, 0.749, 0.711, 0.689, 0.631, 0.598, 0.564, 0.544, 0.544, 0.536},
				{0.236, 0.236, 0.278, 0.342, 0.416, 0.516, 0.624, 0.669, 0.651, 0.609, 0.549, 0.451, 0.380, 0.344, 0.473, 0.644, 0.711, 0.784, 0.822, 0.833, 0.860, 0.889, 0.911, 0.942, 0.960, 0.940, 0.907, 0.831, 0.740, 0.682, 0.716, 0.751, 0.784, 0.833, 0.893, 0.947, 0.973, 0.971, 0.931, 0.836, 0.751, 0.662, 0.616, 0.511, 0.522, 0.540, 0.533, 0.500, 0.467, 0.442, 0.409, 0.391, 0.422, 0.451, 0.442, 0.400, 0.364, 0.320, 0.271}
			}
		};

		for (int i = 0; i < points[playerFrame][0].length; i++) {
			points[playerFrame][0][i] = (double)x + player.width * points[playerFrame][0][i];
			points[playerFrame][1][i] = (double)y + player.height * points[playerFrame][1][i];
		}

		switch (playerFrame) {
			case 0: gc.fillOval(x+11*player.width/24, y      , player.height/4, player.height/4); break;
			case 1: gc.fillOval(x+10*player.width/24, y+player.height/20, player.height/4, player.height/4); break;
			case 2: gc.fillOval(x+ 9*player.width/24, y+player.height/15, player.height/4, player.height/4); break;
			case 3: gc.fillOval(x+ 9*player.width/24, y+player.height/17, player.height/4, player.height/4); break;
			case 4: gc.fillOval(x+10*player.width/24, y+player.height/20, player.height/4, player.height/4); break;
			case 5: gc.fillOval(x+11*player.width/24, y      , player.height/4, player.height/4); break;
			default: gc.fillOval(x, y, player.height, player.height); break;
		}
		gc.fillPolygon(points[playerFrame][0], points[playerFrame][1], points[playerFrame][0].length);
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////

	public int blockIs(int x, int y) {
		double w = this.getWidth();
		double h = this.getHeight();
		if (w < 100 || h < 100) return 1;
		
		return field[(int)(field.length - (getHeight() - y) / cellSize)][(x - fieldLeft) / cellSize];
	}

	////////////////////////////////

	public void moveUp() {
		timelineUp.play();
	}
	public void stopMovingUp() {
		timelineUp.pause();
	}
	private void timelineMoveUp() {
		timelineUp = new Timeline(
			new KeyFrame(
				new Duration(duration),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						player.move_up();
					}
				}
			)
		);
		timelineUp.setCycleCount(Timeline.INDEFINITE);
	}

	////////////////////////////////

	public void moveDown() {
		timelineDown.play();
	}
	public void stopMovingDown() {
		timelineDown.pause();
	}
	private void timelineMoveDown() {
		timelineDown = new Timeline(
			new KeyFrame(
				new Duration(duration),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						player.move_down();
					}
				}
			)
		);
		timelineDown.setCycleCount(Timeline.INDEFINITE);
	}

	////////////////////////////////

	public void moveLeft() {
		timelineL.play();
	}
	public void stopMovingLeft() {
		timelineL.pause();
	}
	private void timelineMoveL() {
		timelineL = new Timeline(
				new KeyFrame(
					new Duration(duration),
					new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if (player.move_left()) return;
							fieldLeft++;
						}
					}
				)
			);
			timelineL.setCycleCount(Timeline.INDEFINITE);
		}

	////////////////////////////////

	public void moveRight() {
		timelineR.play();
	}
	public void stopMovingRight() {
		timelineR.pause();
	}
	private void timelineMoveR() {
		timelineR = new Timeline(
			new KeyFrame(
				new Duration(duration),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if (player.move_right()) return;
						fieldLeft--;
					}
				}
			)
		);
		timelineR.setCycleCount(Timeline.INDEFINITE);
	}
}
