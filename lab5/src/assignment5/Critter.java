package assignment5;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <John Sigmon>
 * <js85773>
 * <15455>
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

// Per Piazza do not use import *
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Map;
import assignment5.CritterWorld;

/* 
 * See the PDF for descriptions of the methods and fields in this class you may
 * add fields, methods or inner classes to Critter ONLY if you make your 
 * additions private. No new public, protected or default-package code or data
 * can be added to Critter.
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new ArrayList<Critter>();
	private static List<Critter> babies = new ArrayList<Critter>();
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/*///////////////////
	//	  	Map		   //
	*********************
	* x012...  (width-1)*
	*y                  *
	*0 			        *
	*1      			*
	*2  				*
	*.   				*
	*.  				*
	*.    				*
	*(height-1)			*
	*********************/
	private int x_coord;
	private int y_coord;
	private int energy = 0;
	private static Random rand = new Random();
	private boolean hasMoved = false;

	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 

	/**
	 *  Gets the package name. This assumes that Critter and its subclasses 
	 *  are all in the same package.
	 */
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}	
	
	/**
	 * A one character long string that visually depicts your critter 
	 * in the ASCII interface .
	 *  
	*/
	public String toString() { 
		return ""; 
	}
	
	/**
	 * Walk in some direction.
	 * @param direction is an integer between 0 and 7 that is parsed.
	 */
	protected final void walk(int direction) {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		int x = this.x_coord;
		int y = this.y_coord;
		coords.add(x);
		coords.add(y);
		
		// If they are dead, kill them off. (This one just avoids
		// the parsing in case we call walk on an already dead 
		// critter.)
		if (hasMoved == false) {
			// First we remove the critter from the current position.
			removeFromMap(coords);
			
			// Then we get the critter's new coordinates and update their position.
			coords = parseDirection(direction);
			updateMap(coords);
		}
		
		// Set the critter's energy level no matter what.
		this.setEnergy(this.getEnergy() - Params.walk_energy_cost);
		
		// If they are dead, kill them off.
		if (dead()) {
			population.remove(population.indexOf(this));
			removeFromMap(coords);
		}
		else {
			hasMoved = true;
	
		// Check if the critter's new spot is already occupied.
			x = coords.get(0);
			y = coords.get(1);
			if (CritterWorld.virtual_map.get(x).get(y).size() == 1) {
				 return; //The critter is the only occupant.
			}
			else {
			// Add the current spot to conflict list to check later.
				CritterWorld.conflicts.add(coords);
			}
		}
	}

	protected final String look(int direction, boolean steps) {
		
		//deduct energy
		this.setEnergy(this.getEnergy() - Params.look_energy_cost);
		
		ArrayList<Integer> coords = this.parseDirection(direction);
		int x = coords.get(0);
		int y = coords.get(1);
		
		if (steps) {
			coords = CritterWorld.parseDirection(direction, x, y);
			x = coords.get(0);
			y = coords.get(1);			
		}
		//check if there is a critter at that spot in the map
		
		if(CritterWorld.old_map.get(x).get(y).isEmpty()) {
			return null;
		}
		else {
			return (CritterWorld.old_map.get(x).get(y).get(0).toString());
		}		
	}	

	/**
	 * Run just walks in some direction twice.
	 * @param direction is an integer between 0 and 7 that is parsed.
	 */
	protected final void run(int direction) {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		int x = this.x_coord;
		int y = this.y_coord;
		coords.add(x);
		coords.add(y);
		
		// If they are dead, kill them off. (This one just avoids
		// the parsing in case we call walk on an already dead 
		// critter.)
		if (hasMoved == false) {
			// First we remove the critter from the current position.
			removeFromMap(coords);
	
			// Then we get the critter's new coordinates and update their position.
			coords = parseDirection(direction);
			this.x_coord = coords.get(0);
			this.y_coord = coords.get(1);
			coords = parseDirection(direction);
			updateMap(coords);
		}
				
		// Set the critter's energy level.
		this.setEnergy(this.getEnergy() - Params.run_energy_cost);
				
		// If they are dead, kill them off.
		if (dead()) {
			population.remove(population.indexOf(this));
			removeFromMap(coords);
		}
		else {
			this.hasMoved = true;
				
			// Check if the critter's new spot is already occupied.
			x = coords.get(0);
			y = coords.get(1);
			if (CritterWorld.virtual_map.get(x).get(y).size() == 1) {
				return; //The critter is the only occupant.
			}
			else {
				// Add the current spot to conflict list to check later.
				CritterWorld.conflicts.add(coords);
			}
		}
	}
	
	/**																			//
	 * 	This method provides a way for the critters to reproduce.				//
	 * 																			//
	 * @param offspring: is created in a Critter's doTimeStep() and/or their 	//
	 * fight() method															//
	 * @param direction: the direction is also determined in a critter's 		//
	 * doTimeStep and/or fight() method.										//
	 */																			//
	protected final void reproduce(Critter offspring, int direction) {			//
		if (this.getEnergy() > Params.min_reproduce_energy) {					//
			// Assign child 1/2 the parent's energy (round down)				//
			offspring.setEnergy(this.getEnergy()/2); 							//
																				//
			// Assign parent 1/2 of its energy (round up)						//
			this.setEnergy((int)Math.ceil(this.getEnergy()/2.0));				//
																				//
			// Assign the child's position immediately adjacent to the parent 	//
			// in the specified direction.										//
			offspring.x_coord = this.x_coord;									//
			offspring.y_coord = this.y_coord;	
			//TODO what happens to the return of parse direction?//
			offspring.parseDirection(direction);
			CritterWorld.queueNewCritter(offspring);							//
		}																		//
	}																			//
																				//
	public abstract void doTimeStep();											//
	public abstract boolean fight(String oponent);								//
																				//
	/**				
	 * Create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass
	 * of Critter, if not, an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the
	 * parameter has lower case instead of upper. For example, if craig is 
	 * supplied instead of Craig, an error is thrown instead of an Exception.)
	 * 															
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) 					//
								throws InvalidCritterException {				//
		// Take the given class name and convert it								//
		// ex. craig -> Craig													//
		// ex. CrAiG -> Craig													//
		// ex. CRAIG -> Craig													//
		critter_class_name = critter_class_name.toLowerCase();					//
		String string = new String();											//
		char first = Character.toUpperCase(critter_class_name.charAt(0));		//
		string = myPackage + "." + first + critter_class_name.substring(1);		//
																				//
		List<String> classList = CritterWorld.getClassList(myPackage);			//
		
		List<String> lowerClassList = new ArrayList<String>();
		
		for(String str : classList) {
			lowerClassList.add(str.toLowerCase());
		}
		
		if(lowerClassList.contains(string.toLowerCase())){
			try {																	//
				// Get the class type or throw an exception							//
				int idx = lowerClassList.indexOf(string.toLowerCase());
				Class<?> newClass = Class.forName(classList.get(idx)); 				//
																					//
				Critter newCritter = null;
				try {
					newCritter = (Critter) newClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new ClassNotFoundException();
				} 
				
				// Set critter's initial energy
				newCritter.setEnergy(Params.start_energy);
				
				// Set critter's initial position at random
				newCritter.x_coord = TestCritter.getRandomInt(
						Params.world_width);
				newCritter.y_coord = TestCritter.getRandomInt(
						Params.world_height);
				
//				/*
//				 * Test
//				 */
//				System.out.println("(" + newCritter.x_coord + "," + newCritter.y_coord + ")");

				// Add new critter to the world
				CritterWorld.addCritter(newCritter, newCritter.x_coord, 
						newCritter.y_coord);	
						
			}			 
			catch( ClassNotFoundException e ) {
				 throw new InvalidCritterException(string);
			}
		}
		else {
			throw new InvalidCritterException(string);
		}
		
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed. 
	 * 			Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) 
									throws InvalidCritterException {
		List<Critter> result = new ArrayList<Critter>();
		
		String string = returnClassName(critter_class_name);	
																				//
		List<String> classList = CritterWorld.getClassList(myPackage);			//
		
		List<String> lowerClassList = new ArrayList<String>();
		
		for(String str : classList) {
			lowerClassList.add(str.toLowerCase());
		}
		
		if(lowerClassList.contains(string.toLowerCase())) {
			try {																//
				// Get the class type or throw an exception						//
				int idx = lowerClassList.indexOf(string.toLowerCase());
				Class<?> newClass = Class.forName(classList.get(idx)); 			//
																				//
				for(Critter crit : CritterWorld.getPopulation()) {
					if(string.equals(myPackage + "." + "Critter"))
					{
						result.add(crit);
					}
					else {
						if(crit.getClass().equals(newClass)) {
							result.add(crit);
						}
					}
				}
						
			}			 
			catch( ClassNotFoundException e ) {
				 throw new InvalidCritterException(string);
			}
		}
		else {
			throw new InvalidCritterException(string);
		}
		
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static String runStats(List<Critter> critters) {
		String stats = new String();
		stats += "" + critters.size() + " critters as follows -- ";
		Map<String, Integer> critter_count = new HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			stats += prefix + s + ":" + critter_count.get(s);
			prefix = ", ";
		}
		return stats;		
	}
	
	
	/* The TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this 
	 * class and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your 
	 * implementation of Critter. That means, if you're recording the positions
	 * of your critters using some sort of external grid or some other data
	 * structure in addition to the x_coord and y_coord functions, then you 
	 * MUST update these setter functions so that they correctly update your 
	 * grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/**
		 * This method getPopulation has to be modified by you if you are not
		 * using the population ArrayList that has been provided in the starter
		 * code. In any case, it has to be implemented for grading tests to 
		 * work.
		 * @return population is the Critter population.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/**
		 * This method getBabies has to be modified by you if you are not using
		 * the babies ArrayList that has been provided in the starter code. In 
		 * any case, it has to be implemented for grading tests to work. Babies
		 * should be added to the general population at either the beginning OR
		 * the end of every time step.
		 * 
		 * @return This method returns a list of new critters (babies).
		 */
		protected static List<Critter> getBabies() {
			@SuppressWarnings("unchecked")
			List<Critter> babies = (List<Critter>) CritterWorld.new_critters;
			return babies;
		}
	}
	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		CritterWorld.clearWorld();
		new CritterWorld();
	}
	
	/**
	 * Executes world time step. Note: add new critters must be performed here
	 * due to the pdf restrictions on public/private methods.
	 */
	public static void worldTimeStep() {
		CritterWorld.worldTimeStep();
		addNewCritters();
		resetHasMoved();
	}
	
	/**
	 * This resets all the 'hasMoved' flags in the critter population.
	 */
	private static void resetHasMoved() {
		for (int i = 0; i < population.size(); i ++ ) {
			population.get(i).hasMoved = false;
		}
	}

	/**
	 * Displays the world to stdout.
	 */
	public static void displayWorld() {
		//CritterWorld.displayWorld();
		new View();
	}
	
	///////////////////////////////////////////////////////
	// 				  Helper Methods					 //
	///////////////////////////////////////////////////////
	

	/**
	 * This method parses the direction command and updates the
	 * critter's location.
	 * @param direction is a random integer from 0 to 7.
	 * @return List of integers of the coordinates of the new location.
	 */
	private ArrayList<Integer> parseDirection(int direction) {
		int x = this.x_coord;
		int y = this.y_coord;
		int width = Params.world_width;
		int height = Params.world_height;
		
		/*////////////////////////////////////////
		//	  Directions   /////	  	Map		//
		 *****************************************
		 * 		  2         ** x012...  (width-1)*
		 *   3    |    1    **y                  *
		 *     \  |  /      **0 			     *
		 *      \ | /       **1 			     *
		 * 4 ----------- 0  **2 		         *
		 *      / | \       **. 		         *
		 *     /  |  \      **. 		         *
		 *   5    |    7    **. 		         *
		 *        6         **(height-1)         *
		 *****************************************
		 ///////////////////////////////////////*/
		
		switch (direction) {
			case 0:
				if (x < width - 1) {
					x += 1;
				}
				else {
					x = 0;
				}
				break;
			
			case 1:
				if (x < width - 1) {
					 x += 1;
				}
				else {
					x = 0;
				}
				if (y > 0) {
					y -= 1;
				}
				else {
					y = height - 1;
				}
				break;
			
			case 2:
				if (y > 0) {
					y -= 1;
				}
				else {
					y = height - 1;
				}
				break;
			
			case 3:
				if(y > 0) {
					y -= 1;
				}
				else {
					y = height - 1;
				}
				if (x > 0) {
					x -= 1;
				}
				else {
					x = width - 1;
				}
				break;
			
			case 4:
				if (x > 0) {
					x -= 1;
				}
				else {
					x = width - 1;
				}
				break;
			
			case 5:
				if (y < height - 1) {
					y += 1;
				}
				else {
					y = 0;
				}
				if (x < 0 ) {
					x -= 1;
				}
				else {
					x = width - 1;
				}
				break;
			
			case 6:
				if (y < height - 1) {
					y += 1;
				}
				else {
					y = 0;
				}
				break;
			
			case 7:
				if (y < height - 1) {
					y += 1;
				}
				else {
					y = 0;
				}
				if (x < width - 1) {
					x += 1;
				}
				else {
					x = 0;
				}
				break;
			
			default:
				System.out.println("I don't know how you reached this );"
						+ "error message.");
				System.out.println("You probably tried to choose an unsupported"
						+ "direction number.");
				System.out.println("Here is your stack trace:");
				new Exception().printStackTrace();
				System.exit(1);
				break;
		}
		
		// Return a tuple of coordinates so the calling method can update the
		// virtual map.
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(x);
		coords.add(y);
		return coords;
	}
	
	/**
	 * For readability, this function checks if critter is dead.
	 * @return true if dead, false if not dead
	 */
	private boolean dead() {
		if (this.getEnergy() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * This empties the queue of new critters and puts them in the world. They
	 * must be preassigned coordinates. Note: Conflicts are to be dealt with 
	 * at the end of the next time step, not the current one.
	 */
	private static void addNewCritters() {
		while (CritterWorld.new_critters.isEmpty() == false) {
			Critter head = CritterWorld.new_critters.poll();
			int x = head.x_coord;
			int y = head.y_coord;
			// Add them to the population, and the virtual map.
			population.add(head);
			CritterWorld.virtual_map.get(x).get(y).add(head);
			babies.remove(head);
		}
	}
	
	/**
	 *  Update the virtual map, given new x and y coordinates.
	 *  @param coords is the coordinates for critter
	 */
	private void updateMap(List<Integer> coords) {
		int x = coords.get(0);
		int y = coords.get(1);
		CritterWorld.virtual_map.get(x).get(y).add(this);
		x_coord = x;
		y_coord = y;
	}
	
	/**
	 * Removes a critter from the virtual map.
	 */
	private void removeFromMap(List<Integer> coords) {
		int x = coords.get(0);
		int y = coords.get(1);
		CritterWorld.virtual_map.get(x).get(y).remove(this);
	}
	
	/**
	 * 
	 * @param critter_class_name A Critter class name.
	 * @return string for the class name
	 */
	private static String returnClassName(String critter_class_name) {
		critter_class_name = critter_class_name.toLowerCase();					//
		String string = new String();											//
		char first = Character.toUpperCase(critter_class_name.charAt(0));		//
		string = myPackage + "." + first + critter_class_name.substring(1);	
		return string;
	}
	
	///////////////////////////////////////////////////////
	// 				Getters and setters					 //
	///////////////////////////////////////////////////////
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new Random(new_seed);
	}
	
	protected int getEnergy() { 
		return energy; 
	}
	
	protected void setEnergy(int energy) {
		this.energy=energy;
	}
	
}
