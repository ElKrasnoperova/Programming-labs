import java.awt.Color;

public class Monster extends Creature implements Traveler {

	public int id;
	protected int ordinariness;
	protected Complex force;
	protected int numberOfLife;
	protected boolean smell;
	protected Color color;

	public Monster(int id, String name, int age, 
			int health, int ordinariness, 
			int force, int numberOfLife, Color color) {
		super(name, age, health);
		this.id = id;
		this.ordinariness = ordinariness;
		this.numberOfLife = numberOfLife;
		this.color = color;
		
		Fear fear = new Fear() {
			public Complex getPower() {
				return new Complex (force,0);
			}
			public String getName() {
				return "Arrrrrrr";
			}
			public boolean isSound(){
				return true;
			}
		};
		this.force = fear.getPower();
	}
	
	public void fright(Person person) {
		if(this.alive)
			person.frightened(this.force);
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

	public boolean isSmell() {
		return this.smell;
	}
	
//	public String getColor() {
//		return color;
//	}
}