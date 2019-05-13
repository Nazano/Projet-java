package gouv;

import gestion.Event;
import gestion.Historique;

public class Ministeres {
	private String intitule;
	private Historique<Event> log = new Historique<Event>();
	
	public Ministeres(String intitule) {
		this.intitule = intitule;
	}
	
	public void addEvent(Event event){
		log.add(event);
	}
	
}
