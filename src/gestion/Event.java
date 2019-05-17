package gestion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Permet de représenter un événement, sera souvent utilisé avec la classe Historique
 * @see gestion.Historique
 * @author Antoine
 *
 */
public class Event {
	private String description;
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	private Date date;
	
	public Event(String date, String description) {
		try {
			this.date = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.description = description;
	}
	
	public Event(Date date, String description) {
		this.date = date;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return date.toString() + ": " + description;
	}
}
