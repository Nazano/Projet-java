package app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import gestion.CsvUtils;
import gestion.Time;
import gouv.Gouvernement;
import gouv.HommePolitique;
import gouv.Ministeres;
import gouv.President;
import gouv.Ministre;
import gouv.PremierMinistre;

public class ViePolitique implements Time{
	
	private	static Date 							currentDate;
	private static String 						path_hp, path_ministres, path_pm, path_presidents; //Chemins d'accès vers les différentes données
	private static Map<Integer, HommePolitique> 	listeHommePolitiques = new HashMap<Integer, HommePolitique>(); //Liste de tous les hommes politiques de la simulation
	private static Set<Ministeres> 				listeMinistere = new HashSet<Ministeres>(); 
	private static Gouvernement 					gouvernement;
	private static HommePolitique 				president;
	
	private 		   Scanner 						keyboard = new Scanner(System.in);
	
	
	public ViePolitique(String path_hp, String path_ministres, String path_pm, String path_president) throws FileNotFoundException, IOException {
		ViePolitique.path_hp =  path_hp;
		ViePolitique.path_ministres = path_ministres;
		ViePolitique.path_pm = path_pm;
		ViePolitique.path_presidents = path_president;
		findHPs(); //Charge tous les hommes politiques
	}
	
	public void startSimulation() throws ParseException, IOException {
		Scanner scanner = new Scanner(System.in);
		affichierMenuDemarrage();
		switch (scanner.nextLine()) {
		case "1":
			jourParJour();
			break;
		case "2":
			datePrecise();
			break;
		default:
			System.out.println("Option incorrecte recommencez");
			break;
		}
	}
	
	private void jourParJour() throws ParseException, IOException {
		String input_quitter, input_options;
		
		affichierMenuDemarrage();
		initDate();
		
		System.out.println("Démarrage");
		while(true) {
			initGouv(currentDate);
			findGouvernement(currentDate);
			trouverPresident(currentDate);
			System.out.println(currentDate);
			upadteDate();
			currentDate = getDate();
					
			while(true) {
				menuJourParJour();
				input_options = keyboard.nextLine();
				
				if(input_options.equals("s"))
					break;
				else if(input_options.equals("1"))
					affichierGouvernementEtPresident();
				else if(input_options.equals("2"))
					datePrecise();
				else if(input_options.equals("3"))
					System.out.println(president);
				else if(input_options.equals("4"))
					System.out.println(gouvernement);
				
			}
			System.out.println("Tapez Entrée pour continuer vers le jour suivant");
			System.out.println("Tapez \"quit\" pour quitter la simulation");
			input_quitter = keyboard.nextLine();
			if(input_quitter.equals("quit"))
				break;
		}
	}
	
	/**
	 * Affiche l'état du paysage politique à une date particulière
	 * @throws ParseException 
	 * @throws IOException 
	 */
	private void datePrecise() throws ParseException, IOException {
		initDate();
		initGouv(currentDate);
		findGouvernement(currentDate);
		trouverPresident(currentDate);
		affichierGouvernementEtPresident();
	}
	
	
	private void initDate() throws ParseException {
		System.out.println("Entrez la date au format " + FORMAT.toPattern());
		currentDate = FORMAT.parse(keyboard.nextLine());
		setDate(currentDate);
	}
	
	
	private void affichierGouvernementEtPresident(){
		System.out.println("Président de la république: " + president);
		System.out.println(gouvernement);
	}
	
	/**
	 * Affiche la liste de différentes options à afficher sur la situation politique actuelle.
	 */
	private void menuJourParJour() {
		StringBuilder sb = new StringBuilder();
		sb.append("═══════════════MENU══════════════\n");
		sb.append("Entrez le numéro correspondant pour effectuer l'action: \n");
		sb.append("\t1. Voir tout\n");
		sb.append("\t2. Saut temporel\n");
		sb.append("\t3. Voir le président\n");
		sb.append("\t4. Voir le gouvernement\n");
		sb.append("\"s\" pour quitter le menu et avancer de jour");
		
		System.out.println(sb.toString());
	}
	
