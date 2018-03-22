package assignment4;

import java.util.List;

import assignment4.Critter;

/**
 * This is my first critter. It runs north all
 * the time and always runs away.
 * @author John Sigmon
 *
 */


public class JohnCritter extends Critter{
	
	/**
	 * @return string representing the Critter.
	 */
	@Override
	public String toString() {
		String str = new String("J");
		return str;
	}
	
	/**
	 *  The Critter always runs north.
	 */
	@Override
	public void doTimeStep() {
		run(2);
	}

	/**
	 * @param boolean returns false to run away instead of fighting.
	 */
	@Override
	public boolean fight(String oponent) {
		return false;
	}
	
	/**
	 * Displays the critter's stats, per pdf
	 * @param johns a list of all the John critters
	 */
	public static void runStats(List<Critter> johns) {
		if (johns.size() > 1) {
			System.out.print(johns.size() + " total Johns    ");
			System.out.println();
		}
		else {
			System.out.println("There is only one John");
		}
	}

}
