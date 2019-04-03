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

public class MenuDisplay extends Application{
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

        
        primaryStage.show();
	}
}
