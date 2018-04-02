package assignment5;

import assignment5.Critter;
import assignment5.Critter.CritterShape;
import javafx.scene.paint.Color;

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
	
	public static String runStats(java.util.List<Critter> critters) {
		String stats = new String();
		
		if(critters.size() > 1) {
			stats += "" + critters.size() + " total Critter3s    ";
		}
		else {
			stats += "There is only 1 Critter3";
		}
		return stats;
	}

	@Override
	public CritterShape viewShape() {
		return Critter.CritterShape.CIRCLE;
	}
	
	public Color viewOutlineColor() {
		return Color.BLACK;		
	}
	
	public Color viewFillColor() {
		return Color.DARKSALMON;
	}
}