package gouv;

import gestion.Time;

public class Ministre extends HommePolitique implements Time{
	
	private Ministeres ministeres;
	
	
	public Ministre(String nom, String prenom, Ministeres ministere) {
		super(nom, prenom);
		this.ministeres = ministere;
	}
	
	
	public Ministre(HommePolitique hm, Ministeres ministere) {
		super(hm);
		this.ministeres = ministere;
	}
}
