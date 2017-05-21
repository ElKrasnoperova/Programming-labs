import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Main {
	public static void main(String[] args) throws MoreThanOneException, IOException, ParseException, ParserConfigurationException, SAXException {
		
		Collection c = new Collection();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					FileWorker.write(c.path, c);
					System.out.println("Programm was closed :(");
				}
				catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
		});
		
		Person gedni = new Person("gedni", 18, 100);
		Person person1 = new Person ("person1", 40, 100);
		Person person2 = new Person ("person2", 14, 100);
		Person person3 = new Person ("person3", 21, 100);
		Monster monster0 = new Monster ("monster0", 1100, 100, 2, 1, 1);
		Monster monster1 = new Monster ("monster1", 321, 100, 8, 4, 3);
		Place place0 = new Place ("place0");
		Place place1 = new Place ("place1");
		

		person2.changePlace(place0);
		person3.changePlace(place0);
		monster0.changePlace(place0);
		person1.changePlace(place1);
		monster1.changePlace(place1);
		gedni.changePlace(place1);
		gedni.makeCataclysm(place1, Place.Cataclysm.FLOOD);
		person2.changePlace(place1);
		person3.changePlace(place1);
		monster1.fright(person2);
		monster1.fright(person3);
		
		Scanner inScanner = new Scanner(System.in);
		String fileName = null;
		boolean fill = false;
		
		while(fill == false) {
			System.out.print("Enter file's name: ");
			if(inScanner.hasNextLine()){
				fileName = inScanner.nextLine();
			
				if (fileName.equals("d")) {
					fileName = "D:\\workspace\\Laba2\\Monsters1.xml";
				} 
				else if (fileName.isEmpty()){
					System.out.println("File's name is empty, try again");
					continue;
				}
				c.setPath(fileName);
				fill = FileWorker.read(fileName, c);
			}
		}
	
		Gson gson = new Gson();
		boolean active = true;
		String name;
		int age;
		int health; 
		int ordinariness;
		int force; 
		int numberOfLife;
		while (active == true) {
			System.out.println("Enter your command info, remove_greater, add_if_max, add, add_if_min, exit");
			name = "MonsterX";
			age = 18;
			health = 100; 
			ordinariness = 0;
			force = 10; 
			numberOfLife = 1;
			
			String command = "";
			String inputString = null;
			if(inScanner.hasNextLine()){
				inputString = inScanner.nextLine();
			}
			String[] split = inputString.split("(?:\\b\\s)");
			command = split[0];	
			
			if (split.length > 1) {
				try {
					JsonObject jobj = gson.fromJson(split[1], JsonObject.class);
					if (jobj.get("name") != null ) {
						name = jobj.get("name").toString();
					}
					if (jobj.get("age") != null) {
						age = jobj.get("age").getAsInt();
					}
					if (jobj.get("health") != null) {
						health = jobj.get("health").getAsInt();
					}
					if (jobj.get("ordinariness") != null) {
						ordinariness = jobj.get("ordinariness").getAsInt();
					}
					if (jobj.get("force") != null) {
						force = jobj.get("force").getAsInt();
					}
					if (jobj.get("numberOfLife") != null) {
						numberOfLife = jobj.get("numberOfLife").getAsInt();
					}
				}
				catch (Exception e){
					System.out.println("Something's wrong with the element");
				}
			}
			Monster m = new Monster (name, age, health, ordinariness,
					force, numberOfLife);
			
			switch (command) {
				case "info": 
					c.getInfo();
					break;
				case "remove_greater":
					c.removeGreater((int)m.force.re());
					break;
				case "rg":
					c.removeGreater((int)m.force.re());
					break;
				case "add_if_max":
					c.addIfMax(m);
					break;
				case "add":
					c.add(m);
					break;
				case "add_if_min":
					c.addIfMin(m);
					break;
				case "exit": 
					FileWorker.write(fileName, c);
					active = false;
					break;
				case "q":
					FileWorker.write(fileName, c);
					active = false;
					break;
				default:
					System.out.println("I'VE TOLD YOU, ENTER THE COMMAND");
					break;
			}
		}
		inScanner.close();
	}
}