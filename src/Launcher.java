import java.net.Socket;

import javafx.application.Application;

public class Launcher {

	public static void main(String[] args) {
		
		Setup game = new Setup();
		game.init();
        Application.launch(Display.class, args);
		
		Server server = new Server();
		
		server.create();
		
		server.open();
		

	}

}
