package ru.ifmo.se.programming;

public class Person extends Creature {

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
			if(this.fear.re() >= 100) {
				this.torpor = true;
			}
		}
		
		public void makeCataclysm(Place place) {
			place.setCataclysm();
		}
		
		@Override
		public void changeHealth(int health) {
			super.changeHealth(health);
			if(this.health <= 0) {
				this.alive = false;
			}
		}
		
		@Override
		public void changePlace(Place place) {
			super.changePlace(place);
			place.addPerson(this);
		}
		
		public void changeFeelSmell(boolean isSmell) {
			this.feelSmell = isSmell;
		}
	}
