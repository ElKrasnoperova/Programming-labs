public class Monster extends Creature {

	protected int ordinariness;
	protected Complex force;
	protected int numberOfLife;
	protected boolean smell;

	public Monster(String name, int age, 
			int health, int ordinariness, 
			int force, int numberOfLife) {
		super(name, age, health);
		this.ordinariness = ordinariness;
		this.force = new Complex(force, 0);
		this.numberOfLife = numberOfLife;
	}

	public void fright(Person person) {
		if(this.alive)
			person.frightened(this.force);
	}

	@Override
	public void changeHealth(int health) {
		super.changeHealth(health);
		if(this.health <= 0) {
			this.numberOfLife--;
			this.smell = true;
			this.force = new Complex(-1, 1);
			if(this.numberOfLife == 0) {
				this.alive = false;
			}
		}
	}

	@Override
	public void changePlace(Place place) {
		super.changePlace(place);
		place.addMonster(this);
	}

	public boolean isSmell() {
		return this.smell;
	}	
}
