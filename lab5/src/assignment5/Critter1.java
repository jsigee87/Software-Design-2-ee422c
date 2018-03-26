package assignment5;

import java.util.List;
import assignment5.Critter;

/**
 * This is my first critter. It runs north all
 * the time and always runs away.
 * @author John Sigmon
 *
 */
public class Critter1 extends Critter{

	/**
	 *  Default constructor
	 */
	public Critter1() {
		//not used
	}
	
	/**
	 * @return string representing the Critter.
	 */
	@Override
	public String toString() {
		String str = new String("F");
		return str;
	}
	
	/**
	 * Fighter makes a baby and moves randomly.
	 */
	@Override
	public void doTimeStep() {
		Critter baby = new Critter1();
		int move = Critter.getRandomInt(8);
		walk(move);
		this.reproduce(baby, (move + 1) % 7);
	}

	/**
	 *  Always returns true.
	 */
	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * Displays the critter's stats per pdf
	 * @param fighters a list of Fighter critters
	 */
	public static void runStats(List<Critter> fighters) {
		if (fighters.size() > 1) {
			System.out.print(fighters.size() + " total Fighters    ");
			System.out.println();
		}
		else {
			System.out.println("There is only one Fighter");
		}
	
	}
}
