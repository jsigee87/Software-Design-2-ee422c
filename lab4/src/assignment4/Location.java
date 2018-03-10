package assignment4;

public class Location {

	private int x;
	private int y;
	private int direction;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Location(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.direction = dir;
	}
	
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