	/**
	 * Affiche le logo et le menu de l'écran d'acceuil
	 */
	private void affichierMenuDemarrage(){
		System.out.println("██╗   ██╗██╗███████╗    ██████╗  ██████╗ ██╗     ██╗████████╗██╗ ██████╗ ██╗   ██╗███████╗    \n" + 
				"██║   ██║██║██╔════╝    ██╔══██╗██╔═══██╗██║     ██║╚══██╔══╝██║██╔═══██╗██║   ██║██╔════╝    \n" + 
				"██║   ██║██║█████╗      ██████╔╝██║   ██║██║     ██║   ██║   ██║██║   ██║██║   ██║█████╗      \n" + 
				"╚██╗ ██╔╝██║██╔══╝      ██╔═══╝ ██║   ██║██║     ██║   ██║   ██║██║▄▄ ██║██║   ██║██╔══╝      \n" + 
				" ╚████╔╝ ██║███████╗    ██║     ╚██████╔╝███████╗██║   ██║   ██║╚██████╔╝╚██████╔╝███████╗    \n" + 
				"  ╚═══╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚══════╝╚═╝   ╚═╝   ╚═╝ ╚══▀▀═╝  ╚═════╝ ╚══════╝    \n" + 
				"                                                                                              \n" + 
				"    ███████╗██╗███╗   ███╗██╗   ██╗██╗      █████╗ ████████╗██╗ ██████╗ ███╗   ██╗            \n" + 
				"    ██╔════╝██║████╗ ████║██║   ██║██║     ██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║            \n" + 
				"    ███████╗██║██╔████╔██║██║   ██║██║     ███████║   ██║   ██║██║   ██║██╔██╗ ██║            \n" + 
				"    ╚════██║██║██║╚██╔╝██║██║   ██║██║     ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║            \n" + 
				"    ███████║██║██║ ╚═╝ ██║╚██████╔╝███████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║            \n" + 
				"    ╚══════╝╚═╝╚═╝     ╚═╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝            \n" + 
				"                                                                                              ");
		System.out.println("Faites votre choix (tapez le numéro):");
		System.out.println("\t1. Démarrer une simulation jour par jour");
		System.out.println("\t2. Voir l'état du paysage politique à une date particulière");
		
	}
	
	/**
	 * Initialise le gouvernement avec son premier ministre pour une date donnée
	 * @return 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void initGouv(Date date) throws IOException, ParseException {
		List<String[] > data = CsvUtils.readCSV(path_pm);
		Date dateNomi, dateRenvoi;
		
		for (String[] line : data) {
			
			if (line[2].equals("")) continue;
			dateNomi = FORMAT.parse(line[1]);
			dateRenvoi = FORMAT.parse(line[2]);
			
			//Si compris entre date de nomination (incluse) et date renvoi (Non inclus)
			if((date.after(dateNomi) || date.equals(dateNomi)) && date.before(dateRenvoi)) {
				if (gouvernement != null) {
					if (gouvernement.getPm().getId() != Integer.valueOf(line[0])){
						gouvernement = new Gouvernement(new PremierMinistre(listeHommePolitiques.get(Integer.valueOf(line[0]))));
					}
				}
				else {
					gouvernement = new Gouvernement(new PremierMinistre(listeHommePolitiques.get(Integer.valueOf(line[0]))));
				}
				break;
			}
			gouvernement = new Gouvernement(new PremierMinistre("Pas de premier ministre", "")); 
			
		}		
	}

	/**
	 * Charge la liste de tous les hommes politique dans un hashmap
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void findHPs() throws IOException, FileNotFoundException {
		List<String[] > data = CsvUtils.readCSV(path_hp);
		for (String[] line : data) {
			Integer id = Integer.valueOf(line[0]);
			listeHommePolitiques.put(id, new HommePolitique(id,line[1], line[2]));
		}
	}

	/**
	 * Associe au gouvernement l'ensemble des ministres actifs pour une date donnée
	 * @param date
	 * @throws IOException
	 * @throws ParseException
	 */
	private void findGouvernement(Date date) throws IOException, ParseException{
		List<String[]> data = CsvUtils.readCSV(path_ministres); data.remove(0);
		ArrayList<Ministre> min = new ArrayList<Ministre>();       

		Date dateNomi, dateRenvoi;
		
		//Si la date actuelle est comprise entre la date de nomination et la date de renvoi
		//alors on ajoute le ministre
		//Ne pas inclure la date de renvoi permet de gérer les supressions de postes
		for (String[] line : data) {
				
			if (line[3].equals("")) continue;
			dateNomi = FORMAT.parse(line[2]);
			dateRenvoi = FORMAT.parse(line[3]);
			
			if((date.after(dateNomi) || date.equals(dateNomi)) && date.before(dateRenvoi)) {
				min.add(new Ministre(listeHommePolitiques.get(Integer.valueOf(line[0])), new Ministeres(line[1])));
				listeMinistere.add(new Ministeres(line[1]));
				gouvernement.getPm().getNominations().add(listeHommePolitiques.get(Integer.valueOf(line[0])));
			}
			
		}
		gouvernement.addAll(min);
		
	}

	/**
	 * Trouve le président actif à la date donnée
	 * @param date
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void trouverPresident(Date date) throws IOException, ParseException {
		List<String[]> data = CsvUtils.readCSV(path_presidents);
		Date dateNomi, dateRenvoi;
		
		for (String[] line : data) {
			if (line[2].equals("")) continue;
			dateNomi = FORMAT.parse(line[1]);
			dateRenvoi = FORMAT.parse(line[2]);
			
			if((date.after(dateNomi) || date.equals(dateNomi)) && date.before(dateRenvoi)) {
				president = new President(listeHommePolitiques.get(Integer.valueOf(line[0])));
			}		
			
		}
	}
	
	public static void main(String[] args) {
		try {
			ViePolitique vp = new ViePolitique(
					"src/data/liste_hp",
					"src/data/liste_fonctions",
					"src/data/liste_premier_min",
					"src/data/presidents.csv");
			vp.startSimulation();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
