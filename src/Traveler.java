public interface Traveler {
	void changeHealth (int health);
	void changePlace (Place place) throws MoreThanOneException;
}