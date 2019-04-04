import java.util.ArrayList;
import java.util.List;

public class Setup {

	private static Team krok = new Team("Krok", "yellow");
	private static Team grounch = new Team("Grounch", "red");
	private static Team blurp = new Team("Blurp", "blue");
	private static Team item = new Team("Item", "white");
	private static List<Player> scoreTable = new ArrayList<Player>();

	public static void init() {
		krok.setStrong(blurp);
		grounch.setStrong(krok);
		blurp.setStrong(grounch);
		Configuration.maxMapSize = (krok.size() + grounch.size() + blurp.size()) * Configuration.microbeSize;
		Configuration.mapSize = Configuration.maxMapSize;
		Configuration.start=true;
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
		scoreTable.add(player);
	}

	public static void removePlayer(Player player) {
		System.out.println("Le joueur n°" + player.getPlayerID() + " : " + player.getPseudo() + " a quitte la partie");
		player.getPion().setVisible(false);
		player.getBox().setVisible(false);
		Setup.getScoreTable().remove(player);
		player.getTeam().removePlayer(player);
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

	public static List<Player> getScoreTable() {
		return scoreTable;
	}

}
