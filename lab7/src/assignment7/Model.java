package assignment7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;

public class Model {

	public static Hashtable<Integer, LinkedList<Integer>> hashtable;
	public static int [][] output_matrix;
	public static TreeMap<Integer, List<Integer>> output_dict;
	
	public Model(int TOTAL_FILES){
		hashtable = new Hashtable<Integer, LinkedList<Integer>>(5 * TOTAL_FILES);
		output_matrix = new int [TOTAL_FILES][TOTAL_FILES];
		//output_dict = new TreeMap<Integer, Integer>();
	}
	
	public static void buildMatrix() {
		/*for (Integer key : hashtable.keySet()) {
			System.out.println(hashtable.get(key));
		}*/
		//System.out.println(setOfKeys.toString());
		for (Integer key: hashtable.keySet()) {
			if (hashtable.get(key) != null) {
				List<Integer> vals = hashtable.get(key);
				for (int i = 0; i < vals.size(); i ++) {
					for (int j = i; j < vals.size(); j ++) {
						if (i != j) {
							output_matrix[vals.get(i)][vals.get(j)] += 1;
						}
					}
				}
			}
		}
		
		
//		for (int i = 0; i < output_matrix.length; i ++) { 
//			for (int j = 0; j < output_matrix.length; j ++) {
//				
//				System.out.print(output_matrix[i][j] + "|"); 
//			}
//			System.out.println();
//			for (int k = 0; k < 2* output_matrix.length; k ++) {System.out.print("-");}
//			System.out.println();
//		}
	}
	
	public static void buildDictionary() {
		Map<Integer, List<Integer>> flattened_matrix = new HashMap<>();
		for (int i = 0; i < output_matrix.length; i ++) {
			for (int j = 0; j < output_matrix.length; j++) {
				int similarities = output_matrix[i][j];
				if (similarities >= Cheaters.threshold) {
					// if similarities is not already in the map
					if (!flattened_matrix.containsKey(similarities)){
						List<Integer> files = new ArrayList<>();
						files.add(i);
						files.add(j);
						flattened_matrix.put(similarities, files);
					}
					else {
						List <Integer> files = new ArrayList<>(flattened_matrix.get(similarities));
						files.add(i);
						files.add(j);
						flattened_matrix.put(similarities, files);
					}
				}
			}
		}
		
//		System.out.println("Keys in flattened matrix");
//		for(Integer i : flattened_matrix.keySet()) {
//			System.out.println(i);
//		}
//		
//		System.out.println("flattened matrix entries");
//		for(int i = 0; i < flattened_matrix.size(); i++) {
//			System.out.println(flattened_matrix.get(i));
//		}
		
		
		output_dict = new TreeMap<Integer, List<Integer>>(flattened_matrix);
		
	}
	
	public static void printDictionary() {
		if (output_dict == null) {
			System.out.println("You haven't built the dictionary " + 
								"yet, you can't print it");
			new Exception().printStackTrace(System.out);
		}
		else {
			// get set of highest key values
			//NavigableSet<Integer> descending_keys = output_dict.descendingKeySet();
//			System.out.println("tyc12 index: " + Cheaters.file_list.indexOf("tyc12.txt"));
//			System.out.println("hal10 index: " + Cheaters.file_list.indexOf("hal10.txt"));
//			System.out.println("ecu201 index: " + Cheaters.file_list.indexOf("ecu201.txt"));
//			System.out.println("catchmeifyoucan index: " + Cheaters.file_list.indexOf("catchmeifyoucan.txt"));
			
			for (int i = 0; i < output_dict.size(); i ++) {
				Map.Entry<Integer, List<Integer>> entry = 
						output_dict.pollLastEntry();
			
				//System.out.println(output_dict.size());
				
				//System.out.println(entry.getValue().get(0));
				//System.out.println(entry.getValue().get(1));
				//System.out.println(Cheaters.file_list.size());
				//String file1 = Cheaters.file_list.get(entry.getValue().get(0));
				//String file2 = Cheaters.file_list.get(entry.getValue().get(1));
				System.out.print("Files [");
				System.out.print(Cheaters.file_list.get(entry.getValue().get(0)));
				for (int j = 1; j < entry.getValue().size(); j++) {
					System.out.print(", ");
					System.out.print(Cheaters.file_list.get(entry.getValue().get(j))); // Print file name
				}
				System.out.println("] have " + entry.getKey() + " similarities with each other.");
			}
		}
	}
	
	public static void printMatrix() {
		
	}
}
