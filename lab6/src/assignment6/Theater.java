// insert header here
package assignment6;

import java.util.List;

public class Theater {
	/*
	 * Represents a seat in the theater
	 * A1, A2, A3, ... B1, B2, B3 ...
	 */
	static class Seat {
		private int rowNum;
		private int seatNum;

		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
		}

		public int getSeatNum() {
			return seatNum;
		}

		public int getRowNum() {
			return rowNum;
		}

		@Override
		public String toString() {
			// Convert row number to String by calculating number
			// of A's needed, then adding the appropriate ending letter
			String row = "";
			int len_row = this.getRowNum() % 26;
			for (int i = 0; i < len_row - 1; i ++) {
				row = row + "A";
			}
			row = row + String.valueOf((char)(this.getRowNum()/26 + 64));
			
			// Add the integer value on the end
			return row + String.valueOf(this.getSeatNum());
		}
	}

  /*
	 * Represents a ticket purchased by a client
	 */
	static class Ticket {
		private String show;
		private String boxOfficeId;
		private Seat seat;
	    private int client;

		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			this.client = client;
		}

		public Seat getSeat() {
			return seat;
		}

		public String getShow() {
			return show;
		}

		public String getBoxOfficeId() {
			return boxOfficeId;
		}

		public int getClient() {
			return client;
		}

		@Override
		public String toString() {
			String ticket = new String();
			ticket = "";
			
			// Line 1
			ticket += "--------------------------------\n";
			
			// Line 2
			ticket += "| Show:  " + this.getShow();
			for (int i = 0; i < 22 - this.getShow().length(); i ++) {
				ticket += " ";
			}
			ticket += "|";
			
			// Line 3
			ticket += "| Box Office ID:  " + this.getBoxOfficeId();
			for (int i = 0; i < 14 - this.getBoxOfficeId().length(); i ++) {
				ticket += " ";
			}
			ticket += "|";
			
			// Line 4
			ticket += "| Seat:  " + this.getSeat();
			for (int i = 0; i < 22 - this.getSeat().toString().length(); i ++) {
				ticket += " ";
			}
			ticket += "|";
			
			//Line 5
			ticket += "| Client:  " + this.getClient();
			for (int i = 0; i < 20 - this.getClient(); i ++) {
				ticket += " ";
			}
			ticket += "|";
			
			// Line 6
			ticket += "--------------------------------";
			
			return ticket;
		}
	}

	public Theater(int numRows, int seatsPerRow, String show) {
		// TODO: Implement this constructor
	}

	/*
	 * Calculates the best seat not yet reserved
	 *
 	 * @return the best seat or null if theater is full
   */
	public Seat bestAvailableSeat() {
		//TODO: Implement this method
	}

	/*
	 * Prints a ticket for the client after they reserve a seat
   * Also prints the ticket to the console
	 *
   * @param seat a particular seat in the theater
   * @return a ticket or null if a box office failed to reserve the seat
   */
	public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		//TODO: Implement this method
	}

	/*
	 * Lists all tickets sold for this theater in order of purchase
	 *
   * @return list of tickets sold
   */
	public List<Ticket> getTransactionLog() {
		//TODO: Implement this method
	}
}
