package gouv;

import gestion.Event;
import gestion.Historique;

public class Ministeres {
	
	private String intitule;
	
	//Non utilisé, voir README
	private int id;
	private Historique<Event> log = new Historique<Event>();
	private int actif; //Duréee (en jours) d’occupation
	
	public Ministeres(String intitule) {
		this.intitule = intitule;
	}
	
	/**
	 * Ajoute un événénement à l'historique du ministère
	 * @param event
	 */
	public void addEvent(Event event){
		log.add(event);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return intitule;
	}
	
	
	
}
