package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.*;
import assignment4.CritterWorld;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new ArrayList<Critter>();
	private static List<Critter> babies = new ArrayList<Critter>();
	
	private int x_coord;
	private int y_coord;
	private int energy = 0;
	private static Random rand = new Random();
	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { 
		return ""; 
	}
	
	protected final void walk(int direction) {
		//TODO need to implement energy level and all that fun jazz
		int x = CritterWorld.locTable.get(this).getX();
		int y = CritterWorld.locTable.get(this).getY();
		
		switch (direction) {
			case 0:
				
				if(x < Params.world_width - 1) {
					x = x + 1;
				}
				CritterWorld.locTable.put(this, new Location(x,y));
				break;
			case 1:
				if(x < Params.world_width - 1) {
					x = x + 1;
				}
				if(y > 0) {
					y = y-1;
				}
				CritterWorld.locTable.put(this, new Location(x,y));
				break;
			case 2:
				if(y > 0) {
					y = y-1;
				}
				CritterWorld.locTable.put(this, new Location(x,y));
				break;
			case 3:
				if(x > 0) {
					x = x - 1;
				}
				if(y > 0) {
					y = y-1;
				}
				CritterWorld.locTable.put(this, new Location(x,y));
				break;
			case 4:
				if(x > 0) {
					x = x - 1;
				}
				CritterWorld.locTable.put(this, new Location(x,y));
				break;
			case 5:
				if(x > 0) {
					x = x - 1;
				}
				if(y < Params.world_height) {
					y = y + 1;
				}
				CritterWorld.locTable.put(this, new Location(x,y));
				break;
			case 6:
				if(y < Params.world_height) {
					y = y + 1;
				}
				CritterWorld.locTable.put(this, new Location(x,y));
				break;
			case 7:
				if(x < Params.world_width - 1) {
					x = x + 1;
				}
				if(y < Params.world_height) {
					y = y + 1;
				}
				CritterWorld.locTable.put(this, new Location(x,y));
				break;
			default:
				break;
		}
	}
	
	protected final void run(int direction) {
		//TODO check if functionality is correct
		walk(direction);
		walk(direction);
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		//TODO check if functionality is correct
		TestCritter.getBabies().add(offspring);
		int x = CritterWorld.locTable.get(this).getX();
		int y = CritterWorld.locTable.get(this).getY();
		Location loc = new Location(x,y, direction);
		CritterWorld.babyLocTable.put(offspring, loc);
		CritterWorld.locTable.put(offspring, new Location(x,y));
		
	}

	
	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		
		critter_class_name = critter_class_name.toLowerCase();
		String string = new String();
		
		char first = Character.toUpperCase(critter_class_name.charAt(0));
		string = first + critter_class_name.substring(1);
		
		List<String> classList = new ArrayList<String>();
		
		classList.add("Craig");
		classList.add("Algae");
		
		//TODO figure out why this generates a ClassNotFoundException
		try {
			if(classList.contains(string)) {  
				//I just noticed there are instructions for doing this in the lab document... nice
				Class.forName(string);
			}			
		} 
		catch( ClassNotFoundException e ) {
			 throw new InvalidCritterException(string);
		}
	
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new ArrayList<Critter>();
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
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
	
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
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
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
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
	
	public static void worldTimeStep() {
		CritterWorld.worldTimeStep();
	}
	
	public static void displayWorld() {
		CritterWorld.displayWorld();
	}
	
	
	
	/******************************************************/
	// Getters and setters
	/******************************************************/
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	protected int getEnergy() { 
		return energy; 
	}
	
	protected void setEnergy(int energy) {
		this.energy=energy;
	}

	/*
	 * We can't add private fields or private methods to critter per the instructions. The data structures in 
	 * CritterWorld and the Location class were my way of working around this. I hope you're ok with it.
	 */
//	private int getX() {
//		return this.x_coord;
//	}
//	
//	private void setX(int x) {
//		this.x_coord = x;
//	}
//	
//	private int getY() {
//		return this.y_coord;
//	}
//	
//	private void setY(int y) {
//
//		this.y_coord = y;
//	}
	
}
