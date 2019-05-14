package gouv;

import gestion.Event;
import gestion.Historique;

public class HommePolitique {
	protected String nom, prenom, poste;
	private Historique<Event> histo;
	private int dureeFctGouv, dureeOccupationMinistere, nbNomiUniques, nbMinisteresOccu;
	
	public HommePolitique(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}
	
	public HommePolitique(HommePolitique hm) {
		this.nom = hm.nom;
		this.prenom = hm.prenom;
		this.histo = hm.histo;
		this.dureeFctGouv = hm.dureeFctGouv;
		this.dureeOccupationMinistere = hm.dureeOccupationMinistere;
		this.nbNomiUniques = hm.nbNomiUniques;
		this.nbMinisteresOccu = hm.nbMinisteresOccu;
	}
	
	@Override
	public String toString() {
		return prenom + " " + nom + " " + poste;
	}
}
