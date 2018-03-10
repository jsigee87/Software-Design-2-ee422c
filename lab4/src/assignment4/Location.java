package assignment4;

/**
 * 
 * @author Daniel Diamont and John Sigmon
 *
 */
public class Location {

	//private fields
	private int x;
	private int y;
	private int direction; //populate this field if we want to store the direction of the next step (e.g. reproduction of critters)
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Location(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.direction = dir;
	}
	
	/*
	 * Access methods
	 */
	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDir() {
		return direction;
	}
}
