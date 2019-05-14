package gestion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvUtils {
	
	
	private static final char DEFAULT_SEPARATOR = ',';
	
	private final char separator;
	
	public CsvUtils() {
		separator = DEFAULT_SEPARATOR;
	}
	
	public CsvUtils(char separator) {
		this.separator = separator;
	}
	
	public List<String[]> loadCsv(String path) {
		try {
			Stream<String> stream = Files.lines(Paths.get(path));
			List<String[]> l = stream.map(list -> list.split("\n")).flatMap(Arrays::stream).map(list -> list.split(String.valueOf(separator)))
					.collect(Collectors.toList());
			stream.close();
			return l;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<String> split(String str){
	    return Stream.of(str.split(","))
	      .map (elem -> new String(elem))
	      .collect(Collectors.toList());
	} 
	
}
