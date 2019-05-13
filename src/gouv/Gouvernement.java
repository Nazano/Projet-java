package gouv;

import java.util.ArrayList;

import gestion.Event;
import gestion.Historique;
import gestion.Time;

public class Gouvernement extends ArrayList<Ministre> implements Time{
	
	private HommePolitique porteParole;
	private PremierMinistre pm;
	private static Historique<Event> log = new  Historique<Event>();
	
	public Gouvernement(PremierMinistre ministre) {
		pm = ministre;
		log.add(new Event(getLocalDate(), "Nouveau gouvernement de " + ministre));
	}
	
	public Gouvernement(PremierMinistre ministre, ArrayList<Ministre> minist) {
		pm = ministre;
		this.addAll(minist);
	}
	
	public void dissolution() {
		this.clear();
	}
	
	public void revoyerMinistre(Ministre ministre) {
		this.remove(ministre);
	}
}
