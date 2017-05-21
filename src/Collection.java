/**
 * @author Krasnoperova Elizaveta
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.function.Predicate;

/**
 * Class is for working with the Collection
 */
public class Collection {
	public static final Comparator <Monster> monComp = new Comparator<Monster>() {
		public int compare (Monster m, Monster m1){
			return (int) (m.force.re() - m1.force.re());
		}
	};
	protected String path;
	protected Date date;
	private LinkedHashSet<Monster> monsterCollection;
	
	public Collection() {
		this.monsterCollection = new LinkedHashSet <Monster>();
		this.date = new Date();
	}
	
/**
 * Method adds transmitted element to the collection.
 * @param m Monster
 */
	public void add(Monster m) {
			monsterCollection.add(m);
			FileWorker.write(this.path, this);
	}	

/**
 * Method will add transmitted element to the collection if its force is max.
 * @param m Monster
 * @return True if Monster has been added, false if Monster hasn't been added
 */

	public boolean addIfMax (Monster m)
	{		
    	boolean max=false;
		Monster m1 = Collections.max(monsterCollection, Collection.monComp);
		if(monComp.compare(m1,m) < 0) {
			max=true;
			monsterCollection.add(m);
			FileWorker.write(this.path, this);
		}
		return max;
	}

/**
 * Method will add transmitted element to the collection if its force is min.
 * @param m Monster
 * @return True if Monster has been added, false if Monster hasn't been added
 */
	public boolean addIfMin(Monster m){		
    	boolean min=false;
		Monster m1 = Collections.min(monsterCollection, Collection.monComp);
		if(monComp.compare(m1,m) > 0) {
			min=true;
			monsterCollection.add(m);
			FileWorker.write(this.path, this);
		}
		return min;
	}
	
	
	public LinkedHashSet<Monster> getMColletion() {
		return this.monsterCollection;
	}
	
/**
 * Method deletes all elements from the collection which force are greater then 
 * transmitted force.	
 * @param force Monster's force
 */

	public int removeGreater (int force) {
		int rg=0;
		int current = this.monsterCollection.size();
		Predicate<Monster> monsterPredecate = m -> m.force.re() > force;
		if (this.monsterCollection.removeIf(monsterPredecate)) {
			FileWorker.write(this.path, this);
			rg=current-this.monsterCollection.size();
		}
		return rg;
	}
	
/**
 * Method change path 
 * @param path Path to xml file
 */
	public void setPath(String path) {
		this.path = path;
	}
}