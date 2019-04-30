import javafx.application.Application;

public class Launcher {

	public static void main(String[] args) {

		new Setup();
		new Server();
		Application.launch(Display.class, args);

	}
}
