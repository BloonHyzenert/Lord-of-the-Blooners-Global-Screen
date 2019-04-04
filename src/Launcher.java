

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;

public class Launcher {

	public static void main(String[] args) {
		
		Setup game = new Setup();
        new Thread(new SortList()).start();
		game.init();
		
		Server server = new Server();
		
		server.create();
		
		server.open();
		
        Application.launch(Display.class, args);

	}

}
