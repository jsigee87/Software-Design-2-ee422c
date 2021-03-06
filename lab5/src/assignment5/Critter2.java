package assignment5;

import java.util.List;
import assignment5.Main;
import assignment5.Critter.CritterShape;
import javafx.scene.paint.Color;
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
	 * @param opponent boolean returns false to run away instead of fighting.
	 */
	@Override
	public boolean fight(String opponent) {
		return false;
	}
	
	/**
	 * Displays the critter's stats, per pdf
	 * @param johns a list of all the John critters
	 * @return String stats
	 */
	public static String runStats(List<Critter> johns) {
		String stats = new String();
		if (johns.size() > 1) {
			stats += johns.size() + " total Critter2s    ";
		}
		else {
			stats += "There is only one Critter2";
		}
		
		return stats;
	}


	@Override
	public CritterShape viewShape() {
		return Critter.CritterShape.TRIANGLE;
	}
	
	public Color viewOutlineColor() {
		return Color.BLACK;		
	}
	
	public Color viewFillColor() {
		return Color.BURLYWOOD;
	}
}
