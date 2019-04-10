import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Frame implements Runnable {

	@Override
	public void run() {
		while (!Configuration.end) {
			for (int i = 0; i < Setup.getPlayerList().size(); i++) {
				Player p = Setup.getPlayerList().get(i);
				try {
					Setup.getSemaphore().acquire();
					p.setPosition(p.getPosition().getX() + p.getDeltaPosition().getX(),
							p.getPosition().getY() + p.getDeltaPosition().getY());
					Setup.getSemaphore().release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			collisions();
			
			try {
				TimeUnit.MILLISECONDS.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	private void collisions() {
		List tempList = ((List) ((ArrayList) Setup.getPlayerList()).clone());
		for (int i = 0; i < Setup.getPlayerList().size(); i++) {
			Player p = Setup.getPlayerList().get(i);
			if (tempList.contains(p)) {
				collision(p, tempList);
			}
		}
	}

	private void collision(Player p1, List tempList) {
		for (int i = 0; i < Setup.getPlayerList().size(); i++) {
			Player p2 = Setup.getPlayerList().get(i);
			if (p1 != p2) { // On compare bien les références
				if (p1.getPosition().distance(p2.getPosition()) < Configuration.microbeRadius * 2) {
					ecarter(p1, p2);
					collision(p1, tempList);
					collision(p2, tempList);
					tempList.remove(p2);					
				}
			}
		}
	}
	
	private void ecarter(Player p1, Player p2) {
		Position centre = new Position((p1.getPosition().getX() + p2.getPosition().getX())/2, (p1.getPosition().getY() + p2.getPosition().getY())/2);
		p1.setPosition((int) ((p1.getPosition().getX() - centre.getX()) * (Configuration.microbeRadius/p1.getPosition().distance(centre))), (int) ((p1.getPosition().getY() - centre.getY()) * (Configuration.microbeRadius/p1.getPosition().distance(centre))));
		p2.setPosition((int) ((p2.getPosition().getX() - centre.getX()) * (Configuration.microbeRadius/p2.getPosition().distance(centre))), (int) ((p2.getPosition().getY() - centre.getY()) * (Configuration.microbeRadius/p2.getPosition().distance(centre))));
		
	}
}
