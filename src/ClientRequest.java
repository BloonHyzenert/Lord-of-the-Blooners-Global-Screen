import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class ClientRequest  implements Runnable {
	
	private Socket sock;	   
	private PrintWriter writer = null;	   
	private BufferedInputStream reader = null;

	public ClientRequest(Socket client){
		sock=client;
	}

	@Override
	public void run() {
		// Traitement de la connexion
		System.err.println("Lancement du traitement de la connexion cliente");

	      boolean closeConnexion = false;
	      //tant que la connexion est active, on traite les demandes
	      while(!sock.isClosed()){
	         
	         try { 
	            writer = new PrintWriter(sock.getOutputStream());
	            reader = new BufferedInputStream(sock.getInputStream());
	            
	            //On attend la demande du client            
	            String response = read();
	            InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();
	            
	            //On affiche quelques infos 
	            String debug = "";
	            debug = "Thread : " + Thread.currentThread().getName() + ". ";
	            debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() +".";
	            debug += " Sur le port : " + remote.getPort() + ".\n";
	            debug += "\t -> Commande reçue : " + response + "\n";
	            System.err.println("\n" + debug);
	            
	            //Taritement de la demande du client en fonction de la commande envoyée
	            String toSend = "Accepted";
	          
	            
	            //Envoie la réponse au client
	            writer.write(toSend);
	            writer.flush();
	            
	            if(closeConnexion){
	               System.err.println("COMMANDE CLOSE DETECTEE ! ");
	               writer = null;
	               reader = null;
	               sock.close();
	               break;
	            }
	         }catch(SocketException e){
	            System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
	            break;
	         } catch (IOException e) {
	             e.printStackTrace();
	         }         
	      }
	   }

	   //Méthode pour lire les réponses

	   private String read() throws IOException{      
	      String response = "";
	      int stream;
	      byte[] b = new byte[4096];
	      stream = reader.read(b);
	      response = new String(b, 0, stream);

	      return response;
	   }
}
