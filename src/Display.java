
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Display extends Application{
	
	private Stage primaryStage;
	private static Group root;

	@Override
	public void start(Stage stage) {
		this.primaryStage=stage;
		
        primaryStage.setTitle("Lord of the Blooners");
        root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.GRAY);    
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
            	switch (t.getCode()) {
				case ESCAPE :
					Configuration.END=true;
		               primaryStage.close();
		               System.exit(0);
		               
					break;

				default:
					break;
				}
            }
        });
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		Configuration.WIDTH=bounds.getWidth();
		Configuration.HEIGHT=bounds.getHeight();
		

        board();
        
        primaryStage.show();
        
		/*Platform.runLater(new Runnable() {

			@Override
			public void run() {
				while(!Configuration.END) {
				System.out.println("Do");
				// TODO Auto-generated method stub
				for (int i = 0; i < Setup.getBlurp().size(); i++) {

					if(Setup.getBlurp().getPlayerList().get(i).getPion()==null) {
						Setup.getBlurp().getPlayerList().get(i).setPion(microbe(Setup.getBlurp().getPlayerList().get(i)));
					}
				}
				for (int i = 0; i < Setup.getGrounch().size(); i++) {

					if(Setup.getGrounch().getPlayerList().get(i).getPion()==null) {
						Setup.getGrounch().getPlayerList().get(i).setPion(microbe(Setup.getGrounch().getPlayerList().get(i)));
					}
				}
				for (int i = 0; i < Setup.getKrok().size(); i++) {

					if(Setup.getKrok().getPlayerList().get(i).getPion()==null) {
						Setup.getKrok().getPlayerList().get(i).setPion(microbe(Setup.getKrok().getPlayerList().get(i)));
						System.out.println("Create");
					}
					System.out.println("FailCreate");
				}
			}
			}
			
		});*/
	}

	private void board() {

        Circle board = new Circle();

        board.setCenterX(primaryStage.getWidth()/2);
        board.setCenterY(primaryStage.getHeight()/2);
        board.setRadius(primaryStage.getHeight()/2-10);
        board.setFill(Color.BLACK);
        board.setStroke(Color.RED);
        board.setStrokeWidth(1);	
        
        /*TranslateTransition translateTransition = new TranslateTransition(); 
        translateTransition.setDuration(Duration.millis(1000)); 
        
        translateTransition.setNode(board);  
        translateTransition.setByX(500); 
        translateTransition.setCycleCount(50); 
        translateTransition.setAutoReverse(false);  
        translateTransition.play(); */
        
        root.getChildren().add(board);
		
	}
	public static void microbe( Player player) {
		Platform.runLater(new Runnable() {

		@Override
		public void run() {
	        Circle pion = new Circle();
	        pion.setCenterX(player.getPosition().getX());
	        pion.setCenterY(player.getPosition().getY());
	        pion.setRadius(Configuration.PIONSIZE);
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
	
	public static void remove(Object o) {
		root.getChildren().remove(o);
	}

}
