package Charcters;

public abstract class Character {
	private int x, y;
	private int width, height;

	//canvasのblockIsメソッドで、壁があるか判断する
	public abstract void move_left(int blockIs);
	public abstract void move_right(int blockIs);
	public abstract void move_up(int blockIs);
	public abstract void move_down(int blockIs);

	//Threadの実行
	public abstract void run();
	public void interval(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	};

	//重力の設定
	public abstract void gravity();


	//getter, setterの設定
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}


	//コンストラクタ
	public Character(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

}
