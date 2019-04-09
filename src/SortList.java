import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SortList implements Runnable {

	@Override
	public void run() {
		while (!Configuration.end) {

			try {
				Setup.getSemaphore().acquire();
				Collections.sort(Setup.getPlayerList(), Collections.reverseOrder());
				Setup.getSemaphore().release();
				for (int i = 0; i < Setup.getPlayerList().size(); i++) {
					Player p = Setup.getPlayerList().get(i);
					p.setScore((int) (Math.random() * 50 - 25));
					p.getNameBox().setY(Configuration.ytext + i * Configuration.leading);
					p.getScoreBox().setText("" + p.getScore());
					p.getScoreBox().setY(Configuration.ytext + i * Configuration.leading);
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
