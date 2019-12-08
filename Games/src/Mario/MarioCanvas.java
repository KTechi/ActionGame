package Mario;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MarioCanvas extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private int windowLeft = 0;
	private int fieldWidth = 1000;
	private int cellSize = 40;
	public int jumpPower = 0;
	public MovingObject mo = new MovingObject(200, 100, cellSize, cellSize);

	public MarioCanvas() {
		Thread th = new Thread(this);
		th.start();
	}
	
	private int[][] field = {
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 4, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 1, 0, 0, 0, 0, 0, 0},
		{4, 4, 0, 0, 1, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
		{4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
		{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
	};
	
	@Override
	public void paint(Graphics g) {
		int w = this.getWidth();
		int h = this.getHeight();
		g.setColor(new Color(108, 177, 231/*0,0,0*/));
		g.fillRect(0, 0, w, h);
		
		String str;
		
		for (int i = 0; i < field.length; i++)
		for (int j = 0; j < field[0].length; j++) {
			str = "Mario/";
			if (field[i][j] == 0) continue;

			if (field[i][j] == 1) str += "Blocks/BrickBlock";
			else if (field[i][j] == 2) str += "Blocks/QuestionBlock";
			else if (field[i][j] == 3) str += "Blocks/EmptyBlock";
			else if (field[i][j] == 4) str += "Blocks/StructureBlock";
			else if (field[i][j] == 7) str += "Items/Mushroom";
			
			
			str += "_LQ.png";
			g.drawImage(Toolkit.getDefaultToolkit().getImage(str),
					windowLeft + j * cellSize,// x
					h - (field.length - i)*cellSize,// y
					cellSize,// width
					cellSize,// height
					this
				);
		}

		g.drawImage(Toolkit.getDefaultToolkit().getImage("Mario/Structures/ShortPipe_LQ.png"),
				windowLeft + 10 * cellSize,// x
				h - (field.length - 8) * cellSize,// y
				2 * cellSize, 2 * cellSize, this);
		g.drawImage(Toolkit.getDefaultToolkit().getImage("Mario/Items/Mushroom_LQ.png"),
				mo.x, mo.y, mo.w, mo.h, this);
	}
	
	@Override
	public void run() {
//		while (true) {
//			if (mo.y + )
//		}
	}
	
	public void jump() {
		mo.y -= 30;
		jumpPower = 2;
		System.out.println("hewr");
	}

	public void moveRight() {
		if (field[field.length - (this.getHeight() - mo.y) / cellSize - 1][(-windowLeft + mo.x+mo.w) / cellSize] != 0) return;
	
		if (windowLeft + fieldWidth <= this.getWidth()) {
			if (windowLeft + fieldWidth <= mo.x + mo.w) return;
			else mo.x += 10;
		} else if (this.getWidth() * 0.8 - mo.w < mo.x) windowLeft -= 10;
		else mo.x += 10;
	}
	
	public void moveLeft() {
		if (field[field.length - (this.getHeight() - mo.y) / cellSize - 1][(-windowLeft + mo.x) / cellSize] != 0) return;
		
		if (0 <= windowLeft) {
			if (mo.x <= 0) return;
			else mo.x -= 10;
		} else if (mo.x < this.getWidth() * 0.2) windowLeft += 10;
		else mo.x -= 10;
	}
	
	public void crouch() {
		mo.y += 10;
	}
	
	public boolean gravity(int clock) {
		if (field[field.length - (this.getHeight() - mo.y - mo.h) / cellSize - 1][(-windowLeft + mo.x) / cellSize] != 0 ||
			field[field.length - (this.getHeight() - mo.y - mo.h) / cellSize - 1][(-windowLeft + mo.x+mo.w) / cellSize] != 0 ) return false;
		
		mo.y += (int) (clock * clock / 2 / 1000) - jumpPower;
		
		repaint();
		return true;
	}
}