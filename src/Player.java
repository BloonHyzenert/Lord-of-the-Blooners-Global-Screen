import java.net.Socket;

public class Player {

	private int playerID;
	
	private static int nbPlayer=0;
	
	private String pseudo;
	
	private Team team;
	
	private ClientRequest dialog;
	
	private Position position;
	
	public Player(ClientRequest dialog,String tPseudo) {
		setDialog(dialog);
		position = new Position();
		setPseudo(tPseudo);
		playerID=++nbPlayer;
	}

	public void move(int dx, int dy) {
		setPosition(new Position(position.getX()+dx,position.getY()+dy));
	}

	public void setStartPosition() {
		switch(team.getName()) {
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

	public ClientRequest getDialog() {
		return dialog;
	}

	public void setDialog(ClientRequest socket) {
		this.dialog = socket;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void setPseudo(String tPseudo) {
		pseudo=tPseudo;
		
	}
	
	public String getPseudo() {
		return pseudo;
	}
}
