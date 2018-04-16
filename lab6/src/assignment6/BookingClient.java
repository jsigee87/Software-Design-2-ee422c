// John Sigmon
// 4/16/18
package assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Thread;

public class BookingClient {
	private Map<String, Integer> office;
	private Theater theater;
	

	
	
	/*
	 * @param office maps box office id to number of customers in line
	 * @param theater the theater where the show is playing
	 */
	public BookingClient(Map<String, Integer> office, Theater theater) {
	  this.office = office;
	  this.theater = theater;
	}
	
	/*
	 * Starts the box office simulation by creating (and starting) threads
	 * for each box office to sell tickets for the given theater
	 *
	 * @return list of threads used in the simulation,
	 *         should have as many threads as there are box offices
	 */
	public List<Thread> simulate() {
		List<Thread> threads = new ArrayList<>();
		
		for (Map.Entry<String, Integer> off : office.entrySet()) {
			BoxOffice box_office = new BoxOffice(off.getKey(), 
					off.getValue(), this);
			threads.add(box_office);
		}
		
		for (int i = 0; i < threads.size(); i ++) {
			threads.get(i).start();
		}
		
		return threads;
	}

	/**
	 *  @return theater returns the theater
	 */
	public Theater getTheater() {
		return this.theater;
	}
	
	/**
	 * 
	 * @return office returns the office
	 */
	public Map<String, Integer> getOffice(){
		return this.office;
	}
	
	public static void main(String [] args) {
		int num_rows = 3;
		int seats_per_row = 5;
		Theater th = new Theater(num_rows, seats_per_row, "Ouija");
		Map<String,Integer> off = new HashMap<>();
		off.put("BX1", 3);
		off.put("BX3", 3);
		off.put("BX2", 4);
		off.put("BX5", 3);
		off.put("BX4", 3);
		BookingClient BC = new BookingClient(off, th);
		BC.simulate();
	}
	
}
