import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SortList implements Runnable {

	@Override
	public void run() {
		int teamTime = 0;
		Team first = null;
		Display.timerLabel.setVisible(true);
		for (int j = 9; j > -1; j--) {
			Display.start(j);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Display.timerLabel.setVisible(false);
		Display.play();
		Configuration.maxMapRadius = (Setup.getPlayerList().size()) * 2 * Configuration.microbeRadius + 1;
		Configuration.mapRadius = Configuration.maxMapRadius;
		Configuration.pionRadius = (int) (Configuration.boardRadius * Configuration.microbeRadius
				/ (double) Configuration.maxMapRadius);
		Setup.balance();
		Setup.setStartPositions();
		Configuration.start = true;
		while (!Configuration.end && Configuration.start) {

			System.out.println(first + "  " + teamTime);
			try {
				Setup.getSemaphore().acquire();
				Collections.sort(Setup.getPlayerList(), Collections.reverseOrder());
				Setup.getSemaphore().release();
				Display.actualizeScore();
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

				Display.actualizeScoreTeam(posB, posK, posG, scoreBlurp, scoreKrok, scoreGrounch);

				if (teamTime == 15) {
					if (first == Setup.getBlurp() && !Setup.BlurpSong.isPlaying())
						Display.play(Setup.BlurpSong);
					else if (first == Setup.getKrok() && !Setup.KrokSong.isPlaying())
						Display.play(Setup.KrokSong);
					else if (first == Setup.getGrounch() && !Setup.GrounchSong.isPlaying())
						Display.play(Setup.GrounchSong);
				}

				if (Setup.getBlurp().size() == 0 || Setup.getKrok().size() == 0 || Setup.getGrounch().size() == 0) {
					Configuration.start = false;
					System.out.println("END");
					Display.end();		
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
