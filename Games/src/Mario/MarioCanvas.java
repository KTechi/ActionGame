package Mario;

import java.awt.*;

public class MarioCanvas extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private final int CELL_SIZE = 50;
	private final int FIELD_WIDTH = 62 * CELL_SIZE;
	private int fieldLeft = 0;//画面がどれだけ、左にスクロースされたか（負整数）
	private boolean jumping = false;//ジャンプ中
	public boolean movingLeft = false;//左に移動中
	public boolean movingRight = false;//右に移動中
	
	private boolean tmp = true;
	
	public MovingObject movObj = new MovingObject(200, 100, CELL_SIZE-1, (int)(1.8*CELL_SIZE-1), "Mario/MainChara/MarioStanding.png");
	private StaticObject[] staObjs;//静的ブロック
	//private MovingObject[] movObjs;//動的ブロック
	
	private int[][] field = Field.field;
	/*//////////////// ブロックの説明 ////////////////
	 * -1: サイズが大きなブロックは、左上以外これで埋める
	 *  0: 空気
	 *  1: レンガブロック
	 *  2: ハテナブロック
	 *  3: ハテナを叩いた後のブロック
	 *  4: 土台ブロック
	 *  5: 2x2 土管
	 *  6: 2x4 土管
	 *  7: 以降制作途中
	 * */
	
	public MarioCanvas() {
		System.out.println(field[0].length);
		String[] objectsName = {//静的なブロックの名前一覧
				"Mario/Blocks/BrickBlock_LQ.png", "Mario/Blocks/QuestionBlock_LQ.png",
				"Mario/Blocks/EmptyBlock_LQ.png", "Mario/Blocks/StructureBlock_LQ.png",
				"Mario/Structures/ShortPipe_LQ.png", "Mario/Structures/LongPipe_LQ.png",
		};
		int[][] objectsSize = {//静的なブロックの大きさ一覧 [幅、高さ]
				{CELL_SIZE, CELL_SIZE}, {CELL_SIZE, CELL_SIZE}, {CELL_SIZE, CELL_SIZE},
				{CELL_SIZE, CELL_SIZE}, {2*CELL_SIZE, 2*CELL_SIZE}, {2*CELL_SIZE, 4*CELL_SIZE}
		};
		
		if (objectsName.length != objectsSize.length) {//ブロック名の数とブロック数が違うとエラー
			System.out.println("Error! Number ObjectsName and ObjectsSize is not equal");
			System.exit(0);
		}
		
		//静的なブロックの名前とサイズを登録
		staObjs = new StaticObject[objectsName.length];
		for (int i = 0; i < objectsName.length; i++)
			staObjs[i] = new StaticObject(i + 1, objectsSize[i][0], objectsSize[i][1], objectsName[i]);

		//重力スレッド開始
		Thread th = new Thread(this);
		th.start();
	}
	
	@Override
	public void paint(Graphics g) {
		int h = this.getHeight();
		
		g.setColor(new Color(108, 177, 231));//背景色　水色
		g.fillRect(0, 0, this.getWidth(), h);
		
		int blockID, index;
		
		for (int i = 0; i < field.length; i++)
		for (int j = 0; j < field[0].length; j++) {//全フィールドの描画
			blockID = field[i][j];
			if (blockID == 0 || blockID == -1) continue;
			
			//フィールドの番号と一致する、静的ブロックを探す
			for (index = 0; index < staObjs.length; index++)
				if (staObjs[index].ID == blockID) break;
			
			//index は静的ブロックの配列 satObjs[] の添字である
			g.drawImage(Toolkit.getDefaultToolkit().getImage(staObjs[index].NAME),
					fieldLeft + j * CELL_SIZE,// x
					h - (field.length - i) * CELL_SIZE,// y
					staObjs[index].W,// width
					staObjs[index].H,// height
					this
				);
		}
		
		//プレイヤーの描画
		g.drawImage(Toolkit.getDefaultToolkit().getImage(movObj.name),
				movObj.x, movObj.y, movObj.w, movObj.h, this);
	}
	
	@Override
	public void run() {//重力用のスレッド
		while (this.getHeight() < 100) myStop(100);// 画面が用意できるまで待つ。
		
		int tic, time, tmp;
		while (true) {
			tic = 100;//重力を作用させるインターバル
			time = 0;//経過時刻
			myStop(10);
			
			while (isFloating() && !jumping) {//空中に浮いていて、ジャンプ中じゃない
				movObj.name = "Mario/MainChara/MarioJumping.png";//////////////////////////////////////////////////////////////////////////////////////////////
				
				movObj.y += 2;
				repaint();
				myStop(tic);
				time += tic;
				tmp = 700 / time;//インターバルは時刻に反比例（時間が経てばたつほど、重力のインターバルは短くなる）
				tic = (3 <= tmp) ? tmp : 3;
			}
			
			if (movingLeft) movObj.name = "Mario/MainChara/MarioLeft.png";//////////////////////////////////////////////////////////////////////////////////////////////
			else if (movingRight) movObj.name = "Mario/MainChara/MarioRight.png";//////////////////////////////////////////////////////////////////////////////////////////////
			else if (isFloating()) movObj.name = "Mario/MainChara/MarioJumping.png";//////////////////////////////////////////////////////////////////////////////////////////////
			else movObj.name = "Mario/MainChara/MarioStanding.png";//////////////////////////////////////////////////////////////////////////////////////////////
			repaint();
		}
	}
	
	private void myStop(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void jump() {
		if (upIsCeiling()) {
			//////////////////////////////////////////////////////////////////////////////////ブロックを壊す
			int x = getIndexOfFieldX(movObj.x + movObj.w/2);
			int y = getIndexOfFieldY(movObj.y-2);
			int blockType = field[y][x];
			
			if (blockType == 1) {
				field[y][x] = 0;
			} else if (blockType == 2) {
				field[y][x] = 3;
			}
			//////////////////////////////////////////////////////////////////////////////ブロックを壊す
			return;
		}
		if (isFloating()) return;//足場がない
		
		jumping = true;//空中に浮いている間「重力」を一旦無効化する
		
		for (int time = 450, tic = 2; 5 <= time;) {
			if (upIsCeiling()) {//上が天井なら強制終了
				//////////////////////////////////////////////////////////////////////////////////ブロックを壊す
				int x = getIndexOfFieldX(movObj.x + movObj.w/2);
				int y = getIndexOfFieldY(movObj.y-2);
				int blockType = field[y][x];
				
				if (blockType == 1) {
					field[y][x] = 0;
				} else if (blockType == 2) {
					field[y][x] = 3;
				}
				//////////////////////////////////////////////////////////////////////////////////ブロックを壊す
				jumping = false;
				return;
			}
			movObj.y -= 1;
			repaint();
			myStop(tic);
			time -= tic;
			int tmp = 700 / time;//インターバルは時刻に反比例（時間が経てばたつほど、上昇のインターバルは長くなる）
			tic = (1 <= tmp) ? tmp : 1;
		}
		jumping = false;
	}

	public void moveDown() {
		if (!isFloating()) {
			//
			//しゃがみ（画像を差し替える）
			//
			if (tmp) {
				movObj.y -= (int)(0.8*CELL_SIZE);
				movObj.h = (int)(1.8*CELL_SIZE-1);
				tmp = !tmp;
			} else {
				movObj.y += (int)(0.8*CELL_SIZE);
				movObj.h = CELL_SIZE-1;
				tmp = !tmp;
			}
			repaint();
			myStop(300);
			return;
			//
			//しゃがみ（画像を差し替える）
			//
		}
		//作成途中
		movObj.y += 1;
		repaint();
	}
	
	public void moveLeft() {
		if (leftIsWall()) return;//左が壁なら
		
		if (0 <= fieldLeft) {//左端まで来た時の処理
			if (movObj.x <= 0) return;//プレイヤーが本当に左端のとき
			else movObj.x -= 1;//まだ 20% が残っているとき
		}//ウィンドウの 20% のところに来たら、プレイヤーではなく背景を動かす
		else if (movObj.x < this.getWidth() * 0.2) fieldLeft += 1;
		else movObj.x -= 1;//プレイヤーを動かす
		repaint();
	}
	
	public void moveRight() {
		if (rightIsWall()) return;//右が壁なら
		
		if (fieldLeft + FIELD_WIDTH <= this.getWidth()) {//右端まで来た時の処理
			if (fieldLeft + FIELD_WIDTH <= movObj.x + movObj.w) return;//プレイヤーが本当に右端のとき
			else movObj.x += 1;//まだ 20% が残っているとき
		}//ウィンドウの 80% のところに来たら、プレイヤーではなく背景を動かす
		else if (this.getWidth() * 0.8 < movObj.x + movObj.w) fieldLeft -= 1;
		else movObj.x += 1;//プレイヤーを動かす
		repaint();
	}
	
	//引数：ウィンドウのY座標　返り値：配列のY座標
	private int getIndexOfFieldY(int windowY) {
		return field.length - 1 - (this.getHeight() - windowY) / CELL_SIZE;
	}
	//引数：ウィンドウのX座標　返り値：配列のX座標
	private int getIndexOfFieldX(int windowX) {
		return (-fieldLeft + windowX) / CELL_SIZE;
	}
	
	private boolean upIsCeiling() {//上が天井
		return (field[getIndexOfFieldY(movObj.y - 1)][getIndexOfFieldX(movObj.x)] != 0) ||
			   (field[getIndexOfFieldY(movObj.y - 1)][getIndexOfFieldX(movObj.x + movObj.w)] != 0);
	}
	private boolean isFloating() {//浮いている
		return (field[getIndexOfFieldY(movObj.y + movObj.h + 2)][getIndexOfFieldX(movObj.x)] == 0) &&
			   (field[getIndexOfFieldY(movObj.y + movObj.h + 2)][getIndexOfFieldX(movObj.x + movObj.w)] == 0);
	}
	private boolean leftIsWall() {//左が壁
		return (field[getIndexOfFieldY(movObj.y           )][getIndexOfFieldX(movObj.x - 1)] != 0) ||
			   (field[getIndexOfFieldY(movObj.y + movObj.h)][getIndexOfFieldX(movObj.x - 1)] != 0);
	}
	private boolean rightIsWall() {//右が壁
		return (field[getIndexOfFieldY(movObj.y           )][getIndexOfFieldX(movObj.x + movObj.w + 1)] != 0) ||
			   (field[getIndexOfFieldY(movObj.y + movObj.h)][getIndexOfFieldX(movObj.x + movObj.w + 1)] != 0);
	}
}