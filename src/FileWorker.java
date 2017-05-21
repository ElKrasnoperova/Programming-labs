import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

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

public class FileWorker {
	
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
	private static Node getMonster(Document doc, String id, String name, String age, 
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
	
/**
 * Method for adding elements to collection from the file
 * @throws IOException
 * @throws ParserConfigurationException
 * @throws SAXException
 */	
	public static boolean read(String path, Collection c) {
		InputStream inputStream = null;		
		Document doc = null;
		DocumentBuilder builder = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		String fileString = "";
		File file = null;
		
		try {
			file = new File(path);
			if(file.length() == 0) {
				System.out.println("File is empty, but okey, we can work with it");
				return true;
			}
			inputStream = new FileInputStream(file);
			Reader inputStreamReader = new InputStreamReader(inputStream);
			int data = 0;
			data = inputStreamReader.read();
			
			while(data != -1){
			    fileString += (char) data;
			    try {
					data = inputStreamReader.read();
				} catch (IOException e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			inputStreamReader.close();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(fileString)));
		} catch (ParserConfigurationException | IOException | SAXException e) {
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
	            } 	            
	            Monster monster = new Monster(
	                element.getElementsByTagName("name").item(0).getTextContent(),
	                Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("ordinariness").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("force").item(0).getTextContent()),
	                Integer.parseInt(element.getElementsByTagName("numberOfLife").item(0).getTextContent())
	            );
	            c.getMColletion().add(monster);
	        }
	    } 
	    return true;
	}

/**
 * Method saves collection to file
 */
	public static void write(String path, Collection c) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder;
	    try {
	        dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.newDocument();
	        Element rootElement =
	            doc.createElementNS("","Monsters");
	        doc.appendChild(rootElement);
	        int id = 1;
	        for(Monster m: c.getMColletion()) {
		        rootElement.appendChild(getMonster(doc, "" + id, m.getName(), "" + m.age , "" + m.health, "" + m.ordinariness, "" + (int)m.force.re(), "" + m.numberOfLife));
		        id++;
	        }
	       
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        DOMSource source = new DOMSource(doc);
	
	        BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream(path));
	        ByteArrayOutputStream baos=new ByteArrayOutputStream();
	        StreamResult result=new StreamResult(bos);
	        transformer.transform(source, result);
	        byte []array=baos.toByteArray();
	        bos.write(array);
	        bos.flush();	
	    } catch (Exception e) {
	        System.out.println(e.getLocalizedMessage());
	    }
	}
}