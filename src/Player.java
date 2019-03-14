import java.net.Socket;

public class Player {

	private int playerID;
	
	private String team;
	
	private Socket socket;
	
	private Position position;
	
	public Player(int tPlayerID,Socket tSocket,String tName) {
		setSocket(tSocket);
		playerID=tPlayerID;
		setTeam(tName);
		setStartPosition();
	}
	
	public void move(int dx, int dy) {
		setPosition(new Position(position.getX()+dx,position.getY()+dy));
	}

	public void setStartPosition() {
		switch(team) {
		case "Krok":
			setPosition(new Position(0,-Configuration.MAXMAPSIZE/2));
			break;
		case "Blurp":
			setPosition(new Position((int)(-Configuration.MAXMAPSIZE*Math.sqrt(2) /2),(int)(Configuration.MAXMAPSIZE*Math.sqrt(3)/2)));
			break;
		case "Grounch":
			setPosition(new Position((int)(Configuration.MAXMAPSIZE*Math.sqrt(2)/2),(int)(Configuration.MAXMAPSIZE*Math.sqrt(3)/2)));
			break;
		case "Item":
			setPosition(new Position(-Configuration.MAXMAPSIZE/2,-Configuration.MAXMAPSIZE/2));
			break;
		default:
			break;
		
		}
	}
	
	public int getPlayerID() {
		return playerID;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
}
