import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SortList implements Runnable {

	@Override
	public void run() {
		while(!Configuration.end) {

			Collections.sort(Setup.getScoreTable(), Collections.reverseOrder());
			for (int i = 0; i < Setup.getScoreTable().size(); i++) {
        		Setup.getScoreTable().get(i).setScore(Setup.getScoreTable().get(i).getScore()+1);
    			Setup.getScoreTable().get(i).getBox().setText(Setup.getScoreTable().get(i).getPseudo()+"\t"+Setup.getScoreTable().get(i).getScore());
				Setup.getScoreTable().get(i).getBox().setY(Configuration.yscore+i*Configuration.leading);
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
