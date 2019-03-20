
public class Setup {
	
	final int FACTEUR_TAILLE = 10;
	
	private static Team krok = new Team("Krok"); 	
	private static Team grounch = new Team("Grounch");	
	private static Team blurp = new Team("Blurp"); 
	
	private Team item = new Team("Item");

	public Setup() {
		krok.setStrong(blurp);
		grounch.setStrong(krok);
		blurp.setStrong(grounch);
	}
	
	public void init() {
		Configuration.MAXMAPSIZE = (krok.size()+grounch.size()+blurp.size())*FACTEUR_TAILLE;
		Configuration.MAXSIZE = Configuration.MAXMAPSIZE;
		
	}
	
	public static void addPlayer(Player player) {
		int sizeMin=krok.size();
		Team add=krok;
		if(grounch.size()<sizeMin) {
			sizeMin=grounch.size();
			add=grounch;
		}
		if(blurp.size()<sizeMin) {
			sizeMin=blurp.size();
			add=blurp;
		}
		add.addPlayer(player);
	}

	public Team getKrok() {
		return krok;
	}

	public Team getGrounch() {
		return grounch;
	}

	public Team getBlurp() {
		return blurp;
	}

	public Team getItem() {
		return item;
	}
	
	
}
