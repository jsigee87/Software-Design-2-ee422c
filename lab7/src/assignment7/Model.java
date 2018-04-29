/* CHEATERS Model.java
 * EE422C Project 7 submission by
 *
 * <John Sigmon>
 * <js85773>
 * <15455>
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

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

/**
 * 
 * @author Daniel Diamont and John Sigmon
 *
 */
public class Model {

	public static Hashtable<Integer, LinkedList<Integer>> hashtable;
	public static int [][] output_matrix;
	public static TreeMap<Integer, List<Integer>> output_dict;
	public static int total_files;
	
	public Model(int TOTAL_FILES){
		hashtable = new Hashtable<Integer, LinkedList<Integer>>(5 * TOTAL_FILES);
		output_matrix = new int [TOTAL_FILES][TOTAL_FILES];
		Model.total_files = TOTAL_FILES;
	}
	
	/**
	 * This method builds a 2D matrix where each entry in the matrix represents the
	 * number of similarities that any two files have with each other
	 */
	public static void buildMatrix() {
		
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
	}

	/**
	 * This method builds a dictionary out of the 2D matrix, where each key
	 * in the dictionary the number of similarities that a group of files
	 * share with each other, and the respective groups of files are the values
	 * attached to each key 
	 */
	public static void buildDictionary() {
		
		long start = System.nanoTime();
		
		Map<Integer, List<Integer>> flattened_matrix = new HashMap<>(Model.total_files*Model.total_files*5);
		
		for (int i = 0; i < output_matrix.length; i ++) {
			for (int j = 0; j < output_matrix.length; j++) {
				int similarities = output_matrix[i][j];
				
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
		
		System.out.println("flat matrix make time :" + (System.nanoTime()-start)/1000000000.0);
		//create a red-black tree keyed by the decreasing order of the keys of the flattened_matrix
		output_dict = new TreeMap<Integer, List<Integer>>(flattened_matrix);
		
	}
	
	/**
	 * This method repeatedly polls the last entry "x" of the output_dict red-black tree
	 * and prints out the files which share x similarities
	 */
	public static void printDictionary() {
		if (output_dict == null) {
			System.out.println("You haven't built the dictionary " + 
								"yet, you can't print it");
			new Exception().printStackTrace(System.out);
		}
		else {
			// get set of highest key values
			for (int i = 0; i < output_dict.size(); i ++) {
				Map.Entry<Integer, List<Integer>> entry = 
						output_dict.pollLastEntry();
			
				if(entry.getKey() >= Cheaters.threshold) {
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
	}
	
}
