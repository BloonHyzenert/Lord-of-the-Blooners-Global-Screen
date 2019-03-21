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
	private Player player;
	private String toSend;

    private boolean closeConnexion = false;

	public ClientRequest(Socket client){
		sock=client;
	}

	@Override
	public void run() {
		// Traitement de la connexion
		//System.err.println("Lancement du traitement de la connexion cliente");

	      //tant que la connexion est active, on traite les demandes
	      while(!sock.isClosed()){
	         
	         try { 
	            writer = new PrintWriter(sock.getOutputStream());
	            reader = new BufferedInputStream(sock.getInputStream());
	            
	            //On attend la demande du client            
	            String response = read();
	            //InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();
	            if(response!="") {
	            String tabInfos[] = response.split(",");
	            if(Integer.parseInt(tabInfos[0])==0) {
	            	player = new Player(this,tabInfos[1]);
	            	Setup.addPlayer(player);	            
	            	toSend = player.getPlayerID() +","+player.getPseudo()+","+player.getTeam().getName();
	            }
	            else if(player.getPlayerID()==Integer.parseInt(tabInfos[0])) {
	            		toSend = player.getPlayerID()+","+player.getPosition().toString();
	            		player.move(Integer.parseInt(tabInfos[1]), Integer.parseInt(tabInfos[2]));
	            }
	            		
	            else toSend="Erreur";
	            
	          //System.out.println(toSend);
	            //Envoie la réponse au client
	            writer.write(toSend);
	            writer.flush();
	            }
	            
	            if(closeConnexion){
	               //System.err.println("COMMANDE CLOSE DETECTEE ! ");
	 	          player.getTeam().removePlayer(player);
	               writer = null;
	               reader = null;
	               sock.close();
	               break;
	            }
	         }catch(SocketException e){
	            //System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
	            
	            break;
	         } catch (IOException e) {
	         }         
	      }
	      
	   }

	   //Méthode pour lire les réponses

	   private String read() throws IOException{      
	      String response = "";
	      int stream=0;
	      byte[] b = new byte[4096];
	      stream = reader.read(b);
	      if(stream==-1) {
	    	  closeConnexion=true;
	      }
	      else response = new String(b, 0, stream);

	      return response;
	   }
}
