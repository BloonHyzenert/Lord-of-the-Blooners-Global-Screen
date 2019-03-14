
public class Setup {
	
	final int FACTEUR_TAILLE = 10;
	
	private Team krok = new Team("Krok"); 	
	private Team grounch = new Team("Grounch");	
	private Team blurp = new Team("Blurp"); 
	
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

	public Team getKrok() {
		return krok;
	}

	public void setKrok(Team krok) {
		this.krok = krok;
	}

	public Team getGrounch() {
		return grounch;
	}

	public void setGrounch(Team grounch) {
		this.grounch = grounch;
	}

	public Team getBlurp() {
		return blurp;
	}

	public void setBlurp(Team blurp) {
		this.blurp = blurp;
	}

	public Team getItem() {
		return item;
	}

	public void setItem(Team item) {
		this.item = item;
	}
	
	
}
