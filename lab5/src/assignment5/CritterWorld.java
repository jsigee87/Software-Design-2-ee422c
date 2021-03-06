package assignment5;

// Per Piazza, do not use import*
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import assignment5.Critter.TestCritter;
// This is the model

public class CritterWorld extends TestCritter{

	private static boolean shouldQuit;
	/**
	 * Virtual map. Holds the index of the critter that is to be displayed when
	 *  displayWorld() is called. 'x' is a list, 'y' is a list in 'x', a member
	 *  of 'y' is a list of critters.
	 *
	 *  Ex. Given x and y, to access the critters in that location, first check
	 *  that virtual_map.get(x).get(y).isEmpty() is false. Then you can simply
	 *  call virtual_map.get(x).get(y).get(i) to get the ith critter in that 
	 *  spot. Calling for a third critter when there is only one will give
	 *  an out of bounds exception.
	 */
	protected static ArrayList<ArrayList<ArrayList<Critter>>> virtual_map = 
			new ArrayList<ArrayList<ArrayList<Critter>>>();
	
	/**
	 * 
	 */
	protected static ArrayList<ArrayList<ArrayList<Critter>>> old_map = 
			new ArrayList<ArrayList<ArrayList<Critter>>>();
	
	/**
	 * Holds offspring until the end of the turn when they get added to the
	 * map. It is assumed that they already have their coordinates set.
	 */
	protected static Queue<Critter> new_critters = new LinkedList<Critter>();
	
	/**
	 * When a _possible_ conflict is detected, the coordinates are added to 
	 * this queue. At the end of the turn they are all checked and resolved.
	 * 
	 */
	protected static Queue<ArrayList<Integer>> conflicts = 
			new LinkedList<ArrayList<Integer>>();

	/**
	 * Constructor. Called at the beginning of the game. Creates a default 
	 * world using the parameters located in Params, and initializes the 
	 * virtual map.
	 */
	public CritterWorld() {
		shouldQuit = false;
		// Populate the virtual map.
		for(int i = 0; i < Params.world_width; i++) {
			ArrayList<ArrayList<Critter>> outer_list = 
					new ArrayList<ArrayList<Critter>>();
			for (int j = 0; j < Params.world_height; j ++) {
				ArrayList<Critter> inner_list = new ArrayList<Critter>();
				outer_list.add(inner_list);
			}
			virtual_map.add(outer_list);
		}
		
		for(int i = 0; i < Params.world_width; i++) {
			ArrayList<ArrayList<Critter>> outer_list = 
					new ArrayList<ArrayList<Critter>>();
			for (int j = 0; j < Params.world_height; j ++) {
				ArrayList<Critter> inner_list = new ArrayList<Critter>();
				outer_list.add(inner_list);
			}
			old_map.add(outer_list);
		}
	}
	
	/**
	 * This method takes a critter and the location to where it will be 
	 * inserted.
	 * 
	 * This method assumes the critter's x and y coordinate are already set.
	 * Sets the initial energy level to the level specified in Params.
	 * 
	 * @param critter is the Critter to be inserted.
	 * @param x is the critter's x coordinate.
	 * @param y is the critter's y coordinate.
	 */
	public static void addCritter(Critter critter, int x, int y) {
		critter.setEnergy(Params.start_energy);
		TestCritter.getPopulation().add(critter);
		virtual_map.get(x).get(y).add(critter);
	}
	
	/**
	 * clearWorld resets the baby, population, and conflict lists, and clears the virtual map
	 * 
	 */
	public static void clearWorld() {
		TestCritter.getPopulation().clear();
		virtual_map.clear();
		old_map.clear();
		new_critters.clear();
		conflicts.clear();
	}
	
	/**
	 * Perform a world time step.
	 */
	@SuppressWarnings("unchecked")
	public static void worldTimeStep() {	
		// Copy current virtual map into "old map"
		old_map = (ArrayList<ArrayList<ArrayList<Critter>>>) virtual_map.clone();
		
		// Time step all the critters.
		for (int i = 0; i < getPopulation().size(); i++) {
			getPopulation().get(i).doTimeStep();
		}
		
		old_map = virtual_map;
		
		// Resolve conflicts.
		resolveConflicts();
		
		// Update rest energy.
		updateRestEnergy();
		
		// Generate algae.
		genAlgae();
		
		// Add new critters to the map is done inside Critter.
	}
	
	/**
	 * Display the world on the console. DEBUG mode
	 */
	public static void displayWorld() {		
		printEdge();
		printBody();
		printEdge();
	}
	
