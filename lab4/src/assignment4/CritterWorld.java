package assignment4;

import java.util.*;
import assignment4.Critter.TestCritter;
// This is the model

public class CritterWorld {

	private boolean shouldQuit;
	//virtual map
	static ArrayList<ArrayList<Integer>> virtualMap = new ArrayList<ArrayList<Integer>>();
	
	//location of individual critters
	static Hashtable<Critter,Location> locTable = new Hashtable<Critter,Location>();
	
	//baby location table
	static Hashtable<Critter,Location> babyLocTable = new Hashtable<Critter,Location>();
	
	//constructor
	public CritterWorld() {
		//TODO create default virtual map using Params
		shouldQuit = false;
		
		for(int i = 0; i < Params.world_height; i++) {
			ArrayList<Integer> list = new ArrayList<Integer>(Collections.nCopies(Params.world_width, -1));
			virtualMap.add(list);
		}
	}
	
	
	public List<Critter> getPopulation(){
		return TestCritter.getPopulation();
	}
	
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
	
	public static void clearWorld() {
		//TODO 
		for (int i = 0; i < virtualMap.size(); i++) {
			for(int j = 0; j < virtualMap.get(i).size(); j++) {
				virtualMap.get(i).set(j, -1);
			}
		}
	}
	
	public static void worldTimeStep() {
		//TODO
		
		//for each critter that is alive in our population, perform a timestep
		for(int i = 0; i < TestCritter.getPopulation().size(); i++) {
			//check if alive
			TestCritter.getPopulation().get(i).doTimeStep();
		}
	}
	
	public static void displayWorld() {
		
		updateMap();
		printEdge();
		printBody();
		printEdge();
	}
	
	public static void updateMap() {
		
		clearWorld();
		
		if(TestCritter.getBabies().size() > 0) {
			addBabies();
			emptyBabyList();
		}
		
		Set<Critter> keySet = locTable.keySet();
		
		for(Critter critter : keySet) {
			int x = locTable.get(critter).getX();
			int y = locTable.get(critter).getY();
			
			virtualMap.get(x).set(y, TestCritter.getPopulation().indexOf(critter));
		}
	}
	
	public static void addBabies() {
		System.out.println("addBabies");
		for(Critter baby : TestCritter.getBabies()) {
			
			baby.walk(babyLocTable.get(baby).getDir());
			int x = babyLocTable.get(baby).getX();
			int y = babyLocTable.get(baby).getY();
			
			addCritter(baby,x,y);
		}
	}
	
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
