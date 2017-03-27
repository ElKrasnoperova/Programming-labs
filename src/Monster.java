public class Monster extends Creature {

	protected int ordinariness;
	protected Complex force;
	protected int numberOfLife;
	protected boolean smell;

	public Monster(String name, int age, 
			int health, int ordinariness, 
			Complex force, int numberOfLife) {
		super(name, age, health);
		this.ordinariness = ordinariness;
		this.force = force;
		this.numberOfLife = numberOfLife;
	}

	public void fright(Person person) {
		person.frightened(this.force);
	}

	public void stupefy(Person person) {
		if(this.smell)
			person.frightened(new Complex(100, 0));
	}

	@Override
	public void changeHealth(int health) {
		super.changeHealth(health);
		if(this.health <= 0) {
			this.numberOfLife--;
			this.smell = true;
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
