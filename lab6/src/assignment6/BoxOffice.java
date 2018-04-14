package assignment6;

public class BoxOffice extends Thread{
	private String id;
	private int num_customers;
	private int first_customer;
	private BookingClient client;
	private Theater theater;
	
	
	
	public BoxOffice(String key, Integer value, BookingClient bookingClient) {
		this.id = key;
		this.num_customers = value;
		this.client = bookingClient;
		this.theater = bookingClient.getTheater();
		this.first_customer = bookingClient.getTheater().getNext(value);
	}



	public void run(){
		
	}
}
