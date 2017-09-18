/**
 * @author Krasnoperova Elizaveta
 */

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.function.Predicate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Class is for working with the Collection
 */
public class Collection {
	private String path;
	private Date date;
	private LinkedHashSet<Monster> monsterCollection;
	public Collection() {
		this.monsterCollection = new LinkedHashSet <Monster>();
		this.date = new Date();
	}
	
	public LinkedHashSet<Monster> getMColletion() {
		return this.monsterCollection;
	}
	
	/**
	 * Method change path 
	 * @param path Path to xml file
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Method for adding elements to collection from the file
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public boolean fill() {
		boolean fill = false;			
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println(e.getLocalizedMessage());
		}
	    Document doc = null;
		try {
			doc = builder.parse(new InputSource(new StringReader(FileWorker.read(this.path))));
		} catch (SAXException | IOException | NullPointerException e) {
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	    NodeList list = doc.getElementsByTagName("Monster");
	    
	    for (int i = 0; i < list.getLength(); i++) {
	        Node node = list.item(i);
	        if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element element = (Element) node;
	            if(element.getElementsByTagName("name").item(0) == null){
	            	System.out.println("Name's value is not exist, change it now!");
	            	return false;
	            } else if(element.getElementsByTagName("health").item(0) == null){
	            	System.out.println("Health's value is not exist, change it now!");
	            	return false;
	            } else if(element.getElementsByTagName("ordinariness").item(0) == null){
	            	System.out.println("Ordinarines's value is not exist, change it now!");
	            	return false;
	            } else if(element.getElementsByTagName("force").item(0) == null){
	            	System.out.println("Force's value is not exist, change it now!");
	            	return false;
	            } else if(element.getElementsByTagName("numberOfLife").item(0) == null){
	            	System.out.println("NumberOfLife's value is not exist, change it now!");
	            	return false;
//	            }
//	            	else if(element.getElementsByTagName("color").item(0) == null){
//		            	System.out.println("Color's value is not exist, change it now!");
//		            	return false;
	            } 	            
	            Monster monster = new Monster(
	            	Integer.parseInt(element.getAttribute("id")),
	                element.getElementsByTagName("name").item(0).getTextContent(),
	                Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("ordinariness").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("force").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("numberOfLife").item(0).getTextContent()),
//	                element.getElementsByTagName("color").item(0).getTextContent()
	                new Color(Integer.parseInt(element.getElementsByTagName("color").item(0).getTextContent()))
	            );
	            this.monsterCollection.add(monster);
	            fill=true;
	        }
	    } 
	    return fill;
	}	
	
	public int size() {
		return this.monsterCollection.size();
	}

	/**
	 * Method has printed information (type, size, initialization date) about collection
	 */
	public void getInfo() {
		String type = this.monsterCollection.getClass().getName();
		int count = this.monsterCollection.size();
		System.out.println("Type: " + type + "\nSize: " + count + "\nDate: " + this.date);
	}
	
	public void remove(int id) {
		Predicate<Monster> monsterPredecate = m -> m.id == id;
		if(this.monsterCollection.removeIf(monsterPredecate)) {
			FileWorker.write(this.path, this);
			System.out.println("Monsters have been removed");
		}
		else System.out.println("Monsters haven't been removed");
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

	public static final Comparator <Monster> monComp = new Comparator<Monster>() {
		public int compare (Monster m, Monster m1){
			return (int) (m.force.re() - m1.force.re());
		}
	};
	
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
 * Method adds transmitted element to the collection.
 * @param m Monster
 */
	public void add(Monster m) {
			monsterCollection.add(m);
			FileWorker.write(this.path, this);
			System.out.println(m.name + " was added to the collection");
	}
	
	public Monster getById(int id) {
		for (Monster m: this.monsterCollection) {
			if (m.id == id)
				return m;
		}
		return null;	
	}
	
	public String getMonsterName(Monster m) {
		return m.name;
	}
	
	public void save() {
		FileWorker.write(this.path, this);
	}
	
	public String getPath() {
		return this.path;
	}
	
	public Color getColor(Monster m) {
		return m.color;
	}

}