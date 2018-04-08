package polymorphism;

public class Car {
	private String name;
	private int velocity;
	
	public String toString() {
		return name + Integer.toString(velocity);
	}
	
	public Car() {
		this.name = "mycar";
		this.velocity = 5;
	}
	
}
