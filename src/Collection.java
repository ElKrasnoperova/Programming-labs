/**
 * @author Krasnoperova Elizaveta
 */

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Scanner;
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
		InputStream inputStream = null;		

		try {
			inputStream = new FileInputStream(new File(this.path));
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		}

		Reader inputStreamReader = new InputStreamReader(inputStream);

		int data = 0;
		try {
			data = inputStreamReader.read();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		String fileString = "";
		while(data != -1){
		    fileString += (char) data;
		    try {
				data = inputStreamReader.read();
			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		try {
			inputStreamReader.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println(e.getLocalizedMessage());
		}
	    Document doc = null;
		try {
			doc = builder.parse(new InputSource(new StringReader(fileString)));
		} catch (SAXException | IOException e) {
			System.out.println(e.getLocalizedMessage());
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
	            } 	            
	            Monster monster = new Monster(
	                element.getElementsByTagName("name").item(0).getTextContent(),
	                Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("ordinariness").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("force").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("numberOfLife").item(0).getTextContent())
	            );
	            this.monsterCollection.add(monster);
	            fill=true;
	        }
			 if (fill == true) {
				 System.out.println("Collection is initialized");
				 return fill;	
			 }
	    } 
	    return fill;
	}	

	/**
	 * Method has printed information (type, size, initialization date) about collection
	 */
	public void getInfo() {
		String type = this.monsterCollection.getClass().getName();
		int count = this.monsterCollection.size();
		System.out.println("Type: " + type + "\nSize: " + count + "\nDate: " + this.date);
	}
	
/**
 * Method deletes all elements from the collection which force are greater then 
 * transmitted force.	
 * @param force Monster's force
 */

	public void removeGreater (int force) {
		Predicate<Monster> monsterPredecate = m -> m.force.re() > force;
		if (this.monsterCollection.removeIf(monsterPredecate)) {
			saveToFile();
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
			saveToFile();
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
			saveToFile();
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
			saveToFile();
			System.out.println(m.name + " was added to the collection");
	}
	
/**
 * Method saves collection to file
 */
	public void saveToFile() {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder;
	    try {
	        dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.newDocument();
	        Element rootElement =
	            doc.createElementNS("","Monsters");
	        doc.appendChild(rootElement);
	        int id = 1;
	        for(Monster m: monsterCollection) {
		        rootElement.appendChild(getMonster(doc, "" + id, m.getName(), "" + m.age , "" + m.health, "" + m.ordinariness, "" + (int)m.force.re(), "" + m.numberOfLife));
		        id++;
	        }
	       
	
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        DOMSource source = new DOMSource(doc);
	
	        BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream(this.path));
	        ByteArrayOutputStream baos=new ByteArrayOutputStream();
	        StreamResult result=new StreamResult(bos);
	        transformer.transform(source, result);
	        byte []array=baos.toByteArray();
	        bos.write(array);
	        bos.flush();
	        
	        System.out.println("DONE");
	
	    } catch (Exception e) {
	        System.out.println(e.getLocalizedMessage());
	    }
	}

/**
 * Method forms element from the collection for saving it to file
 * @param doc Object for building DOM tree
 * @param id Monster's serial number
 * @param name Monster's name
 * @param age Monster's age
 * @param health Monster's health 
 * @param ordinariness Monster's ordinariness
 * @param force Monster's force (real part)
 * @param numberOfLife Monster's number of life
 * @return Monster
 */
	private Node getMonster(Document doc, String id, String name, String age, 
			String health, String ordinariness, 
			String force, String numberOfLife){
		Element monster = doc.createElement("Monster");
		 
		monster.setAttribute("id", id);
 
		monster.appendChild(getMonsterElements(doc, monster, "name", name));
		monster.appendChild(getMonsterElements(doc, monster, "age", age));
		monster.appendChild(getMonsterElements(doc, monster, "health", health));
		monster.appendChild(getMonsterElements(doc, monster, "ordinariness", ordinariness));
		monster.appendChild(getMonsterElements(doc, monster, "force", force));
		monster.appendChild(getMonsterElements(doc, monster, "numberOfLife", numberOfLife));
 
        return monster;
	}
	
/**
 * Method forms line with one parameter of element
 * @param doc Object for building DOM tree 
 * @param element Monster
 * @param name Key 
 * @param value Value
 * @return Monster's element
 */

	private static Node getMonsterElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}