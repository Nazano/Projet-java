package app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
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

/**
 * Class principale de la simulation elle regroupe toutes les fonctions d'affichage
 * @author ajulien
 *
 */
public class ViePolitique implements Time{
	
	private	static Date 							currentDate;
	private static String 							path_hp, path_ministres, path_pm, path_presidents; //Chemins d'accès vers les différentes données
	private static Map<Integer, HommePolitique> 	listeHommePolitiques = new HashMap<Integer, HommePolitique>(); //Liste de tous les hommes politiques de la simulation
	private static Set<Ministeres> 					listeMinistere = new HashSet<Ministeres>(); 
	private static Gouvernement 					gouvernement;
	private static HommePolitique 					president;
	
	private 	   Scanner 							keyboard = new Scanner(System.in);
	
	/**
	 * 
	 * @param path_hp Chemin vers l'ensemble des hommes politiques
	 * @param path_fonctions Chemin vers l'ensemble des nomination 
	 * @param path_pm Chemin pour vers l'ensemble des premiers ministres
	 * @param path_president Chemin vers l'ensemble des présidents
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ViePolitique(String path_hp, String path_fonctions, String path_pm, String path_president) throws FileNotFoundException, IOException {
		ViePolitique.path_hp =  path_hp;
		ViePolitique.path_ministres = path_fonctions;
		ViePolitique.path_pm = path_pm;
		ViePolitique.path_presidents = path_president;
		findHPs(); //Charge tous les hommes politiques
	}
	
	/**
	 * Démarre la simulation 
	 * @throws ParseException
	 * @throws IOException
	 */
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
	
	/**
	 * Mode de fonction en jour par jour. Entrée pour avancer d'un jouer et "quit" pour quitter
	 * @throws ParseException
	 * @throws IOException
	 */
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
				else if(input_options.equals("5")) {
					trouverNominations();
				}
					
				
			}
			System.out.println("Tapez Entrée pour continuer vers le jour suivant");
			System.out.println("Tapez \"quit\" pour quitter la simulation");
			input_quitter = keyboard.nextLine();
			if(input_quitter.equals("quit"))
				break;
		}
	}
	
	/**
	 * Affiche l'ensemble des nominations pour une peronne demandé
	 * @throws IOException
	 */
	private void trouverNominations() throws IOException {
		String nom, prenom;
		System.out.println("Entrez le nom de l'individu");
		nom = keyboard.nextLine();
		System.out.println("Entrez le prénom de l'individu");
		prenom = keyboard.nextLine();
		System.out.println(listeNomination(nom, prenom));
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
		sb.append("\t5. Voir nomination pour une personne donnée\n");
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
	private void initGouv(Date date) throws IOException, ParseException {
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
	private void findHPs() throws IOException, FileNotFoundException {
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
	private void trouverPresident(Date date) throws IOException, ParseException {
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
	
	/**
	 * Trouve la liste des nominations associés au nom et prénoms
	 * @throws IOException 
	 */
	private String listeNomination(String nom, String prenom) throws IOException {
		List<HommePolitique> match = trouverHommeP(nom, prenom);
		List<String[]> data = CsvUtils.readCSV(path_ministres);
		StringBuilder nomi = new StringBuilder();
		nomi.append(match.size());
		nomi.append(" personnes trouvés\n\n");
		
		for (HommePolitique p : match) {
			nomi.append(p);
			nomi.append("\n");
			for(String[] line : data) {
				if(p.getId() == Integer.valueOf(line[0])){
					nomi.append("\t");
					nomi.append(line[1]);
					nomi.append(" du " + line[2] + " au " + line[3]);
					nomi.append("\n");
				}
			}
			nomi.append("══════════════════════════════\n");
		}
		
		return nomi.toString();
	}
	
	/**
	 * Retourne tous les hommes politiques associés au nom et prénom suivant
	 * @param nom
	 * @return
	 */
	private List<HommePolitique> trouverHommeP(String nom, String prenom){
		List<HommePolitique> hm = new ArrayList<HommePolitique>(listeHommePolitiques.values());
		List<HommePolitique> match = new ArrayList<HommePolitique>();
		
		for (HommePolitique p : hm) {
			if(p.getNom().toLowerCase().contains(nom.toLowerCase()) && p.getPrenom().toLowerCase().contains(prenom.toLowerCase())) {
				match.add(p);
			}
		}
		return match;
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
