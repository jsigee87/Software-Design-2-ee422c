package polymorphism;

public class Car {
	public double speed;
	public double costPerMile;
	public String name;
	private double distanceTraveled;
	
	public Car () {
		speed = 0;
		costPerMile = 0;
		name = "default";
		setDistanceTraveled(0);
	}
	
	public Car (double cPM, double speed, String name) {
		this.speed = speed;
		this.costPerMile = cPM;
		this.name = name;
	}
	
	public void run(double runTime) {
		this.setDistanceTraveled(this.getDistanceTraveled() + this.speed * runTime);
	}
	
	public double getTotalCost() {
		
		return(costPerMile * getDistanceTraveled());
	}

	public double getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(double distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}
}
