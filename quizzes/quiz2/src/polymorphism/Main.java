package polymorphism;

public class Main {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String args) {
		LinkedList<Car> cars = 
				new LinkedList<Car>();
		
		cars.insert(new LinkedListNodeClass(new Car()));
		cars.insert(new LinkedListNodeClass(new Car()));
		cars.printList();
	
	}
}
	
	
