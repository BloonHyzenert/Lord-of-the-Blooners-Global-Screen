import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Team {

	private String name;
	
	private List<Player> playerList;
	
	private Team strong;
	
	public Team(String tname) {
		name=tname;
		playerList = new ArrayList<Player>(0);
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

	public void addPlayer(ClientRequest dialog, String pseudo) {
		Player player = new Player(size()+1, dialog,pseudo ,this);
		this.playerList.add(player);
	}

	public Team getStrong() {
		return strong;
	}

	public void setStrong(Team strong) {
		this.strong = strong;
	}
}
