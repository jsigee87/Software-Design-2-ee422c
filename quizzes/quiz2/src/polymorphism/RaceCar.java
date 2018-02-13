package polymorphism;

public class RaceCar extends Car{
	public double acceleration;
	
	public RaceCar() {
		super();
		acceleration = 0;
	}
	
	public RaceCar(double cPM, double speed, 
			double acceleration, String name) {
		super(cPM,speed,name);
		this.acceleration = acceleration;
	}

	@Override
	public void run(double runTime) {
		super.setDistanceTraveled(speed * runTime + 
				(0.5) * acceleration * (runTime) * (runTime));		
	}
}
