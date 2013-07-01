package pt.ulht.tfc.sapconn.domain;

import org.joda.time.DateTime;

public class Recipe extends Recipe_Base implements Comparable<Recipe> {
    
    public  Recipe(String title, String problem, String solution, String author, String tags) {
    	setTitle(title);
    	setProblem(problem);
    	setSolution(solution);
    	setAuthor(author);
    	setTags(tags);
    	setCreationTimestamp(new DateTime());
    	setSapconnManager(SapConnManager.getInstance());
    }

	public int compareTo(Recipe o) {
		// TODO Auto-generated method stub
		return getTitle().toLowerCase().compareTo(o.getTitle().toLowerCase());
	}
}
