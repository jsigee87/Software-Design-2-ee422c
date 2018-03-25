package assignment5;

/*
 * Daniel_Critter4 will arbitrarily choose a direction in which to move,
 * and will arbitrarily choose to walk n number of times, where n > 1 will make
 * the critter appear as if it is engaging in teleportation.
 * @author Daniel Diamont
 *
 */
public class Critter4 extends Critter {

	private int dir;
	private int numSteps;
	private int maxStep;
	
	Critter4(){
		this.dir = Critter.getRandomInt(8);
		this.maxStep = 4;
		this.numSteps = Critter.getRandomInt(maxStep) + 1;
	}
	
	@Override
	public String toString() { return "T"; }
	
	@Override
	public void doTimeStep() {
		//pick an initial direction
		dir = Critter.getRandomInt(8);
		
		numSteps = Critter.getRandomInt(maxStep) + 1;
		
		//go a random number of steps in that direction
		for(int i = 0; i < numSteps; i++) {
			walk(dir);
		}		
	}

	@Override
	public boolean fight(String oponent) {
		return true; //always choose to fight
	}
	
	public static void runStats(java.util.List<Critter> Critter4s) {
		if(Critter4s.size() > 1) {
			System.out.print("" + Critter4s.size() + " total Critter4s    ");
			System.out.println();
		}
		else {
			System.out.println("There is only 1 Critter4");
		}
	}

}
