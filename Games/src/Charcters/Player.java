package characters;
import application.GameCanvas;

public class Player extends Character {
	String status = "normal";
	GameCanvas gc;

	public Player(GameCanvas gamecanvas) {
		super(100, 200, 50, 50);
		gc = gamecanvas;
	}


	public boolean move_left() {//動けるかどうかを返す

		if(x <= gc.getWidth()*0.2 && gc.fieldLeft <= 0) return false;
		if(gc.blockIs(x, y) != 0 || gc.blockIs(x, y+height) != 0) return true;

		x--;
		return true;

	}


	public boolean move_right() {

		if(gc.getWidth()*0.8 <= x && gc.fieldLeft <= 0) return false;
		if(gc.blockIs(x+width, y) != 0 || gc.blockIs(x+width, y+height) != 0 ) return true;

		x++;
		return true;
	}


	public boolean move_up() {

		if(y <= 0) return false;
		if(gc.blockIs(x, y) != 0 || gc.blockIs(x+width, y) != 0) return true;

		y--;
		return true;
	}


	public boolean move_down() {

		if(gc.getHeight() <= y) return false;
		if(gc.blockIs(x, y) != 0 || gc.blockIs(x+width, y) != 0) return true;

		y++;
		return true;
	}


	public void run() {//重力
		int time, temp, tic;

		while(true) {
			tic = 100;
			time = 0;

			while(gc.blockIs(x,y+height) == 0 && gc.blockIs(x+width,y+height) == 0) {//地面から落ちる
				interval(tic);
				time += tic;
				temp -= 700/time;
				tic = (1 <= temp) ? temp : 1;
				gravity();
			}
		}
	}

	public void gravity() {//重力
		y += 2;
	}

}
