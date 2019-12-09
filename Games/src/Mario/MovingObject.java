package Mario;

public class MovingObject {
	public int x, y;
	public int w, h;
	public String name = "Mario/Enemies/Bobomb_LQ.png";
	
	public MovingObject(int xx, int yy, int ww, int hh, String nn) {
		x = xx;
		y = yy;
		w = ww;
		h = hh;
		name = nn;
	}
}