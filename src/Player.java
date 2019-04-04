import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Player implements Comparable<Player> {

	private int playerID;
	private static int nbPlayer = 0;
	private String pseudo;
	private Team team;
	private Circle pion;
	private Text box;
	private int score;
	private ClientRequest dialog;
	private Position position;

	public Player(ClientRequest dialog, String tPseudo) {
		setDialog(dialog);
		position = new Position(Configuration.width / 2, Configuration.height / 2);
		setPseudo(tPseudo);
		setScore(0);
		playerID = ++nbPlayer;
		Setup.addPlayer(this);
		Display.microbe(this);
		Display.score(this);
		System.out.println("Le joueur n°" + getPlayerID() + " : " + getPseudo() + " a rejoint la partie");

	}

	public void move(int dx, int dy) {
		position.setPosition(position.getX() + dx, position.getY() + dy);
		if (pion != null) {
			pion.setCenterX(position.getX());
			pion.setCenterY(position.getY());

		}
	}

	public void setStartPosition() {
		switch (team.getName()) {
		case "Krok":
			setPosition(new Position(0, -Configuration.maxMapSize / 2));
			break;
		case "Blurp":
			setPosition(new Position((int) (-Configuration.maxMapSize * Math.sqrt(2) / 2),
					(int) (Configuration.maxMapSize * Math.sqrt(3) / 2)));
			break;
		case "Grounch":
			setPosition(new Position((int) (Configuration.maxMapSize * Math.sqrt(2) / 2),
					(int) (Configuration.maxMapSize * Math.sqrt(3) / 2)));
			break;
		case "Item":
			setPosition(new Position(-Configuration.maxMapSize / 2, -Configuration.maxMapSize / 2));
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
		if (pion != null) {
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

	public Text getBox() {
		return box;
	}

	public void setBox(Text box) {
		this.box = box;
	}

	public void setPion(Circle pion) {
		this.pion = pion;
	}

	public Circle getPion() {
		return pion;
	}

}
