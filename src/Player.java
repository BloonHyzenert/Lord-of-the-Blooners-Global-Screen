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

	public Player(ClientRequest dialog, String tPseudo) {
		setDialog(dialog);
		position = new Position();
		setPseudo(tPseudo);
		setScore(0);
		playerID = ++nbPlayer;
		Setup.addPlayer(this);
		Display.addPion(this);
		Display.addText(this);
		System.out.println("Le joueur n°" + getPlayerID() + " : " + getPseudo() + " a rejoint la partie");

	}

	public void move(int dx, int dy) {
		position.setPosition(position.getX() + dx, position.getY() + dy);
	}

	public void setStartPosition() {
		switch (team.getName()) {
		case "Krok":
			setPosition(new Position(0, -Configuration.maxMapRadius / 2));
			break;
		case "Blurp":
			setPosition(new Position((int) (-Configuration.maxMapRadius * Math.sqrt(2) / 2),
					(int) (Configuration.maxMapRadius * Math.sqrt(3) / 2)));
			break;
		case "Grounch":
			setPosition(new Position((int) (Configuration.maxMapRadius * Math.sqrt(2) / 2),
					(int) (Configuration.maxMapRadius * Math.sqrt(3) / 2)));
			break;
		case "Item":
			setPosition(new Position(-Configuration.maxMapRadius / 2, -Configuration.maxMapRadius / 2));
			break;
		default:
			break;

		}
	}

	@Override
	public int compareTo(Player o) {
		if (score > o.getScore())
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

}
