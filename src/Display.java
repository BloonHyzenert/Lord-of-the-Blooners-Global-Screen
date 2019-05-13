import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sun.javafx.tk.FontLoader;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.applet.Main;
import javafx.stage.WindowEvent;

public class Display extends Application {

	private static Stage primaryStage;
	private static Group root;
	public static Text timerLabel;
	public static Text premier;
	public static Text deuxieme;
	public static Text troisieme;
	public static Text teamTimer;
	public static AudioClip BlurpSong;
	public static AudioClip GrounchSong;
	public static AudioClip KrokSong;
	public static AudioClip bip;
	public static AudioClip bipend;

	@Override
	public void start(Stage stage) {

		try {
			String bip0 = Main.class.getResource("/ressources/BlurpSong.wav").toURI().toString();
			Media hit = new Media(bip0);
			BlurpSong = new AudioClip(hit.getSource());
			BlurpSong.setCycleCount(AudioClip.INDEFINITE);
			String bip1 = Main.class.getResource("/ressources/GrounchSong.wav").toURI().toString();
			Media hit1 = new Media(bip1);
			GrounchSong = new AudioClip(hit1.getSource());
			GrounchSong.setCycleCount(AudioClip.INDEFINITE);
			String bip11 = Main.class.getResource("/ressources/KrokSong.wav").toURI().toString();
			Media hit11 = new Media(bip11);
			KrokSong = new AudioClip(hit11.getSource());
			KrokSong.setCycleCount(AudioClip.INDEFINITE);
			String bip2 = Main.class.getResource("/ressources/bip.wav").toURI().toString();
			Media hit2 = new Media(bip2);
			bip = new AudioClip(hit2.getSource());
			String bip22 = Main.class.getResource("/ressources/bipend.wav").toURI().toString();
			Media hit22 = new Media(bip22);
			bipend = new AudioClip(hit22.getSource());
		} catch (Exception e) {
			e.printStackTrace();
		}

		primaryStage = stage;
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				primaryStage.close();
				System.exit(0);

			}
		});

		primaryStage.setTitle("Lord of the Blooners");
		root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);

		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				switch (t.getCode()) {
				case ESCAPE:
					Configuration.end = true;
					primaryStage.close();
					System.exit(0);
					break;
				case SPACE:
					Setup.init();
					System.out.println("START");
					break;
				case ENTER:
					Configuration.stop = true;
					for (int i = 0; i < Setup.getPlayerList().size(); i++) {
						try {
							Setup.getSemaphore().acquire();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Setup.getPlayerList().get(i).setScore(0);
						Setup.getSemaphore().release();
					}
					break;
				default:
					break;

				}
			}
		});

		getScreenSize();
		board();
		scoreTable();
		scoreTeamTable();
		host();
		strong();
		play();
		timer();
		scorefinal();

		primaryStage.show();

		AnimationTimer boucle = new AnimationTimer() {

			public void handle(long now) {
				for (int i = 0; i < Setup.getPlayerList().size(); i++) {
					Player p = Setup.getPlayerList().get(i);
					try {
						Position tempPos = new Position();
						double deltaX, deltaY;

						if (p.isChargement()) {
							p.incCharge();
							deltaX = Configuration.coefFriction
									* (p.getPosition().getX() - p.getDernierePosition().getX());
							deltaY = Configuration.coefFriction
									* (p.getPosition().getY() - p.getDernierePosition().getY());
						} else {
							deltaX = (3 * p.getCharge() / 4.0 + 1) * p.getDeltaPosition().getX()
									+ Configuration.coefFriction
											* (p.getPosition().getX() - p.getDernierePosition().getX());
							deltaY = (3 * p.getCharge() / 4.0 + 1) * p.getDeltaPosition().getY()
									+ Configuration.coefFriction
											* (p.getPosition().getY() - p.getDernierePosition().getY());
							p.resCharge();
						}

						Setup.getSemaphore().acquire();
						tempPos.setPosition(p.getPosition().getX(), p.getPosition().getY());

						p.setPosition(p.getPosition().getX() + deltaX, p.getPosition().getY() + deltaY);
						p.setDernierePosition(tempPos);
						if (p.getPion() != null) {
							p.getPion().setCenterX(
									p.getPosition().getX() * (Configuration.boardRadius) / Configuration.mapRadius
											+ Configuration.width / 2);
							p.getPion().setCenterY(
									p.getPosition().getY() * (Configuration.boardRadius) / Configuration.mapRadius
											+ Configuration.height / 2);
							p.getPion().setRadius(Configuration.pionRadius);
						}
						p.setDernierePosition(tempPos);
						if (p.getRange() != null) {
							p.getRange().setCenterX(
									p.getPosition().getX() * (Configuration.boardRadius) / Configuration.mapRadius
											+ Configuration.width / 2);
							p.getRange().setCenterY(
									p.getPosition().getY() * (Configuration.boardRadius) / Configuration.mapRadius
											+ Configuration.height / 2);
							p.getRange().setRadius(Configuration.pionRadius * p.getCharge() / 100);
						}
						if (p.getIdBox() != null) {
							int pos=0;
							if(p.getPlayerID()>19)
								pos=Configuration.pionRadius / 2;
							else if(p.getPlayerID()>9)
								pos=Configuration.pionRadius / 4;
							p.getIdBox()
									.setX(p.getPosition().getX() * (Configuration.boardRadius) / Configuration.mapRadius
											+ Configuration.width / 2 - 5*Configuration.pionRadius / 16 - pos);
							p.getIdBox()
									.setY(p.getPosition().getY() * (Configuration.boardRadius) / Configuration.mapRadius
											+ Configuration.height / 2 + 4 / 8f * Configuration.pionRadius);
							p.getIdBox().setFont(Font.loadFont(Setup.font, Configuration.pionRadius*1.5));
						}
						Setup.getSemaphore().release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				collisions();

				try {
					TimeUnit.MILLISECONDS.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		boucle.start();
	}

	private void scorefinal() {
		premier = new Text();
		premier.setFont(Font.loadFont(Setup.font, 70));
		premier.setText("1 : vide");
		premier.setX(Configuration.width / 2 - 450);
		premier.setY(Configuration.height / 2 - 100);
		premier.setVisible(false);
		root.getChildren().add(premier);

		deuxieme = new Text();
		deuxieme.setFont(Font.loadFont(Setup.font, 70));
		deuxieme.setText("2 : vide");
		deuxieme.setX(Configuration.width / 2 - 450);
		deuxieme.setY(Configuration.height / 2);
		deuxieme.setVisible(false);
		root.getChildren().add(deuxieme);

		troisieme = new Text();
		troisieme.setFont(Font.loadFont(Setup.font, 70));
		troisieme.setText("3 : vide");
		troisieme.setX(Configuration.width / 2 - 450);
		troisieme.setY(Configuration.height / 2 + 100);
		troisieme.setVisible(false);
		root.getChildren().add(troisieme);

	}

	private void timer() {
		timerLabel = new Text();
		timerLabel.setFont(Font.loadFont(Setup.font, 700));
		timerLabel.setText("5");
		timerLabel.setX(Configuration.width / 2 - Configuration.width / 10f);
		timerLabel.setY(Configuration.height / 2 + Configuration.width / 8f);
		timerLabel.setVisible(false);
		root.getChildren().add(timerLabel);

		teamTimer = new Text();
		teamTimer.setFont(Font.loadFont(Setup.font, 100));
		teamTimer.setText("");
		teamTimer.setX(Configuration.width - Configuration.tableWidth/2-50);
		teamTimer.setY(Configuration.height/2);
		root.getChildren().add(teamTimer);

	}

	private void collisions() {
		@SuppressWarnings("unchecked")
		List<Player> tempList = ((List<Player>) ((ArrayList<Player>) Setup.getPlayerList()).clone());
		for (int i = 0; i < Setup.getPlayerList().size(); i++) {
			Player p = Setup.getPlayerList().get(i);
			if (tempList.contains(p)) {
				collision(p, tempList);
			}
		}
	}

	private void collision(Player p1, List<Player> tempList) {
		for (int i = 0; i < Setup.getPlayerList().size(); i++) {
			Player p2 = Setup.getPlayerList().get(i);
			if (p1 != p2) { // On compare bien les references
				if (p1.getPosition().distance(p2.getPosition()) < (Configuration.microbeRadius * 2) - 3) {
					if (Configuration.start)
						eat(p1, p2);
					ecarter(p1, p2);
					collision(p1, tempList);
					collision(p2, tempList);
					tempList.remove(p2);
				}
			}
		}
	}

	private void eat(Player p1, Player p2) {

		if (p1.getTeam().getStrong() == p2.getTeam()) {
			p1.upScore();
			p2.downScore();
			Setup.changePlayer(p2, p1.getTeam());
		} else if (p2.getTeam().getStrong() == p1.getTeam()) {
			p2.upScore();
			p1.downScore();
			Setup.changePlayer(p1, p2.getTeam());
		}
	}

	private void ecarter(Player p1, Player p2) {
		Position centre = new Position((p1.getPosition().getX() + p2.getPosition().getX()) / 2,
				(p1.getPosition().getY() + p2.getPosition().getY()) / 2);
		double distance = centre.distance(p1.getPosition());
		if (distance == 0) {
			p1.getPosition().incX();
			p2.getPosition().decX();
			p1.setDernierePosition(p1.getPosition().getX(), p1.getPosition().getY());
			p2.setDernierePosition(p2.getPosition().getX(), p2.getPosition().getY());
			distance = centre.distance(p1.getPosition());
		}
		double deltaX = (centre.getX() - p1.getPosition().getX()) * (Configuration.microbeRadius / distance);
		double deltaY = (centre.getY() - p1.getPosition().getY()) * (Configuration.microbeRadius / distance);
		p1.setPosition(centre.getX() - deltaX, centre.getY() - deltaY);
		p2.setPosition(centre.getX() + deltaX, centre.getY() + deltaY);

	}

	public static void play() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (KrokSong != null)
					KrokSong.stop();
				if (GrounchSong != null)
					GrounchSong.stop();
				if (BlurpSong != null)
					BlurpSong.stop();
				int rand = (int) (Math.random() * 3);
				switch (rand) {
				case 0:
					if (KrokSong != null)
						KrokSong.play();
					break;
				case 1:
					if (GrounchSong != null)
						GrounchSong.play();
					;
					break;
				case 2:
					if (BlurpSong != null)
						BlurpSong.play();
					break;
				default:
					break;
				}
			}
		});

	}

	public static void play(AudioClip song) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				KrokSong.stop();
				GrounchSong.stop();
				BlurpSong.stop();
				if (song != null)
					song.play();
			}
		});

	}

	private void scoreTeamTable() {
		Text text = new Text();
		Setup.getBlurp().setNameBox(text);
		text.setFont(Font.loadFont(Setup.font, 60));
		text.setText(Setup.getBlurp().getName());
		text.setX(Configuration.width - Configuration.tableWidth + 20);
		text.setY(2 * Configuration.ytext + 2 * Configuration.leading);
		text.setFill(Color.BLUE);
		root.getChildren().add(text);
		Text text1 = new Text();
		Setup.getKrok().setNameBox(text1);
		text1.setFont(Font.loadFont(Setup.font, 60));
		text1.setText(Setup.getKrok().getName());
		text1.setX(Configuration.width - Configuration.tableWidth + 20);
		text1.setY(2 * Configuration.ytext + 4 * Configuration.leading);
		text1.setFill(Color.YELLOW);
		root.getChildren().add(text1);
		Text text2 = new Text();
		Setup.getGrounch().setNameBox(text2);
		text2.setFont(Font.loadFont(Setup.font, 60));
		text2.setText(Setup.getGrounch().getName());
		text2.setX(Configuration.width - Configuration.tableWidth + 20);
		text2.setY(2 * Configuration.ytext);
		text2.setFill(Color.RED);
		root.getChildren().add(text2);

		Text text12 = new Text();
		Setup.getBlurp().setScoreBox(text12);
		text12.setFont(Font.loadFont(Setup.font, 60));
		text12.setText("" + Setup.getBlurp().getScore());
		text12.setX(Configuration.width - 100);
		text12.setY(2 * Configuration.ytext + 2 * Configuration.leading);
		text12.setFill(Color.BLUE);
		root.getChildren().add(text12);
		Text text21 = new Text();
		Setup.getKrok().setScoreBox(text21);
		text21.setFont(Font.loadFont(Setup.font, 60));
		text21.setText("" + Setup.getKrok().getScore());
		text21.setX(Configuration.width - 100);
		text21.setY(2 * Configuration.ytext + 4 * Configuration.leading);
		text21.setFill(Color.YELLOW);
		root.getChildren().add(text21);
		Text text32 = new Text();
		Setup.getGrounch().setScoreBox(text32);
		text32.setFont(Font.loadFont(Setup.font, 60));
		text32.setText("" + Setup.getGrounch().getScore());
		text32.setX(Configuration.width - 100);
		text32.setY(2 * Configuration.ytext);
		text32.setFill(Color.RED);
		root.getChildren().add(text32);

	}

	private void strong() {
		Image image = new Image(Setup.strongImage, Configuration.height / 7, Configuration.height / 7, false, true);
		ImageView strong = new ImageView(image);
		strong.setX(Configuration.width - Configuration.tableWidth - Configuration.height / 7 - 20);
		strong.setY(20);
		root.getChildren().add(strong);
	}

	private void host() {
		Text text = new Text();
		text.setFill(Color.WHITE);
		text.setFont(new Font(20));
		text.setText("LocalIP " + Configuration.host);
		text.setX((Configuration.width - Configuration.height) / 2 + 10);
		text.setY(Configuration.height - 10);
		root.getChildren().add(text);
		
		
		Text text2 = new Text();
		text2.setFill(Color.BLACK);
		text2.setFont(Font.loadFont(Setup.font, 70));
		text2.setText("Music Time :");
		text2.setX(Configuration.width - Configuration.tableWidth + 20);
		text2.setY(Configuration.height/2-100);
		root.getChildren().add(text2);

	}

	private void getScreenSize() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Configuration.width = dimension.width;
		Configuration.height = dimension.height;
		System.out.println("Screen resolution : " + Configuration.width + "x" + Configuration.height);
	}

	private void board() {
		Circle board = new Circle();
		board.setCenterX(Configuration.width / 2);
		board.setCenterY(Configuration.height / 2);
		board.setRadius(Configuration.height / 2 - 10);
		Configuration.boardRadius = Configuration.height / 2 - 10;
		Configuration.pionRadius = (int) (Configuration.boardRadius * Configuration.microbeRadius
				/ (double) Configuration.maxMapRadius);
		board.setFill(Color.GRAY);
		board.setStroke(Color.WHITE);
		board.setStrokeWidth(0);
		root.getChildren().add(board);
	}

	private void scoreTable() {
		Configuration.tableWidth = (Configuration.width - Configuration.height) / 2;

		Rectangle sideBar = new Rectangle();
		sideBar.setX(0);
		sideBar.setY(0);
		sideBar.setWidth(Configuration.tableWidth);
		sideBar.setHeight(Configuration.height);
		sideBar.setArcWidth(0);
		sideBar.setArcHeight(0);
		sideBar.setFill(Color.DARKGRAY);
		sideBar.setStroke(Color.WHITE);
		sideBar.setStrokeWidth(0);

		root.getChildren().add(sideBar);
		Rectangle sideBar1 = new Rectangle();
		sideBar1.setX(Configuration.width - Configuration.tableWidth);
		sideBar1.setY(0);
		sideBar1.setWidth(Configuration.tableWidth);
		sideBar1.setHeight(Configuration.height);
		sideBar1.setArcWidth(0);
		sideBar1.setArcHeight(0);
		sideBar1.setFill(Color.DARKGRAY);
		sideBar1.setStroke(Color.WHITE);
		sideBar1.setStrokeWidth(0);
		root.getChildren().add(sideBar1);
	}

	public static void addPion(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Circle pion = new Circle();
				player.setPion(pion);
				pion.setCenterX((int) Configuration.width / 2);
				pion.setCenterY((int) Configuration.height / 2);
				pion.setRadius(Configuration.pionRadius);
				colorPion(player);
				pion.setStroke(Color.BLACK);
				pion.setStrokeWidth(0);
				root.getChildren().add(pion);

				Circle range = new Circle();
				player.setRange(range);
				range.setCenterX((int) Configuration.width / 2);
				range.setCenterY((int) Configuration.height / 2);
				range.setRadius(0);
				range.setFill(null);
				range.setStroke(Color.BLACK);
				range.setStrokeWidth(2);
				root.getChildren().add(range);
			}
		});
	}

	protected static void colorPion(Player player) {

		Circle pion = player.getPion();
		switch (player.getTeam().getColor()) {
		case "red":
			pion.setFill(Color.RED);
			break;
		case "yellow":
			pion.setFill(Color.YELLOW);
			break;
		case "blue":
			pion.setFill(Color.BLUE);
			break;
		default:
			break;
		}
	}

	public static void addText(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Text text = new Text();
				player.setNameBox(text);
				text.setFont(Font.loadFont(Setup.font, 20));
				text.setText(player.getPlayerID() + " : " + player.getPseudo());
				text.setX(Configuration.xtext);
				text.setY(Configuration.ytext + (Player.getNbPlayer() - 1) * Configuration.leading);
				colorName(player);
				root.getChildren().add(text);

				Text text1 = new Text();
				player.setScoreBox(text1);
				text1.setFont(Font.loadFont(Setup.font, 20));
				text1.setText("" + player.getScore());
				text1.setX(Configuration.xtext + 300);
				text1.setY(Configuration.ytext + (Player.getNbPlayer() - 1) * Configuration.leading);
				colorScore(player);
				root.getChildren().add(text1);
			}
		});
	}

	protected static void colorName(Player player) {

		Text text = player.getNameBox();

		switch (player.getTeam().getColor()) {
		case "red":
			text.setFill(Color.RED);
			break;
		case "yellow":
			text.setFill(Color.YELLOW);
			break;
		case "blue":
			text.setFill(Color.BLUE);
			break;
		default:
			break;
		}

	}

	protected static void colorScore(Player player) {

		Text text = player.getScoreBox();

		switch (player.getTeam().getColor()) {
		case "red":
			text.setFill(Color.RED);
			break;
		case "yellow":
			text.setFill(Color.YELLOW);
			break;
		case "blue":
			text.setFill(Color.BLUE);
			break;
		default:
			break;
		}

	}

	public static void actualizeScore() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < Setup.getPlayerList().size() && i < 35; i++) {
					Player p = Setup.getPlayerList().get(i);
					if (p.getNameBox() != null)
						p.getNameBox().setY(Configuration.ytext + i * Configuration.leading);

					if (p.getScoreBox() != null) {
						p.getScoreBox().setText("" + p.getScore());
						p.getScoreBox().setY(Configuration.ytext + i * Configuration.leading);
					}
				}
			}
		});

	}

	public static void actualizeScoreTeam(int posB, int posK, int posG, int scoreBlurp, int scoreKrok,
			int scoreGrounch) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Setup.getBlurp().getNameBox().setY(2 * Configuration.ytext + 2 * posB * Configuration.leading);
				Setup.getKrok().getNameBox().setY(2 * Configuration.ytext + 2 * posK * Configuration.leading);
				Setup.getGrounch().getNameBox().setY(2 * Configuration.ytext + 2 * posG * Configuration.leading);
				Setup.getBlurp().getScoreBox().setY(2 * Configuration.ytext + 2 * posB * Configuration.leading);
				Setup.getKrok().getScoreBox().setY(2 * Configuration.ytext + 2 * posK * Configuration.leading);
				Setup.getGrounch().getScoreBox().setY(2 * Configuration.ytext + 2 * posG * Configuration.leading);
				Setup.getBlurp().getScoreBox().setText("" + scoreBlurp);
				Setup.getKrok().getScoreBox().setText("" + scoreKrok);
				Setup.getGrounch().getScoreBox().setText("" + scoreGrounch);
			}
		});

	}

	public static void start(int i) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				timerLabel.setText("" + i);
			}
		});
	}

	public static void setTeamTimer(int i) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (i >= 0)
					teamTimer.setText("" + i);
				else
					teamTimer.setText("");
			}
		});
	}

	public static void end() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				premier.setVisible(true);
				deuxieme.setVisible(true);
				troisieme.setVisible(true);
				int s = Setup.getPlayerList().size();
				if (s > 0)
					premier.setText("1 : " + Setup.getPlayerList().get(0).getPseudo() + "  "
							+ Setup.getPlayerList().get(0).getScore());
				else
					premier.setText("1 : null  0");

				if (s > 1)
					deuxieme.setText("2 : " + Setup.getPlayerList().get(1).getPseudo() + "  "
							+ Setup.getPlayerList().get(1).getScore());
				else
					deuxieme.setText("2 : null  0");

				if (s > 2)
					troisieme.setText("3 : " + Setup.getPlayerList().get(2).getPseudo() + "  "
							+ Setup.getPlayerList().get(2).getScore());
				else
					troisieme.setText("3 : null  0");
			}
		});

	}

	public static void addIdBox(Player player) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Text text = new Text();
				player.setIdBox(text);
				text.setFont(Font.loadFont(Setup.font, 1.5*Configuration.pionRadius));
				text.setText(player.getPlayerID() + "");
				text.setX(player.getPosition().getX() * (Configuration.boardRadius) / Configuration.mapRadius
						+ Configuration.width / 2 - Configuration.pionRadius / 2);
				text.setY(player.getPosition().getY() * (Configuration.boardRadius) / Configuration.mapRadius
						+ Configuration.height / 2 - Configuration.pionRadius / 2);
				text.setFill(new Color(0, 0, 0, 1));
				root.getChildren().add(text);
			}
		});

	}

}
