import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

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
				//System.out.println("Response "+response);
				if (response != "") {
					String tabInfos[] = response.split(",");
					switch (Integer.parseInt(tabInfos[0])) {
					case 0:
						player = new Player(this, tabInfos[1]);
						toSend = "0,"+player.getPlayerID() + "," + player.getPseudo() + "," + player.getTeam().getName()
								+ "," + player.getPosition().toString();
						break;
					case 1:
						player.move(Double.parseDouble(tabInfos[1]) * 3, Double.parseDouble(tabInfos[2]) * 3);
						toSend = "1," + player.getPosition().toString() + "," + nextTo();

					default:
						break;
					}
					//System.out.println("Command "+toSend);
					writer.write(toSend);
					writer.flush();
				} else
					closeConnexion = true;
				
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					
				}

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

	private String nextTo() {
		String toSend = "";
		int taille = 0;
		for (int i = 0; i < Setup.getPlayerList().size(); i++) {
			Player p = Setup.getPlayerList().get(i);
			if (p.getPlayerID() != player.getPlayerID()
					&& Math.abs(player.getPosition().getX() - p.getPosition().getX()) < Configuration.visionX
							+ Configuration.microbeRadius
					&& Math.abs(player.getPosition().getY() - p.getPosition().getY()) < Configuration.visionY
							+ Configuration.microbeRadius) {
				toSend += "," + p.getPlayerID() + "," + p.getPseudo() + "," + p.getTeam().getName() + ","
						+ p.getPosition().toString();
				taille++;
			}
		}
		return taille + toSend;
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
