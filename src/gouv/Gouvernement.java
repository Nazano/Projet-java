package gouv;

import java.util.ArrayList;

import gestion.Event;
import gestion.Historique;
import gestion.Time;

public class Gouvernement extends ArrayList<Ministre> implements Time{
	
	private PremierMinistre pm;
	//Non utilisé, voir README
	private HommePolitique porteParole;
	private static Historique<Event> log = new  Historique<Event>();
	
	public Gouvernement(PremierMinistre ministre) {
		pm = ministre;
	}
	
	public Gouvernement(PremierMinistre ministre, ArrayList<Ministre> minist) {
		pm = ministre;
		this.addAll(minist);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Gouvernement de ");
		sb.append(pm);
		sb.append("\n");
		sb.append("████Liste des ministres████\n");
		for (Ministre m : this){
			sb.append("\t");
			sb.append(m);
			sb.append(" en tant que ");
			sb.append(m.getMinisteres());
			sb.append("\n");
		}
		return sb.toString();
	}

	public PremierMinistre getPm() {
		return pm;
	}
	
	/**
	 * Supprime le gouvernement (Le vide de toutes ses instances), je
	 * suppose que le premier ministre reste en poste
	 */
	public void dissolution() {
		this.clear();
		log.add(new Event(getDate(), "Le gouvernement de " + pm.toString() + " à été dissous"));
	}
	
	/**
	 * 
	 * @param ministre
	 */
	public void revoyerMinistre(Ministre ministre) {
		this.remove(ministre);
		log.add(new Event(getDate(), ministre.toString() + " est renvoyé"));
		ministre.getHisto().add(new Event(getDate(), "renvoyé du gouvernement"));
	}
	
	/**
	 * Défini un porte parole au gouvernement
	 * @param pp HommePolitique qui deviendra un porte parole
	 */
	public void setPorteParole(HommePolitique pp) {
		if(porteParole != null) { //Si non nul alors il y avait un porte parole en service avant
			porteParole.getHisto().add(new Event(getDate(), "Fin des fonctions de porte parole"));
			log.add(new Event(getDate(), porteParole.toString() + " quitte le poste de porte parole du gouvernement"));
		}
		
		porteParole = pp;
		pp.getHisto().add(new Event(getDate(), "Porte parole du gouvernement"));
		
		log.add(new Event(getDate(), pp.toString() + " nommé en tant de porte parole du gouvernement"));
	}
	
}
