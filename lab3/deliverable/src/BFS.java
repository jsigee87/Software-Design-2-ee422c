package assignment3;

import java.util.*;

public class BFS {
	
	/**
	 *  Graph is a hash map of integers and array lists of strings.
	 *  Ladder is the word ladder to return
	 */
	public HashMap<Integer, ArrayList<String>> bfstree;
	public ArrayList<String> ladder;

	/**
	 * Default Constructor
	 */
	BFS (){
		this.bfstree = new HashMap<Integer, ArrayList<String>>();
		this.ladder = new ArrayList<String>();
	}
	
	/**
	 * BFS search
	 * @param dictionary
	 * @param first
	 * @param last
	 * @return
	 */
	public boolean search (Set<String> dictionary, String first, String last) {
		int key = 0;
		first = first.toUpperCase();
		last = last.toUpperCase();
		ArrayList<String> new_layer = new ArrayList<String>();
		new_layer.add(first);
		bfstree.put(0, new_layer);
		dictionary.remove(first);
		
		while (true) {
			if (new_layer.isEmpty()) {
				ladder.add(first);
				ladder.add(last);
				return false;
			}
			
			new_layer = new ArrayList<String>();
			ArrayList<String> old_layer = bfstree.get(key);
			for (String word: old_layer) {
				new_layer = makeLayers(dictionary, new_layer, word);
			}
			key ++;
			bfstree.put(key, new_layer);
			
			if (new_layer.contains(last)) {
				break;
			}
		}
		
		ladder = retraceSteps(last);
		return true;
	}
	
	/**
	 *  Adds the BFS layers to the BFS tree
	 * @param dictionary The set of appropriate words
	 * @param word_list The lowest level of the bfs tree
	 * @param word
	 * @return
	 */
	public ArrayList<String> makeLayers(Set<String> dictionary, ArrayList<String> word_list, String word){
		for (int i = 0; i < 5; i ++) {
			for (int j = 'a'; j < 'z'; j ++) {
				String word_attempt = word.substring(0, i) + (char)j + word.substring(i + 1, word.length());
				word_attempt = word_attempt.toUpperCase();
				if (dictionary.contains(word_attempt)) {
					word_list.add(word_attempt);
					dictionary.remove(word_attempt);
				}
			}
		}
		return word_list;
	}
	
	/**
	 * Rebuilds the solution from the tree
	 * @param word
	 * @return
	 */
	public ArrayList<String> retraceSteps(String word){
		ArrayList<String> ladder = new ArrayList<String>();
		int num_differences = 0;
		ladder.add(word);
		for (int i = bfstree.size() - 2; i >= 0; i --) {
			char[] word_ = word.toCharArray();
			
			for (String key: bfstree.get(i)) {
				char[] compare = key.toCharArray();
				for (int letter = 0; letter < 5; letter ++) {
					if(word_[letter] != compare[letter]) {
						num_differences ++;
					}
				}
				
				if (num_differences == 1) {
					//System.out.println(compare.toString());
					String new_word = new String(compare);
					ladder.add(0, new_word);
					word = key;
					num_differences = 0;
					break;
				}
				num_differences = 0;
			}
		}
		
		return ladder;
	}
}
