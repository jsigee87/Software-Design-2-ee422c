package assignment4;

/**
 * Daniel_Teleporter will arbitrarily choose a direction in which to move,
 * and will arbitrarily choose to walk n number of times, where n > 1 will make
 * the critter appear as if it is engaging in teleportation.
 * @author Daniel Diamont
 *
 */
public class Teleporter extends Critter {

	private int dir;
	private int numSteps;
	private int maxStep;
	
	Teleporter(){
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
	
	public static void runStats(java.util.List<Critter> Teleporters) {
		if(Teleporters.size() > 1) {
			System.out.print("" + Teleporters.size() + " total Teleporters    ");
			System.out.println();
		}
		else {
			System.out.println("There is only 1 Teleporter");
		}
	}

}