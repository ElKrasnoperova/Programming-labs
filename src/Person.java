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

	public void changeFeelSmell(boolean isSmell) {
		this.feelSmell = isSmell;
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
		if(this.place != null) this.place.removePerson(this);
		this.place = place;
		place.addPerson(this);
	}

	public void frightened(Complex force){
		this.fear = this.fear.plus(force);
		if(this.fear.im() > 0) {
			this.torpor = true;
		}
	}

	public String getAge() {
		class HowOld {
			public String toString(){
				return "Age of a character is " + age;
			}
		}
		return new HowOld().toString();	
	}

	public Complex getFear() {
		return fear;
	}

	public boolean isTorpor() {
		return torpor;
	}
	
	public void makeCataclysm(Place place, Place.Cataclysm c) {
		if(this.alive)  {
			place.setCataclysm(c);
		}
	}
}
