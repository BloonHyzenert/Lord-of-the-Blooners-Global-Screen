
public class Position {

	private double x;
	private double y;

	public Position() {
		setX(0);
		setY(0);
	}

	public Position(double tx, double ty) {
		setX(tx);
		setY(ty);
	}

	public void setPosition(double x, double y) {
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		if (distance > Configuration.mapRadius - Configuration.microbeRadius) {
			setX(x * (Configuration.mapRadius - Configuration.microbeRadius) / distance);
			setY(y * (Configuration.mapRadius - Configuration.microbeRadius) / distance);
		} else {
			setX(x);
			setY(y);
		}
	}

	public double distance(Position p) {
		return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public String toString() {
		return x + " , " + y;
	}

	public void incX() {
		x += 10;

	}

	public void decX() {
		x -= 10;

	}
}
