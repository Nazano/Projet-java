package gestion;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public interface Time {

	final static Date START_DATE = new Date(1945, 01, 01);
	final static SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	
	/**
	 * Récupère la date à partir du fichier local
	 * @return La date lue dans le fichier
	 */
	default Date getLocalDate(){
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
		Date d = getLocalDate();		
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
	 * Change la date suivant celle en paramètre
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
	 * Changer la date à partir d'un string
	 * @param str_date
	 */
	default void setDate(String str_date) {
		try {
			setDate(FORMAT.parse(str_date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
