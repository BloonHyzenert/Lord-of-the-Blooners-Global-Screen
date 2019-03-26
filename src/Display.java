import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Display extends Application{

	@Override
	public void start(Stage primaryStage) {
		
        primaryStage.setTitle("Lord of the Blooners");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.GRAY);    
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
            	switch (t.getCode()) {
				case ESCAPE :
		               primaryStage.close();	
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

        

        Circle plateau = new Circle();

        plateau.setCenterX(primaryStage.getWidth()/2);
        plateau.setCenterY(primaryStage.getHeight()/2);
        plateau.setRadius(primaryStage.getHeight()/2-10);
        plateau.setFill(Color.BLACK);
        plateau.setStroke(Color.RED);
        plateau.setStrokeWidth(1);		
        
        for (int i = 0; i < Setup.getKrok().size(); i++) {
			microbe(Setup.getKrok(),i);
		}
		
        /*Button btn = new Button();
        btn.setLayoutX(100);
        btn.setLayoutY(80);
        btn.setText("Hello World");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Hello World");
            }
        });

        root.getChildren().add(btn); */  
		
        root.getChildren().add(plateau);
        primaryStage.show();
	}
	
	private void microbe( Team team ,int i) {
		
	}

}