	/**
	 * Display the world on the console. DEBUG mode
	 */
	public static void displayWorldDebug() {		
		ArrayList<ArrayList<Integer>> johnlocations = new ArrayList<ArrayList<Integer>>();
		printEdge();
		johnlocations = printBodyDebug(johnlocations);
		printEdge();
		System.out.println();
		System.out.println("The coordinates were the display thinks a johncritter is are:");
		for (int i = 0; i < johnlocations.size(); i ++){
			System.out.println(johnlocations.get(i));
		}
	}
	
	/**
	 * This method is called at the end of each world time step. It 
	 * empties out the list of potential conflicts
	 */
	public static void resolveConflicts() {

		//resolve conflicts if they exist
		while(!conflicts.isEmpty()) { //we have potential conflicts to resolve
			ArrayList<Integer> coords = conflicts.poll(); 
			//remove coordinate with a potential conflict from the Queue
			
			int x = coords.get(0);
			int y = coords.get(1);
			
			ArrayList<Critter> list = new ArrayList<Critter>();
			// If there is still a conflict in this spot
			if (virtual_map.get(x).get(y).size() > 1) {
				try {
					 list = virtual_map.get(x).get(y);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.print("You have tried to access the virtual map");
					System.out.print(" out of bounds.\n");
					System.out.println("x is:\t" + x);
					System.out.println("y is:\t" + y);
					System.out.println("Here is the stack trace:");
					e.printStackTrace();
					System.exit(1);
				}
			}
			
			//while there is more than one critter on that coordinate
			while(list.size() > 1) {
				
				//do choose two critters A and B
				Critter A = list.get(0);
				Critter B = list.get(1);
				
				//fight or flight flags (true == fight; false == run away)
				boolean fightA = A.fight(B.toString());
				boolean fightB = B.fight(A.toString());
				
				boolean ran_away;
				
				//if there is still a conflict (AKA neither invoked run before setting the
				//fight or flight flags
				if(virtual_map.get(x).get(y).size() > 1) {
					if (fightA == false) {
						ran_away = tryToRunAway(A, x, y);
						if (ran_away == true) {
							list.remove(A);
						}
					}
					
					if (dead(A)) {
						list.remove(A);
					}
					
					if (fightB == false) {
						ran_away = tryToRunAway(B, x, y);
						if (ran_away == true) {
							list.remove(B);
						}
					}
					
					if (dead(B)) {
						list.remove(B);
					}
					
					if (!dead(A) && !dead(B)) {
						// If both are in the same position and are still alive
						if (list.contains(A) && list.contains(B)) {
							
							int rollA;
							int rollB;
							
							// Assumes bounds are inclusive
							if(fightA) { //if A elected to fight
								rollA = getRandomInt(A.getEnergy() + 1);
							}
							else {
							rollA = 0;
							}
							if(fightB) { //if B elected to fight
								rollB = getRandomInt(B.getEnergy() + 1);
							}
							else {
								rollB = 0;
							}
							
							// Determine winner
							Critter winner;
							Critter loser;
						
							// A wins all ties
							if(rollA >= rollB) {
								winner = A;
								loser = B;
							}
							else {
								winner = B;
								loser = A;
							}
							
							// Award winner his energy bonus from winning the fight
							int energy_to_add = loser.getEnergy()/2;
							winner.setEnergy(winner.getEnergy() + energy_to_add);
							
							// Set loser's energy to a negative number so it can be removed
							loser.setEnergy(-1);
							try {
								loser.walk(0);
							}
							catch (ArrayIndexOutOfBoundsException e) {
								System.out.print("You have tried to access the virtual map");
								System.out.print(" out of bound while killing a critter.\n");
								System.out.println("x is:\t" + x);
								System.out.println("y is:\t" + y);
								System.out.println("Here is the stack trace:");
								e.printStackTrace();
							System.exit(1);
							}
						}
					}
				}
				
			}	
		}
	}	
	
	/**
	 * Queue a new critter to be added to the world at the end of the time
	 * step.
	 * @param new_critter Critter to be added to the babies list for addition to the 
	 * map later.
	 */
	public static void queueNewCritter(Critter new_critter) {
		new_critters.add(new_critter);
		TestCritter.getBabies().add(new_critter);
	}
	
	/**
	 * Update rest energy for all critters. Reset hasMoved flag.
	 */
	private static void updateRestEnergy() {
		for (int i = 0; i < getPopulation().size(); i ++) {
			int curr_energy = getPopulation().get(i).getEnergy();
			getPopulation().get(i).setEnergy(curr_energy - 
					Params.rest_energy_cost);
			if (getPopulation().get(i).getEnergy() <= 0) {
				// This will kill the critter inside Critter.java, which
				// will remove it from the virtual map.
				getPopulation().get(i).walk(0);
				//getPopulation().remove(i);	// done within walk.
			}
		}
	}

	
	/**
	 * Generate algae at the end of every turn as specified by params.
	 */
	protected static void genAlgae() {
		for (int i = 0; i < Params.refresh_algae_count; i ++) {
			Algae alg = new Algae();
			int x = getRandomInt(Params.world_width);
			int y = getRandomInt(Params.world_height);
			alg.setX_coord(x);
			alg.setY_coord(y);
			addCritter(alg, x, y);	
		}
	}
	
