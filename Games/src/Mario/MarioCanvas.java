package Mario;

import java.awt.*;

public class MarioCanvas extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private final int CELL_SIZE = 40;
	private final int FIELD_WIDTH = 30 * CELL_SIZE;
	private int fieldLeft = 0;
	private boolean jumping = false;
	public MovingObject movObj = new MovingObject(200, 100, CELL_SIZE, CELL_SIZE, "Mario/Enemies/Bobomb_LQ.png");

	public MarioCanvas() {
		Thread th = new Thread(this);
		th.start();
	}
	
	private int[][] field = {
		{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
		{4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
		{4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
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
		{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},//15
	};
	
	@Override
	public void paint(Graphics g) {
		int w = this.getWidth();
		int h = this.getHeight();
		
		g.setColor(new Color(108, 177, 231));
		g.fillRect(0, 0, w, h);
		
		String str;
		
		for (int i = 0; i < field.length; i++)
		for (int j = 0; j < field[0].length; j++) {
			if (field[i][j] == 0) continue;
			
			str = "Mario/";
			if (field[i][j] == 1) str += "Blocks/BrickBlock";
			else if (field[i][j] == 2) str += "Blocks/QuestionBlock";
			else if (field[i][j] == 3) str += "Blocks/EmptyBlock";
			else if (field[i][j] == 4) str += "Blocks/StructureBlock";
			else if (field[i][j] == 7) str += "Items/Mushroom";
			str += "_LQ.png";
			
			g.drawImage(Toolkit.getDefaultToolkit().getImage(str),
					fieldLeft + j * CELL_SIZE,// x
					h - (field.length - i) * CELL_SIZE,// y
					CELL_SIZE,// width
					CELL_SIZE,// height
					this
				);
		}
		
		int x = 10, y = 8;
		g.drawImage(Toolkit.getDefaultToolkit().getImage("Mario/Structures/ShortPipe_LQ.png"),
				fieldLeft + x * CELL_SIZE,// x
				h - (field.length - y) * CELL_SIZE,// y
				2 * CELL_SIZE, 2 * CELL_SIZE, this);
		
		g.drawImage(Toolkit.getDefaultToolkit().getImage(movObj.name),
				movObj.x, movObj.y, movObj.w, movObj.h, this);
	}
	
	@Override
	public void run() {
		// 画面が用意できるまで待つ。
		while (this.getHeight() < 100) stoplittle(100);
		
		int tic, time;
		while (true) {
			tic = 100;
			time = 0;
			
			stoplittle(10);
			//空中に浮いていて、ジャンプ中じゃない
			while (isFloating() && !jumping) {
				movObj.y += 1;
				repaint();
				stoplittle(tic);
				time += tic;
				//インターバルは時刻に反比例（時間が経てばたつほど、重力のインターバルは短くなる）
				if (2 <= 700 / time) tic = 700 / time;
			}
		}
	}
	
	private void stoplittle(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void jump() {
		//既に空中にいる OR 天井すれすれ
		if (isFloating() || upIsCeiling()) return;
		
		//空中に浮いている間「重力」を一旦無効化する
		jumping = true;
		
		for (int time = 450, tic = 2; 5 <= time;) {
			//上が天井なら強制終了
			if (upIsCeiling()) {
				jumping = false;
				return;
			}
			movObj.y -= 1;
			repaint();
			stoplittle(tic);
			time -= tic;
			//インターバルは時刻に反比例（時間が経てばたつほど、上昇のインターバルは長くなる）
			if (2 <= 700 / time) tic = 700 / time;
		}
		jumping = false;
	}

	public void moveDown() {
		if (!isFloating()) {
			//
			//しゃがみ（画像を差し替える）
			//
			System.out.println(movObj.name.charAt(15));
			if (movObj.name.charAt(14) == 'B') movObj.name = "Mario/Items/1UpMushroom_LQ.png";
			else movObj.name = "Mario/Enemies/Bobomb_LQ.png";
			stoplittle(100);
			repaint();
			return;
		}
		//作成途中
		movObj.y += 1;
		repaint();
	}
	
	public void moveLeft() {
		//左が壁なら
		if (leftIsWall()) return;
		
		//左端まで来た時の処理
		if (0 <= fieldLeft) {
			//プレイヤーが本当に左端のとき
			if (movObj.x <= 0) return;
			//まだ 20% が残っているとき
			else movObj.x -= 1;
		}//ウィンドウの 20% のところに来たら、プレイヤーではなく背景を動かす
		else if (movObj.x < this.getWidth() * 0.2) fieldLeft += 1;
		else movObj.x -= 1;
		
		repaint();
	}
	
	public void moveRight() {
		//右が壁なら
		if (rightIsWall()) return;
		
		//右端まで来た時の処理
		if (fieldLeft + FIELD_WIDTH <= this.getWidth()) {
			//プレイヤーが本当に右端のとき
			if (fieldLeft + FIELD_WIDTH <= movObj.x + movObj.w) return;
			//まだ 20% が残っているとき
			else movObj.x += 1;
		}//ウィンドウの 80% のところに来たら、プレイヤーではなく背景を動かす
		else if (this.getWidth() * 0.8 < movObj.x + movObj.w) fieldLeft -= 1;
		else movObj.x += 1;
		
		repaint();
	}
	
	//引数：ウィンドウのY座標　返り値：配列のY座標
	private int getIndexOfFieldY(int windowY) {
		return field.length - 1 - (this.getHeight() - windowY) / CELL_SIZE;
	}//引数：ウィンドウのX座標　返り値：配列のX座標
	private int getIndexOfFieldX(int windowX) {
		return (-fieldLeft + windowX) / CELL_SIZE;
	}

	//上が天井
	private boolean upIsCeiling() {
		return (field[getIndexOfFieldY(movObj.y - 1)][getIndexOfFieldX(movObj.x)] != 0) ||
			   (field[getIndexOfFieldY(movObj.y - 1)][getIndexOfFieldX(movObj.x + movObj.w)] != 0);
	}//浮いている
	private boolean isFloating() {
		return (field[getIndexOfFieldY(movObj.y + movObj.h + 1)][getIndexOfFieldX(movObj.x)] == 0) &&
			   (field[getIndexOfFieldY(movObj.y + movObj.h + 1)][getIndexOfFieldX(movObj.x + movObj.w)] == 0);
	}//左が壁
	private boolean leftIsWall() {
		return (field[getIndexOfFieldY(movObj.y           )][getIndexOfFieldX(movObj.x - 1)] != 0) ||
			   (field[getIndexOfFieldY(movObj.y + movObj.h)][getIndexOfFieldX(movObj.x - 1)] != 0);
	}//右が壁
	private boolean rightIsWall() {
		return (field[getIndexOfFieldY(movObj.y           )][getIndexOfFieldX(movObj.x + movObj.w + 1)] != 0) ||
			   (field[getIndexOfFieldY(movObj.y + movObj.h)][getIndexOfFieldX(movObj.x + movObj.w + 1)] != 0);
	}
}