package ru.ifmo.se.programming;

public class Main {
	public static void main(String[] args){
		Person gedni = new Person("gedni", 18, 100);
		Person person1 = new Person ("person1", 40, 100);
		Person person2 = new Person ("person2", 14, 100);
		Person person3 = new Person ("person3", 21, 100);
		Monster monster0 = new Monster ("monster0", 1100, 100, 2, new Complex(1, 5), 1);
		Monster monster1 = new Monster ("monster1", 321, 100, 8, new Complex (4, 2), 3);
		Place place0 = new Place ("place0");
		Place place1 = new Place ("place1");
		
		person2.changePlace(place0);
		person3.changePlace(place0);
		monster0.changePlace(place0);
		monster0.fright (person2);
		monster0.fright(person3);
		person1.changePlace(place1);
		monster1.changePlace(place1);
		gedni.changePlace(place1);
		gedni.makeCataclysm(place1);
		person2.changePlace(place1);
		person3.changePlace(place1);
		monster1.fright(person2);
		monster1.fright(person3);
		monster1.stupefy(person2);
		monster1.stupefy(person3);
	}
}