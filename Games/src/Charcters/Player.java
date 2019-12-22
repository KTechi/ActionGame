package Charcters;

public class Player extends Character {
	String status = "normal";

	public Player() {
		super(100, 200, 50, 50);
	}


	public void move_left(int blockIs) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void move_right(int blockIs) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void move_up(int blockIs) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void move_down(int blockIs) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void run() {
		//blockの設定
		int blockIs = GameCanavas.blockIs(this.getX(), this.getY());
		int time = 0;
		int tic  = 100;

		while(blockIs == 0) {
			gravity();
			interval(tic);
			//TODO
		}
	}


	public void gravity() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
