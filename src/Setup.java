import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

public class Setup {

	private static Semaphore semaPlayerList;
	private static Team krok = new Team("Krok", "yellow");
	private static Team grounch = new Team("Grounch", "red");
	private static Team blurp = new Team("Blurp", "blue");
	private static Team item = new Team("Item", "white");
	private static List<Player> playerList = new ArrayList<Player>();
	public static AudioClip BlurpSong;
	public static AudioClip GrounchSong;
	public static AudioClip KrokSong;
	
	public Setup() {
		semaPlayerList=new Semaphore(1,true);
		krok.setStrong(blurp);
		grounch.setStrong(krok);
		blurp.setStrong(grounch);
        String bip = "src/music/BlurpSong.mp3";
        Media hit = new Media(Paths.get(bip).toUri().toString());
        BlurpSong = new AudioClip(hit.getSource());
        BlurpSong.setCycleCount(AudioClip.INDEFINITE); 
        String bip1 = "src/music/GrounchSong.mp3";
        Media hit1 = new Media(Paths.get(bip1).toUri().toString());
        GrounchSong = new AudioClip(hit1.getSource());
        GrounchSong.setCycleCount(AudioClip.INDEFINITE); 
        String bip11 = "src/music/KrokSong.mp3";
        Media hit11 = new Media(Paths.get(bip11).toUri().toString());
        KrokSong = new AudioClip(hit11.getSource());
        KrokSong.setCycleCount(AudioClip.INDEFINITE); 
	}

	public static void init() {
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

	public static Semaphore getSemaphore() {
		return semaPlayerList;
	}

	public static void setSemaphore(Semaphore semaphore) {
		Setup.semaPlayerList = semaphore;
	}

}
