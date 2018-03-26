package assignment5;

import java.util.List;
import assignment5.Main;
import assignment5.Critter;

/**
 * This is my first critter. It runs north all
 * the time and always runs away. Nice for debugging.
 * @author John Sigmon
 *
 */


public class Critter2 extends Critter{
	
	/**
	 * @return string representing the Critter.
	 */
	@Override
	public String toString() {
		String str = new String("2");
		return str;
	}
	
	/**
	 *  The Critter always runs north.
	 */
	@Override
	public void doTimeStep() {
		//if (Main.DEBUG == true){
			//System.out.println("My x is " + this.x_coord + " and y is " + this.y_coord);
		//}
		run(2);
		//if (Main.DEBUG == true) {
			//System.out.println("My x is " + x_coord + " y is " + y_coord);
			//System.out.println("My energy is " + this.getEnergy());
			//System.out.println();
		//}
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
			System.out.print(johns.size() + " total Critter2s    ");
			System.out.println();
		}
		else {
			System.out.println("There is only one Critter2");
		}
	}

}
