package assignment4;

import java.util.*;
import assignment4.Critter.TestCritter;
// This is the model

public class CritterWorld {

	private boolean shouldQuit;
	
	/*
	 * Virtual map. Holds the index of the critter that is to be displayed when displayWorld() is called.
	 */
	static ArrayList<ArrayList<Integer>> virtualMap = new ArrayList<ArrayList<Integer>>();
	
	/*
	 * location of individual critters (we need this because multiple critters can be on the
	 * same location pre-conflict resolution. Also, we need to store the critter locations
	 * in order to implement their walk, run, and reproduce functions.
	 */
	static Hashtable<Critter,Location> locTable = new Hashtable<Critter,Location>();
	
	/*
	 * baby location table: if some critter reproduces, the baby critter and its location
	 * goes in this table. At first, the location of the baby is the same as that of its parent.
	 * The location is later updated using the babyCritters walk function in the specified direction.
	 * 
	 * ***Note: This may only work correctly for Craigs. Need to test our own critters later.
	 */
	static Hashtable<Critter,Location> babyLocTable = new Hashtable<Critter,Location>();
	
	/*
	 * Constructor. Called at the beginning of the game. Creates a default world using the parameters
	 * located in Params. For STAGE 3, we will have to update the controller (Main) to create the game
	 * with the user parameters.
	 */
	public CritterWorld() {
		shouldQuit = false;
		
		for(int i = 0; i < Params.world_height; i++) {
			ArrayList<Integer> list = new ArrayList<Integer>(Collections.nCopies(Params.world_width, -1));
			virtualMap.add(list);
		}
	}
	
	
	public List<Critter> getPopulation(){
		return TestCritter.getPopulation();
	}
	
	/*
	 * addCritter takes a critter and the location to where it will be inserted at.
	 * 
	 * Sets the inital energy level to the level specified in Params.
	 * 
	 * Adds the critter to the population collection in Critter.TestCritter, places said
	 * critter on the virtual map for display, and adds the critter to the location hashtable (locTable).
	 * 
	 * ***NOTE: Even if there are multiple critters on the same location, per the instructions,
	 * the virtualMap only has to hold one of the critters because only one critter may be displayed
	 * at a time. So the virtualMap can be overwritten arbitrarily if we add multiple critters in the
	 * same location. We can maintain the other critters and their locations in the location hashtable.
	 */
	public static void addCritter(Critter critter, int x, int y) {
		
		critter.setEnergy(Params.start_energy);
		//insert critter into population (in Critter class)
		TestCritter.getPopulation().add(critter);
		
		//put critter in virtual map
		virtualMap.get(x).set(y, TestCritter.getPopulation().indexOf(critter));
		
		//put critter's loc in locTable
		Location loc = new Location(x,y);
		locTable.put(critter, loc);
	}
	
	/*
	 * clearWorld replaces all the integers in the virtualMap to -1. This should be called every time
	 * before displaying the world.
	 * 
	 * Maybe there's a better way of doing this that's not O(m*n). I'm tired so I'm just gonna leave it for now.
	 */
	public static void clearWorld() {
		//TODO make better if we can, though not a high priority right now.
		for (int i = 0; i < virtualMap.size(); i++) {
			for(int j = 0; j < virtualMap.get(i).size(); j++) {
				virtualMap.get(i).set(j, -1);
			}
		}
	}
	
	/*
	 * perform a time step for each critter in the world
	 */
	public static void worldTimeStep() {
		//TODO
		
		//for each critter that is alive in our population, perform a timestep
		for(int i = 0; i < TestCritter.getPopulation().size(); i++) {
			//check if alive
			TestCritter.getPopulation().get(i).doTimeStep();
		}
	}
	/*
	 * update the virtual map, and display the world on the console.
	 */
	public static void displayWorld() {
		
		updateMap();
		printEdge();
		printBody();
		printEdge();
	}
	
	public static void updateMap() {
		
		clearWorld(); //clear the world
		
		//if babies spawned during the last round, add the babies to the CritterWorld
		if(TestCritter.getBabies().size() > 0) {
			addBabies(); //add
			emptyBabyList(); //clear the list of babies for the next round
		}
		
		//update the virtual map with all of the critters
		Set<Critter> keySet = locTable.keySet();
		
		for(Critter critter : keySet) {
			int x = locTable.get(critter).getX();
			int y = locTable.get(critter).getY();
			
			virtualMap.get(x).set(y, TestCritter.getPopulation().indexOf(critter));
		}
	}
	
	/*
	 * add babies to the Critter World
	 */
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
	 */
	public static void emptyBabyList()
	{
		babyLocTable.clear();
		TestCritter.getBabies().clear();;
	}
	
	public static void resolveConflicts() {
		//TODO Conflict Resolution
		//TODO Garbage Collection
	}	
	
	
	public static void printEdge() {
		System.out.print('+');
		for (int i = 0; i < Params.world_width; i ++) {
			System.out.print('-');
		}
		System.out.print('+');
		System.out.println();
	}
	
	public static void printBody() {
		int i = 0;
		int j = 0;
		
		for (i = 0; i < Params.world_height; i++) {
			System.out.print("|");
			for(j = 0; j < Params.world_width; j++) {
				
				int critter = virtualMap.get(i).get(j);
				//if the spot (i,j) is occupied
				if(critter != -1) {
					//print out the critter
					System.out.print(TestCritter.getPopulation().get(critter));					
				}
				else {
					System.out.print(" "); //print an empty space
				}
				
			}
			
			System.out.println("|");
		}
		
		//setting up bitmap of critters
		
		//TODO any way to make this better?
//		ArrayList<ArrayList<Integer>> virtual_map = new ArrayList<ArrayList<Integer>>();
//		
//		for(int k = 0; k < Params.world_height; k++) {
//			ArrayList<Integer> list  = new ArrayList<Integer>(Collections.nCopies(Params.world_width, -1));
//			virtual_map.add(list);
//		}
//	
//		for (Critter critter : TestCritter.getPopulation()) {
//			//TODO: check if dead
//			int x = critter.getX();
//			int y = critter.getY();
//			virtual_map.get(x).set(y, TestCritter.getPopulation().indexOf(critter));
//		}
//		
//		for (i = 0; i < Params.world_height; i++) {
//			System.out.print("|");
//			for(j = 0; j < Params.world_width; j++) {
//				if (!virtual_map.get(i).get(j).equals(-1)) {
//					//TODO print out what the critter looks like
//					System.out.print(TestCritter.getPopulation().get(virtual_map.get(i).get(j)).toString());
//				}
//				else {
//					System.out.print(" "); //print an empty space
//				}
//				
//			}
//			
//			System.out.println("|");
//		}
		
	}
	
	public boolean shouldQuit() {
		return shouldQuit;
	}
	
	public void quit() {
		shouldQuit = true;
	}
	
	


}
