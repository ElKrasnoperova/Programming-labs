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
	private Date date;
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
			System.out.println(m.name + " was added to the collection");
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
			System.out.println(m.name + " was added to the collection");
			return max;
		}
		else System.out.println("Nobody was added to the collection");
		return max;
	}

/**
 * Method will add transmitted element to the collection if its force is min.
 * @param m Monster
 * @return True if Monster has been added, false if Monster hasn't been added
 */
	public boolean addIfMin(Monster m){		
    	boolean min=false;
		Monster m1 = Collections.max(monsterCollection, Collection.monComp);
		if(monComp.compare(m1,m) > 0) {
			min=true;
			monsterCollection.add(m);
			FileWorker.write(this.path, this);
			System.out.println(m.name + " was added to the collection");
			return min;
		}
		else System.out.println("Nobody was added to the collection");
		return min;
	}
	
/**
 * Method has printed information (type, size, initialization date) about collection
 */
	public void getInfo() {
		String type = this.monsterCollection.getClass().getName();
		int count = this.monsterCollection.size();
		System.out.println("Type: " + type + "\nSize: " + count + "\nDate: " + this.date);
	}
	
	public LinkedHashSet<Monster> getMColletion() {
		return this.monsterCollection;
	}
	
/**
 * Method deletes all elements from the collection which force are greater then 
 * transmitted force.	
 * @param force Monster's force
 */

	public void removeGreater (int force) {
		Predicate<Monster> monsterPredecate = m -> m.force.re() > force;
		if (this.monsterCollection.removeIf(monsterPredecate)) {
			FileWorker.write(this.path, this);
			System.out.println("Monsters have been removed");
		}
		else System.out.println("Monsters haven't been removed");	
	}
	
/**
 * Method change path 
 * @param path Path to xml file
 */
	public void setPath(String path) {
		this.path = path;
	}
}