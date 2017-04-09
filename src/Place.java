import java.util.ArrayList;
import java.util.List;

public class Place implements Location {
	protected String name;
	protected boolean isCataclysmed;
	protected List<Person> people = new ArrayList<Person>();
	protected List<Monster> monsters = new ArrayList<Monster>();
	protected Cataclysm cataclysmTitle;

	public Place(String name) {
		this.name = name;		
		this.isCataclysmed = false;
	}

	public void setCataclysmForPerson() {
		this.isCataclysmed = true;
		for (Person p: people) {
			p.changeHealth(0);
		}
	}
	
	public void setCataclysmForMonster() {
		this.isCataclysmed = true;
		for (Monster m: monsters) {
			m.changeHealth(0);
		}
	}

	protected void addPerson(Person person) throws MoreThanOneException {
		try {
			if(this.people.contains(person)) {
				throw new MoreThanOneException();
			}
			this.people.add(person);
			for (Monster m: monsters) {
				m.fright(person);
				if(m.isSmell()) {
					person.changeFeelSmell(true);
				}
			}
		}
		catch (ArrayOverflowException e) {
			e.getMessage();
		}
	}

	protected void addMonster(Monster monster) throws MoreThanOneException {
		try {
			if(this.monsters.contains(monster)) {
				throw new MoreThanOneException();
			}
			this.monsters.add(monster);
		}
		catch (ArrayOverflowException e) {
			e.getMessage();
		}
	}

	protected void removeCreature(Creature creature) {
		if(creature.getClass().getName() == "Person")
			this.people.remove(creature);
		else
			this.monsters.remove(creature);
	}
	
	public void setName(String string) {
		this.name = string;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCountOfCitizens () {
		return this.people.size();
	}
	
	static enum Cataclysm {
		INFECTION,
		EARTHQUAKE,
		FLOOD,
		ATTACOFCLONES,
		REVOLUTION;
		
		private String description;
		private String destroyTo;
		
		public String getDescription() {
			return this.description;
		}
		
		public void destroy(Place place){
			switch (destroyTo) {
				case "person":  place.setCataclysmForPerson();
				break;
				case "monster": place.setCataclysmForMonster();
				break;
				default: System.out.println("Invalid destroyTo");
				break;
				}
		}
	}
	
	class Cemetry {
		private int countOfGraves;
		private int occupiedGraves;
		private int depthOfGrave;
		
		public Cemetry (int countOfGraves, int occupiedGraves, int depthOfGrave) {
			this.countOfGraves = countOfGraves;
			this.occupiedGraves = occupiedGraves;
			this.depthOfGrave = depthOfGrave;
		}
		
		public void addPerson() {
			if (this.occupiedGraves < this.countOfGraves){
				this.occupiedGraves++;
			}
			else System.out.println("Sorry, you can't visit our cemetry");
		}
		
		public void addMonster() {
			if (this.occupiedGraves < this.countOfGraves){
				this.occupiedGraves += 2;
			}
			else System.out.println("Sorry, you can't visit our cemetry");
		}
	}
}
