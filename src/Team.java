import java.util.ArrayList;
import java.util.List;

public class Team {

	private String name;
	private String color;
	private List<Player> playerList;
	private Team strong;

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

}
