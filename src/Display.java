import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
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
        
        primaryStage.show();
        
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
        board.setFill(Color.GRAY);
        board.setStroke(Color.WHITE);
        board.setStrokeWidth(0);	      
        root.getChildren().add(board);	
	}
	
	private void scoreTable() {
        Rectangle sideBar = new Rectangle();
        sideBar.setX(0);
        sideBar.setY(0);
        sideBar.setWidth((Configuration.width-Configuration.height)/2);
        sideBar.setHeight(Configuration.height);
        sideBar.setArcWidth(0);
        sideBar.setArcHeight(0);
        sideBar.setFill(Color.DARKGRAY);
        sideBar.setStroke(Color.WHITE);
        sideBar.setStrokeWidth(0);	
        
        root.getChildren().add(sideBar);
        Rectangle sideBar1 = new Rectangle();
        sideBar1.setX(Configuration.width-(Configuration.width-Configuration.height)/2);
        sideBar1.setY(0);
        sideBar1.setWidth((Configuration.width-Configuration.height)/2);
        sideBar1.setHeight(Configuration.height);
        sideBar1.setArcWidth(0);
        sideBar1.setArcHeight(0);
        sideBar1.setFill(Color.DARKGRAY);
        sideBar1.setStroke(Color.WHITE);
        sideBar1.setStrokeWidth(0);	    
        root.getChildren().add(sideBar1);	
	}
	public static void microbe( Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		        Circle pion = new Circle();
		        pion.setCenterX(player.getPosition().getX());
		        pion.setCenterY(player.getPosition().getY());
		        pion.setRadius(Configuration.pionSize);
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
	
	public static void score(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Text text = new Text();
				text.setFont(new Font(20));
				text.setWrappingWidth((Configuration.width-Configuration.height)/2-40);
				//text.setTextAlignment(TextAlignment.JUSTIFY);
				text.setText(player.getPseudo()+"\t"+player.getScore());
				text.setX(Configuration.xscore);
				text.setY(Configuration.yscore+(Setup.getScoreTable().size()-1)*Configuration.leading);
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
				player.setBox(text);
			}
		});  
	}
	

}