	/**
	 * Prints the map edge.
	 */
	public static void printEdge() {
		System.out.print('+');
		for (int i = 0; i < Params.world_width; i ++) {
			System.out.print('-');
		}
		System.out.print('+');
		System.out.println();
	}
	
	/**
	 * Prints the map body via the virtual map.
	 * 
	 */
	public static void printBody() {
		int i = 0;
		int j = 0;
		
		for (j = 0; j < Params.world_height; j++) {
			System.out.print("|");
			for(i = 0; i < Params.world_width; i++) {
				
				//if the spot (i,j) is occupied
				if(virtual_map.get(i).get(j).isEmpty() == false) {
					//print out the critter
					System.out.print(virtual_map.get(i).get(j).get(0));					
				}
				else {
					System.out.print(" "); //print an empty space
				}
			}
			System.out.println("|");
		}
	}
	
	/**
	 * Prints the map body via the virtual map. DEBUG
	 * Use this to make johncritters (Critter2) and display their locations.
	 * @return  debug lists
	 */
	public static ArrayList<ArrayList<Integer>> printBodyDebug(ArrayList<ArrayList<Integer>> johnlocations) {
		int i = 0;
		int j = 0;
		
		for (i = 0; i < Params.world_width; i++) {
			System.out.print("|");
			for(j = 0; j < Params.world_height; j++) {
				
				//if the spot (i,j) is occupied
				if(virtual_map.get(i).get(j).isEmpty() == false) {
					ArrayList<Integer> loc = new ArrayList<Integer>();
					loc.add(i);
					loc.add(j);
					johnlocations.add(loc);
				
					//print out the critter
					System.out.print(virtual_map.get(i).get(j).get(0));					
				}
				else {
					System.out.print(" "); //print an empty space
				}
			}
			System.out.println("|");
		}
		return johnlocations;
	}
	
	/**
	 * Accesses the quit game flag.
	 * @return boolean for should quit
	 */
	public static boolean shouldQuit() {
		return shouldQuit;
	}
	
	/**
	 * Sets the quit game flag to true.
	 */
	protected static void quit() {
		shouldQuit = true;
	}

	@Override
	public void doTimeStep() {
		//not used
	}

	@Override
	public boolean fight(String oponent) {
		// not used
		return false;
	}
	
	/**
	 * For readability, this function checks if critter is dead.
	 * @return true if critter is dead, false if alive.
	 */
	public static boolean dead(Critter critter) {
		if (critter.getEnergy() <= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method gets the classes inside a package and returns in a list
	 * all of the files located inside that package.
	 * @param myPackage pkg is assignment4
	 * @return list of all .class files located inside the package
	 */
	@SuppressWarnings({ "unused" })
	public static List<String> getClassList(String myPackage) {

	    List<String> classList = new ArrayList<String>();

	    // Get a File object for the package
	    File directory = null;
	    String fullPath;
	    String relativePath = myPackage.replace('.', '/');

	    URL resource = ClassLoader.getSystemClassLoader().getResource(relativePath);
	    fullPath = resource.getFile();
	    try {
			directory = new File(resource.toURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

	    String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {

            // We are only interested in .class files
            if (files[i].endsWith(".class")) {

                // Removes the .class extension
                String name = myPackage + '.' + files[i].substring(0, files[i].length() - 6);
                
                classList.add(name);
            }
        }
        
	    return classList;
	}

	/**
	 *  Used in resolving conflicts. Calling walk will update the virtual map
	 *  and kill off the critter if needed. Returns a boolean for success.
	 * @param c Critter trying to run away
	 * @param x Critter's x location
	 * @param y Critter's y location
	 * @return boolean whether running was successful
	 */
	public static boolean tryToRunAway(Critter c, int x, int y) {
		ArrayList<Integer> new_coords = new ArrayList<Integer>();
		for (int i = 0; i < 8; i ++) {
			new_coords = parseDirection(i, x, y);
			int x_new = new_coords.get(0);
			int y_new = new_coords.get(1);
			// If the spot is empty, then go there
			if (virtual_map.get(x_new).get(y_new).size() == 0) {
				c.run(i);
				return true;
			}
//			else {
//				c.setEnergy(c.getEnergy() - Params.run_energy_cost);
//			}
		}
		return false;
	}
	
	/**
	 * This method parses the direction command and updates the
	 * critter's location.
	 * @param direction is a random integer from 0 to 7.
	 * @return List of integers of the coordinates of the new location.
	 */
	protected static ArrayList<Integer> parseDirection(int direction, int x, int y) {
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

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}
}
	
