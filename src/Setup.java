
public class Setup {
	
	final int FACTEUR_TAILLE = 10;
	
	private static Team krok = new Team("Krok","yellow"); 	
	private static Team grounch = new Team("Grounch","red");	
	private static Team blurp = new Team("Blurp","blue"); 
	
	private static Team item = new Team("Item","white");

	public Setup() {
		krok.setStrong(blurp);
		grounch.setStrong(krok);
		blurp.setStrong(grounch);
	}
	
	public void init() {
		Configuration.MAXMAPSIZE = (krok.size()+grounch.size()+blurp.size())*FACTEUR_TAILLE;
		Configuration.MAPSIZE = Configuration.MAXMAPSIZE;
		
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

	public static Team getKrok() {
		return krok;
	}

	public static Team getGrounch() {
		return grounch;
	}

	public static Team getBlurp() {
		return blurp;
	}

	public static Team getItem() {
		return item;
	}
	
	
}
