package gouv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PremierMinistre extends HommePolitique{

	//Nomination pour le gouvernement actuel seulement
	private Set<HommePolitique> nominations = new HashSet<HommePolitique>();
	
	public PremierMinistre(String nom, String prenom) {
		super(nom, prenom);
		// TODO Auto-generated constructor stub
	}
	
	public PremierMinistre(HommePolitique hp) {
		super(hp);
	}

	public Set<HommePolitique> getNominations() {
		return nominations;
	}
	
	

}
