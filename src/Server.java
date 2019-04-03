import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private static int PORT = 7778;
	
	private static boolean isRunning = true;
	
	private static ServerSocket server;
	
	private String host = "192.168.1.2";
	
	public void create() {
		// Création socket du serveur
		try {
			server = new ServerSocket(PORT,100);
			System.err.println("Lancement : Port " + PORT);
		} catch (IOException e) {			
			// Problème port
			System.err.println("Le port " + PORT + " est déjà utilisé ! ");
		}
	}
	
	public void create(String tHost, int tPort) {
		// Création socket du serveur paramétré
		PORT = tPort;
		host = tHost;
		try {
			server = new ServerSocket(PORT);
		} catch (IOException e) {			
			// Problème port
			System.err.println("Le port " + PORT + " est déjà utilisé ! ");
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
