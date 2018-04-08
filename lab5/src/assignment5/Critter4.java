package assignment5;

import assignment5.Critter.CritterShape;
import javafx.scene.paint.Color;

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
	
	public static String runStats(java.util.List<Critter> Critter4s) {
		String stats = new String();
		
		if(Critter4s.size() > 1) {
			stats += "" + Critter4s.size() + " total Critter4s    ";
		}
		else {
			stats += "There is only 1 Critter4";
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
		return Color.TOMATO;
	}
}
