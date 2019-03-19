import java.net.Socket;

public class Launcher {

	public static void main(String[] args) {
		
		Setup game = new Setup();
		game.init();
		
		Server server = new Server();
		
		server.create();
		
		server.open();
		
		
		System.out.println("It works");
		

	}

}
