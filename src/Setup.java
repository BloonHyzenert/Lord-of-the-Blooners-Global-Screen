import java.util.ArrayList;
import java.util.List;

public class Setup {

	private static Team krok = new Team("Krok", "yellow");
	private static Team grounch = new Team("Grounch", "red");
	private static Team blurp = new Team("Blurp", "blue");
	private static Team item = new Team("Item", "white");
	private static List<Player> playerList = new ArrayList<Player>();

	public static void init() {
		krok.setStrong(blurp);
		grounch.setStrong(krok);
		blurp.setStrong(grounch);
		Configuration.maxMapRadius = (Setup.getPlayerList().size()) * 3 * Configuration.microbeRadius + 1;
		Configuration.mapRadius = Configuration.maxMapRadius;
		Configuration.pionRadius = (int) (Configuration.boardRadius * Configuration.microbeRadius
				/ (double) Configuration.maxMapRadius);
		setStartPositions();
		Configuration.start = true;
	}

	public static void addPlayer(Player player) {
		int sizeMin = krok.size();
		Team add = krok;
		if (grounch.size() < sizeMin) {
			sizeMin = grounch.size();
			add = grounch;
		}
		if (blurp.size() < sizeMin) {
			sizeMin = blurp.size();
			add = blurp;
		}
		add.addPlayer(player);
		playerList.add(player);
	}

	public static void removePlayer(Player player) {
		System.out.println("Le joueur n°" + player.getPlayerID() + " : " + player.getPseudo() + " a quitte la partie");
		player.getPion().setVisible(false);
		player.getNameBox().setVisible(false);
		player.getScoreBox().setVisible(false);
		Setup.getPlayerList().remove(player);
		player.getTeam().removePlayer(player);
	}

	private static void setStartPositions() {
		for (int i = 0; i < playerList.size(); i++) {
			playerList.get(i).setStartPosition();
		}
	}

	public static Team getKrok() {
		return krok;
	}

	public static Team getGrounch() {
		return grounch;
	}

	public static Team getBlurp() {
		return blurp;
	}

	public static Team getItem() {
		return item;
	}

	public static List<Player> getPlayerList() {
		return playerList;
	}

}
