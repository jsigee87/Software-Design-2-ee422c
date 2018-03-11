package assignment4;

import java.util.*;
import assignment4.Critter.TestCritter;
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
	 * Holds offspring until the end of the turn when they get added to the
	 * map. It is assumed that they already have their coordinates set.
	 */
	protected static Queue<Critter> new_critters = new LinkedList<Critter>();
	
	/**
	 * When a _possible_ conflict is detected, the coordinates are added to 
	 * this queue. At the end of the turn they are all checked and resolved.
	 * 
	 */
	protected static Queue<ArrayList<Integer>> conflicts = new LinkedList<ArrayList<Integer>>();

	/**
	 * Constructor. Called at the beginning of the game. Creates a default 
	 * world using the parameters located in Params, and initializes the 
	 * virtual map.
	 */
	public CritterWorld() {
		shouldQuit = false;
		// Populate the virtual map.
		for(int i = 0; i < Params.world_height; i++) {
			ArrayList<ArrayList<Critter>> outer_list = new ArrayList<ArrayList<Critter>>();
			for (int j = 0; j < Params.world_width; j ++) {
				ArrayList<Critter> inner_list = new ArrayList<Critter>();
				outer_list.add(inner_list);
			}
			virtual_map.add(outer_list);
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
	 * clearWorld replaces all the integers in the virtualMap to -1. This should be called every time
	 * before displaying the world.
	 * 
	 */
	public static void clearWorld() {
		//TODO Daniel I commented this out- I am not sure what clearWorld is 
		//supposed to do, i asked on piazza to confirm
		//for (int i = 0; i < virtualMap.size(); i++) {
		//	for(int j = 0; j < virtualMap.get(i).size(); j++) {
		//		virtualMap.get(i).set(j, -1);
		//	}
		//}
	}
	
	/*
	 * Perform a world time step.
	 */
	public static void worldTimeStep() {				
		// Time step all the critters.
		for (int i = 0; i < getPopulation().size(); i++) {
			getPopulation().get(i).doTimeStep();
		}
		
		// Resolve conflicts.
		resolveConflicts();
		
		// Update rest energy.
		updateRestEnergy();
		
		// Generate algae.
		genAlgae();
		
		// Add new critters to the map is done inside Critter.
	}
	
	/*
	 * Display the world on the console.
	 */
	public static void displayWorld() {		
		printEdge();
		printBody();
		printEdge();
	}
	
	
	public static void resolveConflicts() {
		//TODO Conflict Resolution only - garbage collection is dynamic
		// empty all coordinates from conflicts queue.
		// algae must fight also
	}	
	
	/*
	 * Queue a new critter to be added to the world at the end of the time
	 * step.
	 */
	public static void queueNewCritter(Critter new_critter) {
		new_critters.add(new_critter);
	}
	
	/*
	 * Update rest energy for all critters.
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

	
	/*
	 * 
	 */
	protected static void genAlgae() {
		for (int i = 0; i < Params.refresh_algae_count; i ++) {
			Algae alg = new Algae();
			int x = getRandomInt(Params.world_height);
			int y = getRandomInt(Params.world_width);
			alg.setX_coord(x);
			alg.setY_coord(y);
			addCritter(alg, x, y);
			
		}
	}
	
	/*
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
	
	/*
	 * Prints the map body via the virtual map.
	 */
	public static void printBody() {
		int i = 0;
		int j = 0;
		
		for (i = 0; i < Params.world_height; i++) {
			System.out.print("|");
			for(j = 0; j < Params.world_width; j++) {
				
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
	
	/*
	 * Accesses the quit game flag.
	 */
	public static boolean shouldQuit() {
		return shouldQuit;
	}
	
	/*
	 * Sets the quit game flag to true.
	 */
	protected static void quit() {
		shouldQuit = true;
	}


	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		return false;
	}
	
/***************************************************/
	
		// Dead Code //

/***************************************************/
	
	/* Daniel I'm not clear on what update Map is supposed to do. Critter 
	 locations should be updated dynamically.
	public static void updateMap() {
		
		clearWorld(); //clear the world
		
		// This shouldnt be done here
		//if babies spawned during the last round, add the babies to the CritterWorld
		//if(TestCritter.getBabies().size() > 0) {
		//	addBabies(); //add
		//	emptyBabyList(); //clear the list of babies for the next round
		//}
		
		//update the virtual map with all of the critters
		Set<Critter> keySet = locTable.keySet();
		
		for(Critter critter : keySet) {
			int x = locTable.get(critter).getX();
			int y = locTable.get(critter).getY();
			
			virtualMap.get(x).set(y, TestCritter.getPopulation().indexOf(critter));
		}
	}
	*/
	
	/*****************************
	 * I think these should be implemented in Critter.java 
	 *
	 * add babies to the Critter World
	 
	public static void addBabies() {
		for(Critter baby : TestCritter.getBabies()) {
			
			baby.walk(babyLocTable.get(baby).getDir());
			int x = babyLocTable.get(baby).getX();
			int y = babyLocTable.get(baby).getY();
			
			addCritter(baby,x,y);
		}
	}
	
	/*
	 * Clear the list of babies
	 
	public static void emptyBabyList(){
		babyLocTable.clear();
		TestCritter.getBabies().clear();;
	}
	*************************************/
	
	
	
	
	
	
	
	
	
	
	
	//setting up bitmap of critters
	
	//TODO any way to make this better?
//	ArrayList<ArrayList<Integer>> virtual_map = new ArrayList<ArrayList<Integer>>();
//	
//	for(int k = 0; k < Params.world_height; k++) {
//		ArrayList<Integer> list  = new ArrayList<Integer>(Collections.nCopies(Params.world_width, -1));
//		virtual_map.add(list);
//	}
//
//	for (Critter critter : TestCritter.getPopulation()) {
//		//TODO: check if dead
//		int x = critter.getX();
//		int y = critter.getY();
//		virtual_map.get(x).set(y, TestCritter.getPopulation().indexOf(critter));
//	}
//	
//	for (i = 0; i < Params.world_height; i++) {
//		System.out.print("|");
//		for(j = 0; j < Params.world_width; j++) {
//			if (!virtual_map.get(i).get(j).equals(-1)) {
//				//TODO print out what the critter looks like
//				System.out.print(TestCritter.getPopulation().get(virtual_map.get(i).get(j)).toString());
//			}
//			else {
//				System.out.print(" "); //print an empty space
//			}
//			
//		}
//		
//		System.out.println("|");
//	}

}
