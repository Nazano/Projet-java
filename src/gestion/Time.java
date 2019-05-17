package gestion;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Gère l'écoulement du temps et la date. Il crée un fichier date.txt où il va sauvegarder la date actuelle.
 * Cela permet à n'importe quelle classe d'avoir accès à la date.
 * @author ajulien
 *
 */
public interface Time {
	
	final static SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	
	/**
	 * Récupère la date à partir du fichier local
	 * @return La date lue dans le fichier
	 */
	default Date getDate(){
		Date d = null;		
		try {
			String data;
			data = new String(Files.readAllBytes(Paths.get("date.txt")));
			d = FORMAT.parse(data);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return d;
	}
	
	/**
	 * Augmente la date de 1 jour
	 */
	default void upadteDate() {
		Date d = getDate();		
		Calendar c = Calendar.getInstance();
		
		try {
			c.setTime(d);
			c.add(Calendar.DATE, 1);
			PrintWriter out = new PrintWriter("date.txt");
			
			out.println(FORMAT.format(c.getTime()));
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Change la date contenu dans le fichier date.txt
	 * @param date Nouvelle date
	 */
	default void setDate(Date date) {
		try {
			PrintWriter out = new PrintWriter("date.txt");
			out.println(FORMAT.format(date));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Change la date contenu dans le fichier date.txt.
	 * @param Donner une date de au format dd/MM/yyyy
	 */
	default void setDate(String str_date) {
		try {
			setDate(FORMAT.parse(str_date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
