package assignment4;

import java.util.*;
import assignment4.Critter.TestCritter;
// This is the model

public class CritterWorld {

	//constructor
	public CritterWorld() {
		
	}
	
	
	public List<Critter> getPopulation(){
		return TestCritter.getPopulation();
	}
	
	public void addCritter() {
		//TODO
	}
	
	public static void clearWorld() {
		//TODO 
	}
	
	public static void worldTimeStep() {
		//TODO 
	}
	
	public static void displayWorld() {
		printEdge();
		printBody();
		printEdge();
	}
	
	
	//TODO Conflict Resolution
	//TODO Garbage Collection
	
	
	
	
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
		
		//setting up bitmap of critters
		
		//TODO any way to make this better?
		ArrayList<ArrayList<Integer>> virtual_map = new ArrayList<ArrayList<Integer>>();
		
		for(int k = 0; k < Params.world_height; k++) {
			ArrayList<Integer> list  = new ArrayList<Integer>(Collections.nCopies(Params.world_width, -1));
			virtual_map.add(list);
		}
	
		for (Critter critter : TestCritter.getPopulation()) {
			//TODO: check if dead
			int x = critter.getX();
			int y = critter.getY();
			virtual_map.get(x).set(y, TestCritter.getPopulation().indexOf(critter));
		}
		
		for (i = 0; i < Params.world_height; i++) {
			System.out.print("|");
			for(j = 0; j < Params.world_width; j++) {
				if (!virtual_map.get(i).get(j).equals(-1)) {
					//TODO print out what the critter looks like
					System.out.print(TestCritter.getPopulation().get(virtual_map.get(i).get(j)).toString());
				}
				else {
					System.out.print(" "); //print an empty space
				}
				
			}
			
			System.out.println("|");
		}
	}


}
