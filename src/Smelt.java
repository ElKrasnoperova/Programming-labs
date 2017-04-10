public class Smelt {
	 protected String title;
	 protected boolean isPleasure;
	 protected boolean fromMonster;
	 protected int danger;
	 
	 public Smelt (String title, boolean isPleasure, boolean fromMonster, int danger) {
		 this.title = title;
		 this.isPleasure = isPleasure;
		 this.fromMonster = fromMonster;
		 this.danger = danger;
	 }
	 
	 public boolean isDanger() {
		 if (this.danger > 0) {
			 return true;
		 }
		 else return false;
	 }
}