package Mario;

public class MovingObject {
	public int x, y;
	public int w, h;
	public boolean onGround = false;
	
	public MovingObject(int xx, int yy, int ww, int hh) {
		x = xx;
		y = yy;
		w = ww;
		h = hh;
	}
}