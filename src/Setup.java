import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import sun.applet.Main;

public class Setup {

	private static Semaphore semaPlayerList;
	private static Team krok = new Team("Krok", "yellow");
	private static Team grounch = new Team("Grounch", "red");
	private static Team blurp = new Team("Blurp", "blue");
	private static Team item = new Team("Item", "white");
	private static List<Player> playerList = new ArrayList<Player>();
	public static String strongImage;

	public Setup() {
		semaPlayerList = new Semaphore(1, true);
		krok.setStrong(blurp);
		grounch.setStrong(krok);
		blurp.setStrong(grounch);
		strongImage = Main.class.getResource("/ressources/strongCircle.png").toString();
	}

	public static void init() {
		Display.premier.setVisible(false);
		Display.deuxieme.setVisible(false);
		Display.troisieme.setVisible(false);
		new Thread(new SortList()).start();

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

	public static void changePlayer(Player player, Team t) {
		t.addPlayer(player.getTeam().removePlayer(player));
		Display.colorPion(player);
		Display.colorName(player);
		Display.colorScore(player);
	}

	public static void setStartPositions() {
		for (int i = 0; i < playerList.size(); i++) {
			playerList.get(i).setStartPosition();
		}
	}

	public static void balance() {
		Team min;
		Team max;
		Team moy;

		do {
			if (blurp.size() > krok.size()) {
				max = blurp;
				min = krok;
				moy = grounch;
			} else {
				min = blurp;
				max = krok;
				moy = grounch;
			}
			if (grounch.size() > max.size()) {
				moy = max;
				max = grounch;
			}
			if (grounch.size() < min.size()) {
				moy = min;
				min = grounch;
			}

			if (max.size() - min.size() > 1) {
				changePlayer(max.get((int) (Math.random() * max.size())), min);
				System.out.println("ok");
			}

		} while (max.size() - min.size() > 1 || max.size() - moy.size() > 1);

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

	public static Semaphore getSemaphore() {
		return semaPlayerList;
	}

	public static void setSemaphore(Semaphore semaphore) {
		Setup.semaPlayerList = semaphore;
	}

}
