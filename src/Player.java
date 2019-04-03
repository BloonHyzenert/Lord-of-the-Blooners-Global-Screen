import java.net.Socket;

import javafx.scene.shape.Circle;

public class Player {

	private int playerID;
	
	private static int nbPlayer=0;
	
	private String pseudo;
	
	private Team team;
	
	private Circle pion;
	
	private ClientRequest dialog;
	
	private Position position;
	
	public Player(ClientRequest dialog,String tPseudo) {
		setDialog(dialog);
		position = new Position(Configuration.WIDTH/2,Configuration.HEIGHT/2);
		setPseudo(tPseudo);
		playerID=++nbPlayer;
    	Setup.addPlayer(this);
    	Display.microbe(this);
		System.out.println("Le joueur n°"+getPlayerID()+" : "+getPseudo()+" a rejoint la partie");
		
	}

	public Circle getPion() {
		return pion;
	}

	public void setPion(Circle pion) {
		this.pion = pion;
	}

	public void move(int dx, int dy) {
		position.move(position.getX()+dx,position.getY()+dy);
		if(pion!=null) {
		pion.setCenterX(position.getX());
		pion.setCenterY(position.getY());
		
		}
	}

	public void setStartPosition() {
		System.out.println("setStartPosition");
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
		if(pion!=null) {
		pion.setTranslateX(position.getX());
		pion.setTranslateY(position.getY());
		}
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
