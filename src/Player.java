import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Player implements Comparable<Player> {

	private int playerID;
	private static int nbPlayer = 0;
	private String pseudo;
	private Team team;
	private Circle pion;
	private Text nameBox;
	private Text scoreBox;
	private int score;
	private ClientRequest dialog;
	private Position position;
	private Position dernierePosition;
	private Position deltaPosition;

	public Player(ClientRequest dialog, String tPseudo) {
		setDialog(dialog);
		position = new Position();
		dernierePosition = new Position();
		deltaPosition = new Position();
		setPseudo(tPseudo);
		setScore(0);
		playerID = ++nbPlayer;
		Setup.addPlayer(this);
		Display.addPion(this);
		Display.addText(this);
		System.out.println("Le joueur n°" + getPlayerID() + " : " + getPseudo() + " a rejoint la partie");

	}

	public void move(double dx, double dy) {
		deltaPosition.setPosition(dx, dy);
	}

	public void setStartPosition() {
		switch (team.getName()) {
		case "Krok":
			setPosition(Math.cos(Math.PI/6.0)*Configuration.mapRadius/2.0,-Math.sin(Math.PI/6.0)*Configuration.mapRadius/2.0);
			setDernierePosition(Math.cos(Math.PI/6.0)*Configuration.mapRadius/2.0,-Math.sin(Math.PI/6.0)*Configuration.mapRadius/2.0);
			break;
		case "Blurp":
			setPosition(Math.cos(5*Math.PI/6.0)*Configuration.mapRadius/2.0,-Math.sin(5*Math.PI/6.0)*Configuration.mapRadius/2.0);
			setDernierePosition(Math.cos(5*Math.PI/6.0)*Configuration.mapRadius/2.0,-Math.sin(5*Math.PI/6.0)*Configuration.mapRadius/2.0);
			break;
		case "Grounch":
			setPosition(0,Configuration.mapRadius/2.0);
			setDernierePosition(0,Configuration.mapRadius/2.0);
			break;
		case "Item":
			setPosition(new Position(-Configuration.maxMapRadius / 2, -Configuration.maxMapRadius / 2));
			setDernierePosition(new Position(-Configuration.maxMapRadius / 2, -Configuration.maxMapRadius / 2));
			break;
		default:
			break;

		}
	}

	@Override
	public int compareTo(Player o) {
		if (score >= o.getScore())
			return 1;
		return -1;
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

	public void setPosition(int x, int y) {
		this.position.setPosition((double)x, (double) y);
	}
	
	public void setPosition(double d, double e) {
		this.position.setPosition(d, e);
	}

	public Position getDernierePosition() {
		System.out.println("ID : "+ this.playerID +"  Derniere position :" + this.dernierePosition);
		return this.dernierePosition;
	}

	public void setDernierePosition(Position dernierePosition) {
		System.out.println("ID : "+ this.playerID +"  New Derniere position :" + this.dernierePosition);
		this.dernierePosition = dernierePosition;
		System.out.println("ID : "+ this.playerID +"  New Derniere position2 :" + this.dernierePosition);
	}

	public void setDernierePosition(int x, int y) {
		this.dernierePosition.setPosition((double)x, (double) y);
	}
	
	public void setDernierePosition(double d, double e) {
		this.dernierePosition.setPosition(d, e);
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setPseudo(String tPseudo) {
		pseudo = tPseudo;

	}

	public String getPseudo() {
		return pseudo;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Text getNameBox() {
		return nameBox;
	}

	public void setNameBox(Text box) {
		this.nameBox = box;
	}

	public void setPion(Circle pion) {
		this.pion = pion;
	}

	public Circle getPion() {
		return pion;
	}

	public Text getScoreBox() {
		return scoreBox;
	}

	public void setScoreBox(Text scoreBox) {
		this.scoreBox = scoreBox;
	}

	public Position getDeltaPosition() {
		return deltaPosition;
	}

	public void setDeltaPosition(Position deltaPosition) {
		this.deltaPosition = deltaPosition;
	}

	public void upScore() {
		score+=1;
		
	}

}
