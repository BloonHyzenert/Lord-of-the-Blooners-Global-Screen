
public class Position {

	private int x;
	private int y;
	
	public Position() {
		setX((int)Configuration.WIDTH/2);
		setY((int)Configuration.HEIGHT/2);
	}
	
	public Position(int tx, int ty) {
		setX(tx);
		setY(ty);
	}
	
	public void move(int dx, int dy) {
		setX(dx);
		setY(dy);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public String toString() {
		return x+","+y;
	}
}
