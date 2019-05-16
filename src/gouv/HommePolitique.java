package gouv;

import java.util.HashSet;

import gestion.Event;
import gestion.Historique;

public class HommePolitique {
	protected String nom, prenom;
	private int id;
	
	//Non utilisés, voir README
	private Historique<Event> histo;
	

	protected HashSet<Ministeres> histoMinistereOccupes;
	protected int nbNomiUniques;
	
	public HommePolitique(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}
	
	public HommePolitique(int id, String nom, String prenom) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	public HommePolitique(HommePolitique hm) {
		this.nom = hm.nom;
		this.prenom = hm.prenom;
		this.id = hm.id;
		this.histo = hm.histo;
		this.nbNomiUniques = hm.nbNomiUniques;
	}	

	@Override
	public String toString() {
		return prenom + " " + nom;
	}

	public int getId() {
		return id;
	}
	
	public Historique<Event> getHisto() {
		return histo;
	}
	
	/**
	 * Retourne le nombre de ministères occupés par l'individu
	 * @return taille du tableau réferencant les différents ministères occupés
	 */
	public int getNbMinisOccupes() {
		return histoMinistereOccupes.size();
	}

}
