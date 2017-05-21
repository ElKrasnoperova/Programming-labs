import java.util.ArrayList;
import java.util.List;

public class Place implements Location {
	public static enum Cataclysm {
		ATTACOFCLONES("Congratulations! Now your name is Dart Veider"),
		EARTHQUAKE("Little jolting. (or not little)"),
		FLOOD("Do you want to drink? Bazinga"),
		INFECTION("This is a chemical infection"),
		REVOLUTION("Naval'ny in da house");
		
		private String description;
		
		private Cataclysm(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return description;	
		}
	}
	class Cemetry {
		private int countOfGraves;
		private int depthOfGrave;
		private int occupiedGraves;
		private List<Creature> deadCreatures = new ArrayList<Creature>();
		
		public Cemetry (int countOfGraves, int depthOfGrave) {
			this.countOfGraves = countOfGraves;
			this.depthOfGrave = depthOfGrave;
			this.occupiedGraves = 0;
		}
		
		public String addCreature(Creature c) {
			if (this.occupiedGraves < this.countOfGraves){
				this.occupiedGraves += 1;
				this.deadCreatures.add(c);
				return "Yeah, you're with us!";
			}
			else return "Sorry, you can't visit our cemetry";
		}		
	}
	protected boolean isCataclysmed;
	protected Cataclysm cataclysmTitle;
	protected Cemetry cemetry = new Cemetry(100, 15);
	protected List<Person> people = new ArrayList<Person>();

	protected List<Monster> monsters = new ArrayList<Monster>();

	protected String name;

	public Place(String name) {
		this.name = name;		
		this.isCataclysmed = false;
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
	
	public String getCataclysmName() {
		return this.cataclysmTitle.name();
	}
	
	public int getCountOfCitizens () {
		return this.people.size();
	}
	
	public String getName() {
		return this.name;
	}
	
	private void killMonster(Monster m) {
		m.changeHealth(0);
		this.cemetry.addCreature(m);
	}
	
	private void killPerson(Person p) {
		p.changeHealth(0);
		this.cemetry.addCreature(p);
	}

	protected void removeMonster (Monster monster) {
		this.monsters.remove(monster);
	}
	
	protected void removePerson (Person person) {
		this.people.remove(person);
	}

	
	public void setCataclysm(Cataclysm c) {
		this.cataclysmTitle = c;
		this.isCataclysmed = true;
		switch (c) {
		case INFECTION: 
			for (Person p: people) {
				this.killPerson(p);
			}
			break;
		case EARTHQUAKE:
			for (Person p: people) {
				p.changeHealth((int)(p.getHealth()*(0.6)));
			}
			for (Monster m: monsters) {
				m.changeHealth((int)(m.getHealth()*(0.4)));
			}
			break;
		case FLOOD: 
			for (Person p: people) {
				this.killPerson(p);
			}
			for (Monster m: monsters) {
				this.killMonster(m);
			}
			break;
		case ATTACOFCLONES: 
			for (Person p: people) {
				p.setName("Dart Veider");
			}
			for (Monster m: monsters) {
				m.setName("Dart Veider");
			}
			break;
		case REVOLUTION: 
			for (Person p: people) {
				if (p.getName().equals("Naval'ny")) {
					this.killPerson(p);
				}
			}
			break;
		}
	}
	
	
	public void setName(String string) {
		this.name = string;
	}
}