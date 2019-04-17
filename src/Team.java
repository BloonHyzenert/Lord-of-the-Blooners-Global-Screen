import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Text;

public class Team {

	private String name;
	private String color;
	private List<Player> playerList;
	private Team strong;
	private Text scoreBox;
	private Text nameBox;

	public Team(String tname, String tcolor) {
		name = tname;
		color = tcolor;
		playerList = new ArrayList<Player>(0);
	}

	public void addPlayer(Player player) {
		player.setTeam(this);
		this.playerList.add(player);
	}

	public Player removePlayer(Player player) {
		this.playerList.remove(player);
		return player;
	}

	public int size() {
		return playerList.size();
	}
	
	public int getScore() {
		int score = 0;
		for (int i = 0; i < playerList.size(); i++) {
			score+=playerList.get(i).getScore();
		}
		return score;
	}

	public String getName() {
		return name;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public Team getStrong() {
		return strong;
	}

	public void setStrong(Team strong) {
		this.strong = strong;
	}

	public String getColor() {
		return color;
	}

	public Text getScoreBox() {
		return scoreBox;
	}

	public void setScoreBox(Text scoreBox) {
		this.scoreBox = scoreBox;
	}

	public Text getNameBox() {
		return nameBox;
	}

	public void setNameBox(Text nameBox) {
		this.nameBox = nameBox;
	}

}
