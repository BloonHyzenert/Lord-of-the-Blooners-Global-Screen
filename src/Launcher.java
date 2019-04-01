

import javafx.application.Application;

public class Launcher {

	public static void main(String[] args) {
		
		Setup game = new Setup();
		game.init();
		
		Server server = new Server();
		
		server.create();
		
		server.open();
		
        Application.launch(Display.class, args);

	}

}
