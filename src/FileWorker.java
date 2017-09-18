import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class FileWorker {

	public static String read(String path) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(path));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		catch(NullPointerException e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}

	Reader inputStreamReader = new InputStreamReader(inputStream);

	int data = 0;try
	{
		data = inputStreamReader.read();
	}catch(
	IOException e)
	{
		System.out.println(e.getLocalizedMessage());
	}
	String fileString = "";while(data!=-1)
	{
		fileString += (char) data;
		try {
			data = inputStreamReader.read();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}try
	{
		inputStreamReader.close();
	}catch(
	IOException e)
	{
		System.out.println(e.getLocalizedMessage());
	}return fileString;
	}

	public static void write(String path, Collection c) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElementNS("", "Monsters");
			doc.appendChild(rootElement);
			int id = 1;
			for (Monster m : c.getMColletion()) {
				rootElement.appendChild(getMonster(doc, "" + id, m.getName(), "" + m.age, "" + m.health,
						"" + m.ordinariness, "" + (int) m.force.re(), "" + m.numberOfLife, m.color));
				id++;
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(bos);
			transformer.transform(source, result);
			byte[] array = baos.toByteArray();
			bos.write(array);
			bos.flush();

			System.out.println("DONE");

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	/**
	 * Method forms element from the collection for saving it to file
	 * 
	 * @param doc
	 *            Object for building DOM tree
	 * @param id
	 *            Monster's serial number
	 * @param name
	 *            Monster's name
	 * @param age
	 *            Monster's age
	 * @param health
	 *            Monster's health
	 * @param ordinariness
	 *            Monster's ordinariness
	 * @param force
	 *            Monster's force (real part)
	 * @param numberOfLife
	 *            Monster's number of life
	 * @return Monster
	 */
	private static Node getMonster(Document doc, String id, String name, String age, String health, String ordinariness,
			String force, String numberOfLife, Color color) {
		Element monster = doc.createElement("Monster");

		monster.setAttribute("id", id);

		monster.appendChild(getMonsterElements(doc, monster, "name", name));
		monster.appendChild(getMonsterElements(doc, monster, "age", age));
		monster.appendChild(getMonsterElements(doc, monster, "health", health));
		monster.appendChild(getMonsterElements(doc, monster, "ordinariness", ordinariness));
		monster.appendChild(getMonsterElements(doc, monster, "force", force));
		monster.appendChild(getMonsterElements(doc, monster, "numberOfLife", numberOfLife));
		monster.appendChild(getMonsterElements(doc, monster, "color", String.valueOf(color.getRGB())));

		return monster;
	}

	/**
	 * Method forms line with one parameter of element
	 * 
	 * @param doc
	 *            Object for building DOM tree
	 * @param element
	 *            Monster
	 * @param name
	 *            Key
	 * @param value
	 *            Value
	 * @return Monster's element
	 */

	private static Node getMonsterElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

}