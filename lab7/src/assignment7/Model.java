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
	
	Model(int TOTAL_FILES){
		hashtable = new Hashtable<Integer, LinkedList<Integer>>(5 * TOTAL_FILES);
		output_matrix = new int [TOTAL_FILES][TOTAL_FILES];
		//output_dict = new TreeMap<Integer, Integer>();
	}
	
	public static void buildMatrix() {
		Set<Integer> setOfKeys = hashtable.keySet();
		for (Integer key: setOfKeys) {
			if (hashtable.get(key) != null) {
				List<Integer> vals = new ArrayList<Integer>();
				vals = hashtable.get(key);
				for (int i = 0; i < vals.size(); i ++) {
					for (int j = i; j < vals.size(); j ++) {
						if (i != j) {
							output_matrix[i][j] += 1;
						}
					}
				}
			}
		}	
	}
	
	public static void buildDictionary() {
		Map<Integer, List<Integer>> flattened_matrix = new HashMap<>();
		for (int i = 0; i < output_matrix.length; i ++) {
			for (int j = (i + 1); j < output_matrix.length; j++) {
				int similarities = output_matrix[i][j];
				List<Integer> files = new ArrayList<Integer>();
				files.add(i);
				files.add(j);
				flattened_matrix.put(similarities, files);
			}
		}
		output_dict = new TreeMap<Integer, List<Integer>>(flattened_matrix);
	}
	
	public static void printDictionary(int num_files) {
		if (output_dict == null) {
			System.out.println("You haven't built the dictionary " + 
								"yet, you can't print it");
			new Exception().printStackTrace(System.out);
		}
		else {
			// get set of highest key values
			//NavigableSet<Integer> descending_keys = output_dict.descendingKeySet();
			for (int i = 0; i < num_files; i ++) {
				Map.Entry<Integer, List<Integer>> entry = 
						output_dict.pollLastEntry();
				String file1 = Cheaters.file_list.get(entry.getValue().get(0));
				String file2 = Cheaters.file_list.get(entry.getValue().get(1));
				System.out.print("Files " + file1 + " and " + file2 + " had ");
				System.out.println(entry.getKey() + "similarities");
			}
		}
	}
	
	public static void printMatrix() {
		
	}
}
