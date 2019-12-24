package characters;
import application.GameCanvas;

public class Player extends Character {
	String status = "normal";
	GameCanvas gc;

	public Player(GameCanvas gamecanvas) {
		super(100, 200, 50, 50);
		gc = gamecanvas;
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


	public void run() {//重力

		int l_blockIs = gc.blockIs(this.getX(), this.getY()+this.getHeight());
		int r_blockIs = gc.blockIs(this.getX()+this.getWidth(), this.getY()+this.getHeight());

		if(l_blockIs != 0 || r_blockIs != 0) return;

		int time = 0;
		int tic  = 100;

		while(blockIs == 0) {
			gravity();
			interval(tic);
			//TODO
		}
	}


	public void gravity() {

	}

}
