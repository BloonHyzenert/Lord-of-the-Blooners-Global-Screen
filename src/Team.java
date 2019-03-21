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

	public void addPlayer(Player player) {
		player.setTeam(this);
		this.playerList.add(player);
	}
	
	public void removePlayer(Player player) {
		System.out.println("Le joueur n°"+player.getPlayerID()+" : "+player.getPseudo()+" a quitte la partie");
		this.playerList.remove(player);
	}
	

	public Team getStrong() {
		return strong;
	}

	public void setStrong(Team strong) {
		this.strong = strong;
	}
}
