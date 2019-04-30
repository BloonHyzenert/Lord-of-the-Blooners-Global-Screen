import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Display extends Application {

	private static Stage primaryStage;
	private static Group root;

	@Override
	public void start(Stage stage) {

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
		
		primaryStage.show();

		AnimationTimer boucle = new AnimationTimer() {

			public void handle(long now) {
				for (int i = 0; i < Setup.getPlayerList().size(); i++) {
					Player p = Setup.getPlayerList().get(i);
					//System.out.println("\nDerniere position : " + p.getDernierePosition().toString());
					try {
						Position tempPos = new Position();
						double deltaX, deltaY;
						
						double vitesseDeplacement;
						// System.out.println(p.getPosition().toString());
						Setup.getSemaphore().acquire();
						tempPos.setPosition(p.getPosition().getX(), p.getPosition().getY());
						//System.out.println("X : " + p.getPosition().getX() + "   Y : " + p.getPosition().getY());
						//System.out.println("X : " + tempPos.getX() + "   Y : " + tempPos.getY());
						System.out.println("Dernierepos : " + p.getDernierePosition());
						deltaX = p.getDeltaPosition().getX() + Configuration.coefFriction *(p.getPosition().getX() - p.getDernierePosition().getX());
						deltaY = p.getDeltaPosition().getY() + Configuration.coefFriction *(p.getPosition().getY() - p.getDernierePosition().getY());
						vitesseDeplacement = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
						//System.out.println("Id : " + p.getPlayerID()+ "  deltaX : " + deltaX + "  deltaY : " + deltaY + "\np.position : "+  p.getPosition().toString() + "    p.derniereposition" + p.getDernierePosition().toString());
						if (vitesseDeplacement > Configuration.vitesseMax) {
							deltaX = (deltaX / vitesseDeplacement) * Configuration.vitesseMax;
							deltaY = (deltaY / vitesseDeplacement) * Configuration.vitesseMax;
						}
						System.out.println("DeltaX : " + deltaX);
						//System.out.println("Id : " + p.getPlayerID()+ "  deltaX : " + deltaX + "  deltaY : " + deltaY + "    p.position : "+  p.getPosition().toString());
						p.setDernierePosition(tempPos);
						p.setPosition(p.getPosition().getX() + deltaX, p.getPosition().getY() + deltaY);
						//System.out.println("ID : " + p.getPlayerID()+ "   TempPos : " + tempPos.toString());
						
						//System.out.println("\nDerniere position : " + p.getDernierePosition().toString());
						//System.out.println("Id : " + p.getPlayerID()+ "  deltaX : " + deltaX + "  deltaY : " + deltaY + "\nposition : "+ p.getPosition().toString() + "  dernierePosition : " + p.getDernierePosition().toString() + "\n deltaPosition" + p.getDeltaPosition().toString()) ;
						
						//System.out.println("id : "+ p.getPlayerID()+ "    deltaPosition : " + p.getDeltaPosition().toString());
						//p.setPosition(p.getPosition().getX() + p.getDeltaPosition().getX(),
								//p.getPosition().getY() + p.getDeltaPosition().getY());
						if (p.getPion() != null) {
							p.getPion().setCenterX(
									p.getPosition().getX() * (Configuration.boardRadius) / Configuration.mapRadius
											+ Configuration.width / 2);
							p.getPion().setCenterY(
									p.getPosition().getY() * (Configuration.boardRadius) / Configuration.mapRadius
											+ Configuration.height / 2);
							p.getPion().setRadius(Configuration.pionRadius);
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
			Setup.changePlayer(p2, p1.getTeam());
		} else if (p2.getTeam().getStrong() == p1.getTeam()) {
			p2.upScore();
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
				Setup.KrokSong.stop();
				Setup.GrounchSong.stop();
				Setup.BlurpSong.stop();
				int rand = (int) (Math.random() * 3);
				switch (rand) {
				case 0:
					Setup.KrokSong.play();
					break;
				case 1:
					Setup.GrounchSong.play();
					;
					break;
				case 2:
					Setup.BlurpSong.play();
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
				Setup.KrokSong.stop();
				Setup.GrounchSong.stop();
				Setup.BlurpSong.stop();
				song.play();
			}
		});

	}

	private void scoreTeamTable() {
		Text text = new Text();
		Setup.getBlurp().setNameBox(text);
		text.setFont(new Font(20));
		text.setText(Setup.getBlurp().getName());
		text.setX(Configuration.width - Configuration.tableWidth + 20);
		text.setY(Configuration.ytext + Configuration.leading);
		text.setFill(Color.BLUE);
		root.getChildren().add(text);
		Text text1 = new Text();
		Setup.getKrok().setNameBox(text1);
		text1.setFont(new Font(20));
		text1.setText(Setup.getKrok().getName());
		text1.setX(Configuration.width - Configuration.tableWidth + 20);
		text1.setY(Configuration.ytext + 2 * Configuration.leading);
		text1.setFill(Color.YELLOW);
		root.getChildren().add(text1);
		Text text2 = new Text();
		Setup.getGrounch().setNameBox(text2);
		text2.setFont(new Font(20));
		text2.setText(Setup.getGrounch().getName());
		text2.setX(Configuration.width - Configuration.tableWidth + 20);
		text2.setY(Configuration.ytext);
		text2.setFill(Color.RED);
		root.getChildren().add(text2);

		Text text12 = new Text();
		Setup.getBlurp().setScoreBox(text12);
		text12.setFont(new Font(20));
		text12.setText("" + Setup.getBlurp().getScore());
		text12.setX(Configuration.width - Configuration.tableWidth + 200);
		text12.setY(Configuration.ytext + Configuration.leading);
		text12.setFill(Color.BLUE);
		root.getChildren().add(text12);
		Text text21 = new Text();
		Setup.getKrok().setScoreBox(text21);
		text21.setFont(new Font(20));
		text21.setText("" + Setup.getKrok().getScore());
		text21.setX(Configuration.width - Configuration.tableWidth + 200);
		text21.setY(Configuration.ytext + 2 * Configuration.leading);
		text21.setFill(Color.YELLOW);
		root.getChildren().add(text21);
		Text text32 = new Text();
		Setup.getGrounch().setScoreBox(text32);
		text32.setFont(new Font(20));
		text32.setText("" + Setup.getGrounch().getScore());
		text32.setX(Configuration.width - Configuration.tableWidth + 200);
		text32.setY(Configuration.ytext);
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
				text.setFont(new Font(20));
				text.setText(player.getPseudo() + "");
				text.setX(Configuration.xtext);
				text.setY(Configuration.ytext + (Setup.getPlayerList().size() - 1) * Configuration.leading);
				colorName(player);
				root.getChildren().add(text);

				Text text1 = new Text();
				player.setScoreBox(text1);
				text1.setFont(new Font(20));
				text1.setText("" + player.getScore());
				text1.setX(Configuration.xtext + 150);
				text1.setY(Configuration.ytext + (Setup.getPlayerList().size() - 1) * Configuration.leading);
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
				for (int i = 0; i < Setup.getPlayerList().size(); i++) {
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
				Setup.getBlurp().getNameBox().setY(Configuration.ytext + posB * Configuration.leading);
				Setup.getKrok().getNameBox().setY(Configuration.ytext + posK * Configuration.leading);
				Setup.getGrounch().getNameBox().setY(Configuration.ytext + posG * Configuration.leading);
				Setup.getBlurp().getScoreBox().setY(Configuration.ytext + posB * Configuration.leading);
				Setup.getKrok().getScoreBox().setY(Configuration.ytext + posK * Configuration.leading);
				Setup.getGrounch().getScoreBox().setY(Configuration.ytext + posG * Configuration.leading);
				Setup.getBlurp().getScoreBox().setText("" + scoreBlurp);
				Setup.getKrok().getScoreBox().setText("" + scoreKrok);
				Setup.getGrounch().getScoreBox().setText("" + scoreGrounch);
			}
		});
		// TODO Auto-generated method stub

	}

}
