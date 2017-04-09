public class Person extends Creature implements Traveler {

	protected Complex fear;
	protected boolean torpor;
	protected boolean feelSmell;

	public Person(String name, int age, int health) {
		super(name, age, health);
		this.fear = new Complex(0, 0);
		this.torpor = false;
		this.feelSmell = false;
	}

	public void frightened(Complex force){
		this.fear = this.fear.plus(force);
		if(this.fear.im() > 0) {
			this.torpor = true;
		}
	}

	public void makeCataclysm(Place place) {
		if(this.alive)  {
			place.setCataclysmForPerson();
			place.setCataclysmForMonster();
		}
	}

	@Override
	public void changeHealth(int health) {
		if(this.health <= 0) {
			this.alive = false;
		}
	}

	@Override
	public void changePlace(Place place) throws MoreThanOneException {
		if(!alive) return;
		if(this.place != null) this.place.removeCreature(this);
		this.place = place;
		place.addPerson(this);
	}

	public void changeFeelSmell(boolean isSmell) {
		this.feelSmell = isSmell;
	}

	public Complex getFear() {
		return fear;
	}

	public boolean isTorpor() {
		return torpor;
	}
	
	public String getAge() {
		class HowOld {
			public String toString(){
				return "Age of a character is " + age;
			}
		}
		return new HowOld().toString();	
	}
}
