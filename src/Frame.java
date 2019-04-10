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
					//System.out.println(p.getPosition().toString());
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
		@SuppressWarnings("unchecked")
		List<Player> tempList = ((List<Player>) ((ArrayList<Player>) Setup.getPlayerList()).clone());
		for (int i = 0; i < Setup.getPlayerList().size(); i++) {
			Player p = Setup.getPlayerList().get(i);
			if (tempList.contains(p)) {
				collision(p, tempList);
			}
		}
	}

	private void collision(Player p1, List<Player> tempList) {
		for (int i = 0; i < Setup.getPlayerList().size(); i++) {
			Player p2 = Setup.getPlayerList().get(i);
			if (p1 != p2) { // On compare bien les references
				if (p1.getPosition().distance(p2.getPosition()) < (Configuration.microbeRadius * 2) - 3) {
					eat(p1, p2);
					ecarter(p1, p2);
					collision(p1, tempList);
					collision(p2, tempList);
					tempList.remove(p2);
				}
			}
		}
	}

	private void eat(Player p1, Player p2) {

		if (p1.getTeam().getStrong() == p2.getTeam()) {
			p1.upScore();
			p1.getTeam().addPlayer(p2.getTeam().removePlayer(p2));
			Display.colorPion(p2);
			Display.colorName(p2);
			Display.colorScore(p2);
		} else if (p2.getTeam().getStrong() == p1.getTeam()) {
			p2.upScore();
			p2.getTeam().addPlayer(p1.getTeam().removePlayer(p1));
			Display.colorPion(p1);
			Display.colorName(p1);
			Display.colorScore(p1);
		}
	}

	private void ecarter(Player p1, Player p2) {
		Position centre = new Position((p1.getPosition().getX() + p2.getPosition().getX()) / 2,
				(p1.getPosition().getY() + p2.getPosition().getY()) / 2);
		double distance = centre.distance(p1.getPosition());
		if (distance == 0) {
			p1.getPosition().incX();
			p2.getPosition().decX();
			distance = centre.distance(p1.getPosition());
		}
		double deltaX = (centre.getX() - p1.getPosition().getX()) * (Configuration.microbeRadius / distance);
		double deltaY = (centre.getY() - p1.getPosition().getY()) * (Configuration.microbeRadius / distance);
		p1.setPosition(centre.getX() - deltaX, centre.getY() - deltaY);
		p2.setPosition(centre.getX() + deltaX, centre.getY() + deltaY);

	}
}
