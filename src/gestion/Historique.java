package gestion;

import java.util.ArrayList;

/**
 * Permet de sauvegarder un ensemble d'événements, dévéloppé au minimum car non utilisé dans la simulation
 * @author Antoine
 *
 * @param <T> Comment veut t'on réprésenter notre historique.
 */
public class Historique<T> extends ArrayList<T>{

	public Historique() {
		
	}	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (T elem : this) {
			sb.append(elem);
			sb.append("\n");
		}
		return sb.toString();
	}
}

 