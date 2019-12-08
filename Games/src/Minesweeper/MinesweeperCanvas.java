package Minesweeper;

import java.awt.*;

public class MinesweeperCanvas extends Canvas {
	private static final long serialVersionUID = 1L;///////////////////////////
	private int size = 10;
	private int cellSize;
	private boolean[][] flag;
	private boolean[][] block;
	private int[][] bomb;
	private boolean isRunning = false;
	boolean ready = false;

	int bombNum = 10;
	int result = 0;

	@Override
	public void paint(Graphics g) {
		int w = this.getWidth();
		int h = this.getHeight();

		g.setColor(new Color(50, 50, 50));
		g.fillRect(0, 0, w, h);

		if (!ready) return;

		cellSize = (w < h) ? w / size : h / size;
		int wd = (w - cellSize * size) / 2;
		int hd = (h - cellSize * size) / 2;

		for (int i = 0; i < size; i++)
		for (int j = 0; j < size; j++) {
			String str = "Minesweeper/";
			if (flag[i][j]) str += "flag";
			else if (block[i][j]) str += "block";
			else if (bomb[i][j] == 0) str += "0";
			else if (bomb[i][j] == 1) str += "1";
			else if (bomb[i][j] == 2) str += "2";
			else if (bomb[i][j] == 3) str += "3";
			else if (bomb[i][j] == 4) str += "4";
			else if (bomb[i][j] == 5) str += "5";
			else if (bomb[i][j] == 6) str += "6";
			else if (bomb[i][j] == 7) str += "7";
			else if (bomb[i][j] == 8) str += "8";
			else if (bomb[i][j] == -1) str += "bomb";
			else str += "error";
			str += ".png";
			g.drawImage(Toolkit.getDefaultToolkit().getImage(str), j * cellSize + wd, i * cellSize + hd, cellSize, cellSize, this);
		}

		if (result == 1)	  g.drawImage(Toolkit.getDefaultToolkit().getImage("Cells/gameOver.png"), w / 12, h / 3, w * 5 / 6, h * 1 / 3, this);
		else if (result == 2) g.drawImage(Toolkit.getDefaultToolkit().getImage("Cells/clear.png"),    w / 12, h / 6, w * 5 / 6, h * 2 / 3, this);
	}

	public void creatField(int s, int n) {
		bombNum = n;
		size = s;
		flag = new boolean[size][size];
		block = new boolean[size][size];
		bomb = new int[size][size];
		for (int i = 0; i < size; i++)
		for (int j = 0; j < size; j++) {
			flag[i][j] = false;
			block[i][j] = true;
			bomb[i][j] = 0;
		}
	}

	public boolean breakBlock(int y, int x) {
		int w = this.getWidth();
		int h = this.getHeight();
		cellSize = (w < h) ? w / size : h / size;
		int wd = (w - cellSize * size) / 2;
		int hd = (h - cellSize * size) / 2;
		y = (y - hd) / cellSize;
		x = (x - wd) / cellSize;

		if (!isRunning) {
			for (int i = 0; i < bombNum;) {
				int yy = (int)(Math.random() * size);
				int xx = (int)(Math.random() * size);
				boolean tmp = false;

				for (int j = -1; j <= 1; j++)
				for (int k = -1; k <= 1; k++)
					if ((yy+j == y && xx+k == x) || bomb[yy][xx] == -1)
						tmp = true;

				if (tmp) continue;
				i++;

				bomb[yy][xx] = -1;
				for (int j = -1; j <= 1; j++)
				for (int k = -1; k <= 1; k++) {
					if (0 <= yy+j && yy+j < size &&
						0 <= xx+k && xx+k < size &&
						bomb[yy+j][xx+k] != -1 ) {
						bomb[yy+j][xx+k]++;
					}
				}
			}
			isRunning = true;
		}

		if (y < 0 || size <= y || x < 0 || size <= x) return false;
		if (flag[y][x]) return false;
		if (!block[y][x]) return false;

		block[y][x] = false;

		if (bomb[y][x] == -1) {
			gameover();
			return false;
		}
		else if (bomb[y][x] == 0) reveal(y, x);

		if (checkClear()) {
			for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				block[i][j] = false;
			return true;
		}
		return false;
	}

	public void flag(int y, int x) {
		int w = this.getWidth();
		int h = this.getHeight();
		cellSize = (w < h) ? w / size : h / size;
		int wd = (w - cellSize * size) / 2;
		int hd = (h - cellSize * size) / 2;

		y = (y - hd) / cellSize;
		x = (x - wd) / cellSize;
		if (y < 0 || size <= y || x < 0 || size <= x) return;
		if (!block[y][x]) return;
		flag[y][x] = !flag[y][x];
	}

	private void reveal(int y, int x) {
		block[y][x] = flag[y][x] = false;

		for (int i = -1; i <= 1; i++)
		for (int j = -1; j <= 1; j++)
			if (0 <= y+i && y+i < size && 0 <= x+j && x+j < size && block[y+i][x+j] && bomb[y+i][x+j] == 0) reveal(y+i, x+j);

		for (int i = -1; i <= 1; i++)
		for (int j = -1; j <= 1; j++)
			if (0 <= y+i && y+i < size && 0 <= x+j && x+j < size && bomb[y+i][x+j] != 0)
			    flag[y+i][x+j] = block[y+i][x+j] = false;
	}

	public void gameover() {
		for (int i = 0; i < size; i++)
		for (int j = 0; j < size; j++)
			block[i][j] = flag[i][j] = false;

		System.out.println("Game Over");
		result = 1;
	}

	public void quit() {

	}

	private boolean checkClear() {
		for (int i = 0; i < size; i++)
		for (int j = 0; j < size; j++)
			if (block[i][j] && bomb[i][j] != -1)
				return false;
		return true;
	}

	public void clearGame() {
		for (int i = 0; i < size; i++)
		for (int j = 0; j < size; j++)
			block[i][j] = flag[i][j] = false;
		result = 2;
	}
}
