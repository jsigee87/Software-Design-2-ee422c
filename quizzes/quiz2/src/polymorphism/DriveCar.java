package polymorphism;

import java.util.ArrayList;

public class DriveCar {
	public static void main(String[] args) {
		ArrayList<Car> listOfCars = new ArrayList<Car>();
		for (int i = 0; i < 5; i ++) {
			listOfCars.add(new Car(2, 2, "Car"));
		}
		for (int i = 0; i < 5; i ++) {
			listOfCars.add(new RaceCar(2, 2, 2, "Car"));
		}
		
		for (Car car : listOfCars){
			car.run(5);
		}
		
		int max = 0;
		int max_car = 0;
		
		for (int i = 0; i < listOfCars.size(); i ++) {
			if (listOfCars.get(i).getDistanceTraveled() > max) {
				max_car = i;
			}
		}
		System.out.println("The car with the max distance is car " + max_car);
		
		max = 0;
		max_car = 0;
		for (int i = 0; i < listOfCars.size(); i ++) {
			if (listOfCars.get(i).getTotalCost() > max) {
				max_car = i;
			}
		}
		System.out.println("The car with the max cost is car " + max_car);
	}
	
	
}

/*
 * 1)Create a DriveCar class with main method and in main method:
 * 2)Create an Array of Cars of size 10.
 * 3)Create 5 Cars and 5 RaceCars using the parameterized constructors.
 * 4)Store the cars created in the Cars array.
 * 5)Run the cars for 5 seconds.
 * 6)Find the car that travelled the maximum distance.
 * 7)Find the car that has maximum operational cost.
 * */
