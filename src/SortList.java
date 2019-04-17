import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SortList implements Runnable {

	@Override
	public void run() {
		int teamTime = 0;
		Team first = null;
		while (!Configuration.end && Configuration.start) {

			System.out.println(first+"  "+teamTime);
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

				if (scoreBlurp > scoreKrok) {
					posB++;
				} else
					posK++;
				if (scoreGrounch > scoreBlurp) {
					posG++;
				} else
					posB++;
				if (scoreGrounch > scoreKrok) {
					posG++;
				} else
					posK++;

				if (posB == 2) {
					if (first != Setup.getBlurp())
						teamTime = 0;
					first = Setup.getBlurp();
				} else if (posK == 2) {
					if (first != Setup.getKrok())
						teamTime = 0;
					first = Setup.getKrok();
				} else if (posG == 2) {
					if (first != Setup.getGrounch())
						teamTime = 0;
					first = Setup.getGrounch();
				}

				Setup.getBlurp().getNameBox().setY(Configuration.ytext + posB * Configuration.leading);
				Setup.getKrok().getNameBox().setY(Configuration.ytext + posK * Configuration.leading);
				Setup.getGrounch().getNameBox().setY(Configuration.ytext + posG * Configuration.leading);
				Setup.getBlurp().getScoreBox().setY(Configuration.ytext + posB * Configuration.leading);
				Setup.getKrok().getScoreBox().setY(Configuration.ytext + posK * Configuration.leading);
				Setup.getGrounch().getScoreBox().setY(Configuration.ytext + posG * Configuration.leading);
				Setup.getBlurp().getScoreBox().setText("" + scoreBlurp);
				Setup.getKrok().getScoreBox().setText("" + scoreKrok);
				Setup.getGrounch().getScoreBox().setText("" + scoreGrounch);

				if (Setup.getBlurp().size() == 0 || Setup.getKrok().size() == 0 || Setup.getGrounch().size() == 0) {
					Configuration.start = false;
					System.out.println("END");
				}
				if (teamTime == 15) {
					if (first == Setup.getBlurp() && !Setup.BlurpSong.isPlaying())
						Display.play(Setup.BlurpSong);
					else if (first == Setup.getKrok() && !Setup.KrokSong.isPlaying())
						Display.play(Setup.KrokSong);
					else if (first == Setup.getGrounch() && !Setup.GrounchSong.isPlaying())
						Display.play(Setup.GrounchSong);
				}

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			teamTime++;
		}

	}

}
