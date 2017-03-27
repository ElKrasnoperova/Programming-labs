import java.util.ArrayList;
import java.util.List;

public class Place {
	protected String name;
	protected boolean isCataclysmed;
	protected List<Person> people = new ArrayList<Person>();
	protected List<Monster> monsters = new ArrayList<Monster>();

	public Place(String name) {
		this.name = name;		
		this.isCataclysmed = false;
	}

	public void setCataclysm() {
		this.isCataclysmed = true;
		for (Person p: people) {
			p.changeHealth(0);
		}
		for (Monster m: monsters) {
			m.changeHealth(0);
		}
	}

	protected void addPerson(Person person) {
		this.people.add(person);
		for (Monster m: monsters) {
			m.fright(person);
			if(m.isSmell()) {
				person.changeFeelSmell(true);
			}
		}
	}

	protected void addMonster(Monster monster) {
		this.monsters.add(monster);
	}

	protected void removeCreature(Creature creature) {
		if(creature.getClass().getName() == "Person")
			this.people.remove(creature);
		else
			this.monsters.remove(creature);
	}
}
