import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SortList implements Runnable {

	@Override
	public void run() {
		int teamTime = 0;
		Team first = null;
		Display.play(null);
		Display.timerLabel.setVisible(true);
		for (int j = 5; j > 0; j--) {
			Display.start(j);
			if (Display.bip != null)
				Display.bip.play();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Display.start(0);
		if (Display.bipend != null)
			Display.bipend.play();
		try {
			TimeUnit.MICROSECONDS.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Display.timerLabel.setVisible(false);
		Display.play();
		Configuration.maxMapRadius = (Math.sqrt(Setup.getPlayerList().size())) * 5 * Configuration.microbeRadius + 1;
		Configuration.mapRadius = Configuration.maxMapRadius;
		Configuration.pionRadius = (int) (Configuration.boardRadius * Configuration.microbeRadius
				/ (double) Configuration.maxMapRadius);
		Setup.balance();
		Setup.setStartPositions();
		Configuration.start = true;
		while (!Configuration.end && Configuration.start) {
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

				if (scoreBlurp < scoreKrok) {
					posB++;
				} else
					posK++;
				if (scoreGrounch < scoreBlurp) {
					posG++;
				} else
					posB++;
				if (scoreGrounch < scoreKrok) {
					posG++;
				} else
					posK++;

				if (posB == 0) {
					if (first != Setup.getBlurp())
						teamTime = 0;
					first = Setup.getBlurp();
				} else if (posK == 0) {
					if (first != Setup.getKrok())
						teamTime = 0;
					first = Setup.getKrok();
				} else if (posG == 0) {
					if (first != Setup.getGrounch())
						teamTime = 0;
					first = Setup.getGrounch();
				}

				Display.actualizeScoreTeam(posB, posK, posG, scoreBlurp, scoreKrok, scoreGrounch);

				if (Setup.getBlurp().size() == 0 || Setup.getKrok().size() == 0 || Setup.getGrounch().size() == 0  || Configuration.stop) {
					Configuration.start = false;
					System.out.println("END");
					Display.end();
					teamTime = 15;
				}
				if (teamTime == 15) {
					if (first == Setup.getBlurp() && Display.BlurpSong != null && !Display.BlurpSong.isPlaying())
						Display.play(Display.BlurpSong);
					else if (first == Setup.getKrok() && Display.KrokSong != null && !Display.KrokSong.isPlaying())
						Display.play(Display.KrokSong);
					else if (first == Setup.getGrounch() && Display.GrounchSong != null
							&& !Display.GrounchSong.isPlaying())
						Display.play(Display.GrounchSong);
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
			Display.setTeamTimer(15 - teamTime);
		}
		
	}
	

}
