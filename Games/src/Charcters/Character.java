package characters;

public abstract class Character {
	public int x, y;
	public int width, height;

	//コンストラクタ
	public Character(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	//canvasのblockIsメソッドで、壁があるか判断する
	public abstract boolean move_left();
	public abstract boolean move_right();
	public abstract boolean move_up();
	public abstract boolean move_down();

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
}
