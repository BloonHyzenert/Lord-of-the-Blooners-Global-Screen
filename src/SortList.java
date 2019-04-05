import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SortList implements Runnable {

	@Override
	public void run() {
		while (!Configuration.end) {

			Collections.sort(Setup.getPlayerList(), Collections.reverseOrder());
			for (int i = 0; i < Setup.getPlayerList().size(); i++) {
				Player p = Setup.getPlayerList().get(i);
				p.setScore((int) (Math.random() * 50 - 25));
				p.getNameBox().setTranslateY(Configuration.ytext + i * Configuration.leading);
				p.getScoreBox().setText("" + p.getScore());
				p.getScoreBox().setTranslateY(Configuration.ytext + i * Configuration.leading);
			}

			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
