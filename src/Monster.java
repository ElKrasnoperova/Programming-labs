public class Monster extends Creature implements Traveler, Comparable<Monster> {

	protected int ordinariness;
	protected Complex force;
	protected int numberOfLife;
	protected boolean smell;

	public Monster(String name, int age, 
			int health, int ordinariness, 
			int force, int numberOfLife) {
		super(name, age, health);
		this.ordinariness = ordinariness;
		this.numberOfLife = numberOfLife;
		
		Fear fear = new Fear() {
			public String getName() {
				return "Arrrrrrr";
			}
			public Complex getPower() {
				return new Complex (force,0);
			}
			public boolean isSound(){
				return true;
			}
		};
		this.force = fear.getPower();
	}
	
	@Override
	public void changeHealth(int health) {
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
	public void changePlace(Place place) throws MoreThanOneException {
		if(!alive) return;
		if(this.place != null) this.place.removeMonster(this);
		this.place = place;
		place.addMonster(this);
	}

	public void fright(Person person) {
		if(this.alive)
			person.frightened(this.force);
	}

	public boolean isSmell() {
		return this.smell;
	}

	@Override
	public int compareTo(Monster o) {
		return (((int)o.force.re()-(int)this.force.re()));
	}
}