package assignment5;

import java.util.List;
import assignment5.Critter;
import assignment5.Critter.CritterShape;
import javafx.scene.paint.Color;

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
	 * @return String stats
	 */
	public static String runStats(List<Critter> fighters) {
		String stats = new String();
		if (fighters.size() > 1) {
			stats += fighters.size() + " total Fighters    ";
		}
		else {
			stats += "There is only one Fighter";
		}
		
		return stats;	
	}

	@Override
	public CritterShape viewShape() {
		return Critter.CritterShape.SQUARE;
	}
	
	public Color viewOutlineColor() {
		return Color.BLACK;		
	}
	
	public Color viewFillColor() {
		return Color.AQUAMARINE;
	}
}
