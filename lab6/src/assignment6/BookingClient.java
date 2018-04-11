// Insert header here
package assignment6;

import java.util.ArrayList;
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
	  // TODO: Implement this constructor
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
		// do stuff
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
	
	
	
}
