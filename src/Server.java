import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;

public class Server {
	
	private static int PORT = 7778;
	
	private static boolean isRunning = true;
	
	private static ServerSocket server;
	
	private ArrayList<InetAddress> hostAddress = new ArrayList<InetAddress>();
	
	public void create() {
		// Création socket du serveur
		try {
			Enumeration e = NetworkInterface.getNetworkInterfaces();
		while(e.hasMoreElements())
		{
		    NetworkInterface n = (NetworkInterface) e.nextElement();
		    Enumeration ee = n.getInetAddresses();
		    while (ee.hasMoreElements())
		    {
		        InetAddress i = (InetAddress) ee.nextElement();
		        	hostAddress.add(i);
		       // System.out.println(i.getHostAddress());
		    }
		}
		
			InetAddress addr = InetAddress.getLocalHost();
			server = new ServerSocket(PORT,100);
			System.out.println("Launching : Port " + PORT +" IP " + hostAddress.get(1).getHostAddress());
		} catch (IOException e) {			
			// Problème port
			System.err.println("Launching Error");
		}
	}
	
	//Lancement du serveur

	   public void open(){  
	      //Gestion des clients
	      Thread t = new Thread(new Runnable(){
	         public void run(){
	            while(isRunning == true && !Configuration.END){ 
	            	
	               //Attente d'un client
				  try { 
					  
					  Socket client = server.accept();
				      
					  //Traitement de la requête                
					  System.out.println("Connexion cliente reçue.");                  
					  Thread t = new Thread(new ClientRequest(client));
					  t.start();
					
				} catch (IOException e) {
					// En attente
					//System.out.println("En attente.....");
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
	      });

	      

	      t.start();

	   }
	   
	   public void close() {

			try {
				server.close();
			} catch (IOException e) {
				System.out.println("Fermeture du Serveur");
				// Fermeture socket du serveur
				e.printStackTrace();
				server=null;
			}
	   }
	
	

}
