package characters;
import application.GameCanvas;

public class Player extends Character {
	String status = "normal";
	GameCanvas gc;

	//初期化
	public Player(GameCanvas gamecanvas) {
		super(100, 200, 50, 50);
		gc = gamecanvas;
	}


	public boolean move_left() {

		if(x <= gc.getWidth()*0.2 && gc.fieldLeft <= 0) return false;
		if(gc.blockIs(x, y+1) != 0 || gc.blockIs(x, y+height-1) != 0) return true;
		if(x <= 0) return true;

		x--;
		return true;

	}


	public boolean move_right() {

		if(gc.getWidth()*0.8 <= x && gc.getWidth() <= gc.fieldLeft + gc.fieldWidth) return false;
		if(gc.blockIs(x+width, y+1) != 0 || gc.blockIs(x+width, y+height-1) != 0) return true;
		if(gc.getWidth() <= x+width) return true;

		x++;
		return true;
	}


	public boolean move_up() {

		//TODO
		if(y <= 0) return false;
		if(gc.blockIs(x+1, y) != 0 || gc.blockIs(x+width-1, y) != 0) return true;

		y--;
		return true;
	}


	public boolean move_down() {

		//TODO
		if(gc.getHeight() <= y) return false;
		if(gc.blockIs(x+1, y+height) != 0 || gc.blockIs(x+width-1, y+height) != 0) return true;

		y++;
		return true;
	}


	//重力の適用
	public void run() {
		int time, temp, tic;

		while(true) {
			tic = 100;
			time = 0;

			//地面から落ちる
			while(gc.blockIs(x,y+height) == 0 && gc.blockIs(x+width,y+height) == 0) {
				interval(tic);
				time += tic;
				temp = 700/time;
				tic = (1 <= temp) ? temp : 1;
				gravity();
			}
		}
	}

	//重力の設定
	public void gravity() {
		y += 2;
	}

}

