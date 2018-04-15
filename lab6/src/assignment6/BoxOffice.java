package assignment6;

public class BoxOffice extends Thread{
	private String id;
	private int num_customers;
	private int customer;
	private BookingClient client;
	private Theater theater;
	
	
	
	public BoxOffice(String key, Integer value, BookingClient bookingClient) {
		this.id = key;
		this.num_customers = value;
		this.client = bookingClient;
		this.theater = bookingClient.getTheater();
		this.customer = bookingClient.getTheater().getNextId(value);
	}


	@Override
	public void run(){
		int i = num_customers;
		
		while (i > 0) {
			synchronized (client) {
				Theater.Seat new_seat = theater.bestAvailableSeat();
				if (theater.printTicket(id, new_seat, customer) == null) {
					break;
				}
			}
			try {
				sleep(50);
			}
			catch (InterruptedException e) {}
			i --;
		}
	}
}
