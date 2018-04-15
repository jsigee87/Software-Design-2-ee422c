package assignment6;

import java.util.HashMap;
import java.util.Map;

public class Main {
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
