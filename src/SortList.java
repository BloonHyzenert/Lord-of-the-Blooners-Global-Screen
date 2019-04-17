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
					p.getNameBox().setY(Configuration.ytext + i * Configuration.leading);
					p.getScoreBox().setText("" + p.getScore());
					p.getScoreBox().setY(Configuration.ytext + i * Configuration.leading);
				}
				int scoreBlurp = Setup.getBlurp().getScore();
				int posB = 0;
				int scoreKrok = Setup.getKrok().getScore();
				int posK = 0;
				int scoreGrounch = Setup.getGrounch().getScore();
				int posG = 0;
				
				if (scoreBlurp > scoreKrok)
					posB++;
				else posK++;
				if (scoreGrounch > scoreBlurp)
					posG++;
				else posB++;
				if (scoreKrok > scoreGrounch)
					posK++;
				else posG++;
				
				Setup.getBlurp().getNameBox().setY(Configuration.ytext + posB * Configuration.leading);
				Setup.getKrok().getNameBox().setY(Configuration.ytext + posK * Configuration.leading);
				Setup.getGrounch().getNameBox().setY(Configuration.ytext + posG * Configuration.leading);
				Setup.getBlurp().getScoreBox().setY(Configuration.ytext + posB * Configuration.leading);
				Setup.getKrok().getScoreBox().setY(Configuration.ytext + posK * Configuration.leading);
				Setup.getGrounch().getScoreBox().setY(Configuration.ytext + posG * Configuration.leading);
				Setup.getBlurp().getScoreBox().setText(""+scoreBlurp);
				Setup.getKrok().getScoreBox().setText(""+scoreKrok);
				Setup.getGrounch().getScoreBox().setText(""+scoreGrounch);
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
