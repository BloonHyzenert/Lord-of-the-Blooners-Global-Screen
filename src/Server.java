import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

public class Server {

	private static int PORT = 7778;

	private static ServerSocket server;

	private ArrayList<InetAddress> hostAddress = new ArrayList<InetAddress>();

	public Server() {
		create();
		open();
	}
	
	public void create() {
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					hostAddress.add(i);
					if(i instanceof Inet4Address && !i.isLoopbackAddress()  && !i.isLinkLocalAddress()) {
						Configuration.host = i.getHostAddress();
					}
				}
			}
			server = new ServerSocket(PORT, 100);
			System.out.println("Launching : Port " + PORT + " IP " + Configuration.host + " Nom "+InetAddress.getLocalHost().getHostName());
		} catch (IOException e) {
			System.err.println("Launching Error");
		}
	}

	public void open() {
		new Thread(new Runnable() {
			public void run() {
				while (!Configuration.end) {
					while (!Configuration.start) {
						try {
							Socket client = server.accept();
							new Thread(new ClientRequest(client)).start();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if(Configuration.end)
							break;
					}
					try {
						TimeUnit.SECONDS.wait(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
					server = null;
				}
			}
		}).start();
	}

	public void close() {
		try {
			server.close();
			System.out.println("Shutdown Server");
		} catch (IOException e) {
			System.out.println("Shutdown Server Error");
			e.printStackTrace();
			server = null;
		}
	}
}
