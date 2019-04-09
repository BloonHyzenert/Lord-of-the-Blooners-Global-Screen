import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.InputStream;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Display extends Application{
	
	private static Stage primaryStage;
	private static Group root;

	@Override
	public void start(Stage stage) {
		primaryStage=stage;
		
        primaryStage.setTitle("Lord of the Blooners");
        root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);    
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
            	switch (t.getCode()) {
				case ESCAPE :
					Configuration.end=true;
		               primaryStage.close();
		               System.exit(0);
		               
					break;
					
				case SPACE :
					Setup.init();	

				default:
					break;
				}
            }
        });

		getScreenSize();
        board();
        scoreTable();
        host();
        strong();
        
        primaryStage.show();
        
        AnimationTimer boucle = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				for (int i = 0; i < Setup.getPlayerList().size(); i++) {
					Player p = Setup.getPlayerList().get(i);
					p.getPion().setCenterX(p.getPosition().getX()*(Configuration.boardRadius)/(double)Configuration.mapRadius+Configuration.width/2);
					p.getPion().setCenterY(p.getPosition().getY()*(Configuration.boardRadius)/(double)Configuration.mapRadius+Configuration.height/2);
					p.getPion().setRadius(Configuration.pionRadius);
				}		
			}
		};
		boucle.start();
	}

	private void strong() {
		Class<?> clazz = this.getClass();
		InputStream input = clazz.getResourceAsStream("Image/strongCircle.png");
		Image image = new Image(input,Configuration.height/7,Configuration.height/7,false,true);
		ImageView strong = new ImageView(image);
		strong.setX(Configuration.width-Configuration.tableWidth-Configuration.height/7-20);
		strong.setY(20);
		root.getChildren().add(strong);
	}

	private void host() {
		Text text = new Text();
		text.setFill(Color.WHITE);
		text.setFont(new Font(20));
		text.setText("LocalIP "+Configuration.host);
		text.setX((Configuration.width-Configuration.height)/2+10);
		text.setY(Configuration.height-10);
		root.getChildren().add(text);
		
	}

	private void getScreenSize() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Configuration.width=dimension.width;
		Configuration.height=dimension.height;
		System.out.println("Screen resolution : "+Configuration.width+"x"+Configuration.height);	
	}

	private void board() {
        Circle board = new Circle();
        board.setCenterX(Configuration.width/2);
        board.setCenterY(Configuration.height/2);
        board.setRadius(Configuration.height/2-10);
        Configuration.boardRadius=Configuration.height/2-10;
        Configuration.pionRadius = (int)(Configuration.boardRadius*Configuration.microbeRadius/(double)Configuration.maxMapRadius);
        board.setFill(Color.GRAY);
        board.setStroke(Color.WHITE);
        board.setStrokeWidth(0);	      
        root.getChildren().add(board);	
	}
	
	private void scoreTable() {
		Configuration.tableWidth=(Configuration.width-Configuration.height)/2;
		
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
        sideBar1.setX(Configuration.width-Configuration.tableWidth);
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
	public static void addPion( Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		        Circle pion = new Circle();
		        pion.setCenterX((int) Configuration.width / 2);
		        pion.setCenterY((int) Configuration.height / 2);
		        pion.setRadius(Configuration.pionRadius);
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
		        pion.setStroke(Color.BLACK);	        
		        pion.setStrokeWidth(0);	
		        root.getChildren().add(pion);
		        player.setPion(pion);
			}
		});     
	}
	
	public static void addText(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Text text = new Text();
				text.setFont(new Font(20));
				text.setText(player.getPseudo()+"");
				text.setX(Configuration.xtext);
				text.setY(Configuration.ytext+(Setup.getPlayerList().size()-1)*Configuration.leading);
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
				root.getChildren().add(text);
				player.setNameBox(text);
				
				Text text1 = new Text();
				text1.setFont(new Font(20));
				text1.setText(""+player.getScore());
				text1.setX(Configuration.xtext+150);
				text1.setY(Configuration.ytext+(Setup.getPlayerList().size()-1)*Configuration.leading);
		        switch (player.getTeam().getColor()) {
				case "red":
			        text1.setFill(Color.RED);
					break;
				case "yellow":
			        text1.setFill(Color.YELLOW);
					break;
				case "blue":
			        text1.setFill(Color.BLUE);
					break;
				default:
					break;
				}
				root.getChildren().add(text1);
				player.setScoreBox(text1);
			}
		});  
	}
	

}
