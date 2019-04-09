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

			try {
				TimeUnit.MILLISECONDS.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
