package assignment4;

import assignment4.Critter;

public class Spinner extends Critter {
	
	private int [] dir = {0,2,4,6};
	int dirIndex;
	
	Spinner(){
		this.dirIndex = 0;
	}
	
	@Override
	public String toString() { return "S"; }
	
	@Override
	public void doTimeStep() {
		walk(dir[dirIndex]);
		dirIndex = (dirIndex + 1) % dir.length;
	}

	@Override
	public boolean fight(String oponent) {
		return false; //always choose to run away
	}
	
	public static void runStats(java.util.List<Critter> Spinners) {
		if(Spinners.size() > 1) {
			System.out.print("" + Spinners.size() + " total Spinners    ");
			System.out.println();
		}
		else {
			System.out.println("There is only 1 Spinner");
		}
	}

}