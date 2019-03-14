import java.net.Socket;

public class Launcher {

	public static void main(String[] args) {
		
		Setup game = new Setup();
		
		game.getGrounch().addPlayer(new Socket());
		game.getKrok().addPlayer(new Socket());
		game.getBlurp().addPlayer(new Socket());
		game.init();
		System.out.println(Configuration.MAXSIZE);
		
		Server server = new Server();
		
		server.create();
		
		server.open();
		
		server.close();
		
		
		System.out.println("It works");
		

	}

}
