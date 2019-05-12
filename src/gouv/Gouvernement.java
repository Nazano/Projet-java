package gouv;

import java.util.ArrayList;

public class Gouvernement extends ArrayList<Ministeres> {
	
	private HommePolitique porteParole;
	
	public Gouvernement() {
		// TODO Auto-generated constructor stub
	}
	
	public Gouvernement(ArrayList<Ministeres> minist) {
		this.addAll(minist);
	}
}
