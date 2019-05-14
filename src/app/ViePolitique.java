package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import gestion.Time;
import gouv.HommePolitique;
import gouv.President;

public class ViePolitique implements Time{
	static Date currentDate;
	
	
	public ViePolitique(Date dateDeDepart) {
		currentDate = dateDeDepart;
	}
	
	public void startSimulation(String path) {
		
	}
	
	//Commencer par charger tous les hommes politiques
	protected void init() {
		
	}
	
	//Convertir les dates en int
	//Pour chaque jour charger les événements associés
	protected void loadData(String path) {
		
	}
	
	public List<HommePolitique> findHPs(String path) throws IOException, FileNotFoundException {
		File file = new File(path);
		FileReader fr = new FileReader(file);
		CSVParser csvp = new CSVParserBuilder().withSeparator(',').build();
		CSVReader csvR = new CSVReaderBuilder(fr).withCSVParser(csvp).build();
		
		List<String[] > data = new ArrayList<String[] >();

		String[] nextLine = null;
		while ((nextLine = csvR.readNext()) != null) {
		    int size = nextLine.length;

		    // ligne vide
		    if (size == 0) {
		        continue;
		    }
		    String debut = nextLine[0].trim();
		    if (debut.length() == 0 && size == 1) {
		        continue;
		    }

		    // ligne de commentaire
		    if (debut.startsWith("#")) {
		        continue;
		    }
		    data.add(nextLine);
		}
		ArrayList<HommePolitique> hp = new ArrayList<HommePolitique>();
		for (String[] line : data) {
			hp.add(new HommePolitique(line[0], line[1]));
		}
		
		return hp;
	}
	
	protected void searchDate(Date date) {
		
	}
	
	
	public static void main(String[] args) {
		HommePolitique test = new HommePolitique("Michel", "Gz");
		System.out.println(test);
		test = new President(test);
		System.out.println(test);
		ViePolitique vp = new ViePolitique(currentDate);
		try {
			System.out.println(System.getProperty("user.dir"));
			List<HommePolitique> hp = vp.findHPs("/Users/ajulien/git/Projet-java/src/data/liste_hp");
			System.out.println(hp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*CsvUtils csv = new CsvUtils();
		List<String[]> l = csv.loadCsv("/Users/fjulien 1/Documents/Polytech /S6/Java/Projet/src/data/liste_hp");
		for (String e : l.get(1))
				System.out.println(e);*/
		
	}
}
