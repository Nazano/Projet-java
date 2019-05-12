package gouv;

import java.util.List;

import gestion.Event;
import gestion.Historique;

public class HommePolitique {
	private String nom, prenom;
	private Historique<Event> histo;
	private int dureeFctGouv, dureeOccupationMinistere, nbNomiUniques, nbMinisteresOccu;
	
	public HommePolitique(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}
	
	
}
