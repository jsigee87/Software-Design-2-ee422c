package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <John Sigmon>
 * <js85773>
 * <.......>
 * <Daniel Diamont> //TODO
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */


import java.util.*;
import assignment4.CritterWorld;

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
	
	/*///////////////////
	//	  	Map		   //
	*********************
	* y012...  (width-1)*
	*x                  *
	*0 			        *
	*1      			*
	*2  				*
	*.   				*
	*.  				*
	*.    				*
	*(height-1)			*
	*********************
	///////////////////////////////////////////////////////////////////////////
	/ We use the x and y as matrix accessors. i.e. x ~ i, y ~ j such that	  /
	/ the map is thought of as a matrix. You then access the ith row and jth  /
	/ column via A[i][j]. The edge of the map is then width - 1 on the right  /
	/ hand side, height - 1 on the bottom side, and 0 on the other two sides. /
	/////////////////////////////////////////////////////////////////////////*/
	private int x_coord;
	private int y_coord;
	private int energy = 0;
	private static Random rand = new Random();
	private boolean hasMoved;
	
	/*
	 *  Gets the package name. This assumes that Critter and its subclasses 
	 *  are all in the same package.
	 */
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}	
	
	/*
	 * A one-character long string that visually depicts your critter 
	 * in the ASCII interface .
	 *  
	*/
	public String toString() { 
		return ""; 
	}
	
	/**
	 * Walk in some direction.
	 * @param direction is an int between 0 and 7 that is parsed.
	 */
	protected final void walk(int direction) {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		int x = this.x_coord;
		int y = this.y_coord;
		coords.add(x);
		coords.add(y);
		
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
	
	/**
	 * Run just walks in some direction twice.
	 * @param direction is an int between 0 and 7 that is parsed.
	 */
	protected final void run(int direction) {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		int x = this.x_coord;
		int y = this.y_coord;
		coords.add(x);
		coords.add(y);
		
		if (hasMoved == false) {
			// First we remove the critter from the current position.
			removeFromMap(coords);
	
			// Then we get the critter's new coordinates and update their position.
			coords = parseDirection(direction);
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
	
	/**
	 * 
	 * @param offspring: is created in a Critter's doTimeStep() and/or their fight() method
	 * @param direction: the direction is also determined in a critter's doTimeStep and/or fight() method.
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.getEnergy() > Params.min_reproduce_energy) {
			
//			offspring.x_coord = rand.nextInt(Params.world_height);
//			offspring.y_coord = rand.nextInt(Params.world_width);
//			CritterWorld.queueNewCritter(offspring);		
			
			//Changes made per the document
			
			//Assign child 1/2 the parent's energy (round down)
			offspring.setEnergy(this.getEnergy()/2); // an int divided by an int rounds down
			
			//Assign parent 1/2 of its energy (round up)
			// taking the ceiling of energy/2.0 and casting to an int will round up
			this.setEnergy((int)Math.ceil(this.getEnergy()/2.0));
			
			//Assign the child's position immediately adjacent to the parent in the specified direction.
			offspring.x_coord = this.x_coord;
			offspring.y_coord = this.y_coord;
			offspring.parseDirection(direction);
			CritterWorld.queueNewCritter(offspring);
		}
	}

	
	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass 
	 * of Critter, if not, an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the 
	 * parameter has lower-case instead of upper. For example, if craig is 
	 * supplied instead of Craig, an error is thrown instead of an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	@SuppressWarnings("deprecation")
	public static void makeCritter(String critter_class_name) 
								throws InvalidCritterException {
		// Take the given class name and convert it
		// ex. craig -> Craig
		// ex. CrAiG -> Craig
		// ex. CRAIG -> Craig
		critter_class_name = critter_class_name.toLowerCase();
		String string = new String();
		char first = Character.toUpperCase(critter_class_name.charAt(0));
		string = myPackage + "." + first + critter_class_name.substring(1);
		
//		List<String> classList = new ArrayList<String>();
//		classList.add("Craig");
//		classList.add("Algae");
		
		List<Class> classList = CritterWorld.getClassesForPackage(myPackage);
		
		try {
			
			Class<?> newClass = Class.forName(string); //get the class type or throw an exception
			
			if(classList.contains(newClass)) {  
				
				Critter newCritter = null;
				try {
					newCritter = (Critter) newClass.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//set critter's initial energy
				newCritter.setEnergy(Params.start_energy);
				
				//set critter's initial position at random
				newCritter.x_coord = TestCritter.getRandomInt(Params.world_height);
				newCritter.y_coord = TestCritter.getRandomInt(Params.world_width);
				
				//add new critter to the world
				CritterWorld.queueNewCritter(newCritter);				
			}
			else {
				throw new ClassNotFoundException();
			}
					
		}			 
		catch( ClassNotFoundException e ) {
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
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
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
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
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
	 *///TODO - make sure all this stuff works with our critter world
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
		

		/*
		 * This method getPopulation has to be modified by you if you are not
		 * using the population ArrayList that has been provided in the starter
		 * code. In any case, it has to be implemented for grading tests to 
		 * work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using
		 * the babies ArrayList that has been provided in the starter code. In 
		 * any case, it has to be implemented for grading tests to work. Babies
		 * should be added to the general population at either the beginning OR
		 * the end of every time step.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		CritterWorld.clearWorld();
	}
	/*
	 * Executes world time step. Note: add new critters must be performed here.
	 */
	public static void worldTimeStep() {
		CritterWorld.worldTimeStep();
		addNewCritters();
	}
	
	/*
	 * Displays the world.
	 */
	public static void displayWorld() {
		CritterWorld.displayWorld();
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
		 * 		  2         ** y012...  (width-1)*
		 *   3    |    1    **x                  *
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
				if (y < width - 1) {
					this.y_coord = y + 1;
				}
				else {
					this.y_coord = 0;
				}
				break;
			
			case 1:
				if (y < width - 1) {
					this.y_coord = y + 1;
				}
				else {
					this.y_coord = 0;
				}
				if (x > 0) {
					this.x_coord = x - 1;
				}
				else {
					this.x_coord = height - 1;
				}
				break;
			
			case 2:
				if (x > 0) {
					this.x_coord = x - 1;
				}
				else {
					this.x_coord = height - 1;
				}
				break;
			
			case 3:
				if(x > 0) {
					this.x_coord = x - 1;
				}
				else {
					this.x_coord = height - 1;
				}
				if (y > 0) {
					this.y_coord = y - 1;
				}
				else {
					this.y_coord = width - 1;
				}
				break;
			
			case 4:
				if (y > 0) {
					this.y_coord = y - 1;
				}
				else {
					this.y_coord = width - 1;
				}
				break;
			
			case 5:
				if (x < height - 1) {
					this.x_coord = x + 1;
				}
				else {
					this.x_coord = 0;
				}
				if (y < 0 ) {
					this.y_coord = y - 1;
				}
				else {
					this.y_coord = width - 1;
				}
				break;
			
			case 6:
				if (x < height - 1) {
					this.x_coord = x + 1;
				}
				else {
					this.x_coord = 0;
				}
				break;
			
			case 7:
				if (x < height - 1) {
					this.x_coord = x + 1;
				}
				else {
					this.x_coord = 0;
				}
				if (y < width - 1) {
					this.y_coord = y + 1;
				}
				else {
					this.y_coord = 0;
				}
				break;
			
			default:
				// TODO should this throw an error? assertion? exception?
				break;
		}
		
		// Return a tuple of coordinates so the calling method can update the
		// virtual map.
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(this.x_coord);
		coords.add(this.y_coord);
		return coords;
	}
	
	/*
	 * For readability, this function checks if critter is dead.
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
	}
	
	/*
	 * Removes a critter from the virtual map.
	 */
	private void removeFromMap(List<Integer> coords) {
		int x = coords.get(0);
		int y = coords.get(1);
		CritterWorld.virtual_map.get(x).get(y).remove(this);
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
