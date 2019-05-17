package gestion;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

/**
 * Gère les fichiers CSV à l'aide de la bibliothèque opencsv
 * @author ajulien
 *
 */
public class CsvUtils {
	
	
	private static final char DEFAULT_SEPARATOR = ';';
	
	/**
	 * Lit un ficher csv avec comme séparateur ';'
	 * @param path Chemin vers le fichier csv
	 * @return Retourne une liste de lignes sous forme d'un tableau de String
	 * @throws IOException
	 */
	public static List<String[]> readCSV(String path) throws IOException {
		File file = new File(path);
		FileReader fr = new FileReader(file);
		CSVParser csvp = new CSVParserBuilder().withSeparator(DEFAULT_SEPARATOR).build();
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
		data.remove(0); //Supprime l'entête
		return data;
	}
}
