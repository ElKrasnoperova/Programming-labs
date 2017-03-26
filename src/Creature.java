public abstract class Creature {

	protected String name;
	protected int age;
	protected int health;
	protected boolean alive;

	protected Place place; 

	public Creature(String name, int age, int health) {
		this.name = name;
		this.age = age;
		this.health = health;
		this.alive = true;
	}

	public void changePlace(Place place) {
		this.place = place;
	}

	public void changeHealth(int health) {
		this.health = health;
	}
}
