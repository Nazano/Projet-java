package app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gestion.CsvUtils;
import gouv.HommePolitique;
import gouv.Ministre;
import gouv.President;

public class ViePolitique {
	static Date currentDate;
	
	
	public ViePolitique(Date dateDeDepart) {
		currentDate = dateDeDepart;
	}
	
	public void startSimulation() {
		
	}
	
	
	
	public static void main(String[] args) {
		HommePolitique test = new HommePolitique("Michel", "Gz");
		System.out.println(test);
		test = new President(test);
		System.out.println(test);
		
		/*CsvUtils csv = new CsvUtils();
		List<String[]> l = csv.loadCsv("/Users/fjulien 1/Documents/Polytech /S6/Java/Projet/src/data/liste_hp");
		for (String e : l.get(1))
				System.out.println(e);*/
		
	}
}
