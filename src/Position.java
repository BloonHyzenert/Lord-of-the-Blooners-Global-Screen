
public class Position {

	private int x;
	private int y;

	public Position() {
		setX(0);
		setY(0);
	}

	public Position(int tx, int ty) {
		setX(tx);
		setY(ty);
	}

	public void setPosition(int x, int y) {
		double distance=Math.sqrt(Math.pow(x,2)+Math.pow(y, 2));
		if(distance>Configuration.mapRadius-Configuration.microbeRadius) {
			setX((int) (x*(Configuration.mapRadius-Configuration.microbeRadius)/distance));
			setY((int) (y*(Configuration.mapRadius-Configuration.microbeRadius)/distance));
		}
		else {
			setX(x);
			setY(y);
		}
	}
	
	public double distance(Position p) {
		return Math.sqrt(Math.pow(this.x - p.x,2)+Math.pow(this.y - p.y, 2));
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
		return x + "," + y;
	}
}
