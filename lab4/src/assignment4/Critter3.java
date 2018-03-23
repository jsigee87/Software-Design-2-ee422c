package assignment4;

import assignment4.Critter;

/**
 * Critter3 is a critter that only moves in a box shaped pattern.
 * It walks once per time step. It will cycle through directions: right, up, 
 * left, and down in order to make the box pattern
 * @author Daniel Diamont
 *
 */
public class Critter3 extends Critter {
	
	private int [] dir = {0,2,4,6};
	int dirIndex;
	
	Critter3(){
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
			System.out.println("There is only 1 Critter3");
		}
	}

}