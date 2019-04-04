import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientRequest implements Runnable {

	private Socket sock;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private Player player;
	private String toSend;
	private boolean closeConnexion = false;

	public ClientRequest(Socket client) throws IOException {
		sock = client;
		writer = new PrintWriter(sock.getOutputStream());
		reader = new BufferedInputStream(sock.getInputStream());
	}

	@Override
	public void run() {
		while (!sock.isClosed() && !Configuration.end && !closeConnexion) {
			try {
				sock.setSoTimeout(1000);
				String response = read();
				String tabInfos[] = response.split(",");
				if (response != "") {
					if (Integer.parseInt(tabInfos[0]) == 0) {
						player = new Player(this, tabInfos[1]);
						toSend = player.getPlayerID() + "," + player.getPseudo() + "," + player.getTeam().getName()
								+ "," + player.getPosition().toString();
					} else if (player.getPlayerID() == Integer.parseInt(tabInfos[0])) {
						player.move(Integer.parseInt(tabInfos[1]), Integer.parseInt(tabInfos[2]));
						toSend = player.getPlayerID() + "," + player.getPosition().toString();
					}
					writer.write(toSend);
					writer.flush();
				} else
					closeConnexion = true;

			} catch (SocketException e) {
				closeConnexion = true;
			} catch (IOException e) {
				closeConnexion = true;
			}
		}
		try

		{
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		closeSocket();

	}

	private void closeSocket() {
		closeConnexion = true;
		Setup.removePlayer(player);
		writer = null;
		reader = null;
	}

	private String read() throws IOException {
		String response = "";
		int stream = 0;
		byte[] b = new byte[4096];
		stream = reader.read(b);
		if (stream == -1) {
			closeConnexion = true;
		} else
			response = new String(b, 0, stream);
		return response;
	}
}
