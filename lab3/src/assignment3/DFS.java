package assignment3;
import java.util.*;

public class DFS {
	 
	/**
	 * 
	 */
	public static ArrayList<String> ladder;
	
	public DFS() {
		ladder = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @param first
	 * @param last
	 * @param dictionary
	 * @return
	 */
	public static boolean search(String first, String last, Set<String> dictionary) {
		if(first.equals("No Word Ladder")) {
			return false;
		}
		
		else if(first.equals(last)) {
			ladder.add(0, first);
			return true;
		}
		
		else if(noMatch(first, dictionary) == false) {
			if(search(getNextWord(first, dictionary), last, dictionary)) {
				ladder.add(0, first);
			}
			else if(search(getNextWord(first, dictionary), last, dictionary)) {
				ladder.add(0, first);
			}
		}
		
		else {
			return false;
		}
		
		return false;
		
	}
	
	/**
	 * 
	 * @param word
	 * @param dictionary
	 * @return
	 */
	public static String getNextWord(String word, Set<String> dictionary) {
		//
		for (int i = 0; i < word.length(); i ++) {
			char[] word_array = word.toCharArray();
			word_array[i] = word.charAt(i);
			String next = String.valueOf(word_array);
			if(wordInDict(next, dictionary)) {
				return next;
			}
		}
		
		for (int i = 0; i < word.length(); i ++) {
			char[] word_array = word.toCharArray();
			for (char letter = 'a'; letter < 'z'; letter ++) {
				word_array[i] = letter;
				String attempt = String.valueOf(word_array);
				if(wordInDict(attempt, dictionary)) {
					return attempt;
				}
			}
		}
		
		// If neither of those work
		return "No Word Ladder";
	}
	
	/**
	 * 
	 * @param word
	 * @param dictionary
	 * @return
	 */
	public static boolean noMatch(String word, Set<String> dictionary) {
		char[] word_array;
		for (int i = 0; i < word.length(); i ++) {
			word_array = word.toCharArray();
			for (char letter = 'a'; letter < 'z'; letter ++) {
				word_array[i] = letter;
				String attempt = String.valueOf(word_array);
				// does the word need to be removed here too?
				if(dictionary.contains(attempt.toLowerCase()));
			}
		}
		// We found nothing (no match is true)
		return true;
	}
	
	
	/**
	 * 
	 * @param word
	 * @param dictionary
	 * @return
	 */
	public static boolean wordInDict(String word, Set<String> dictionary) {
		if (dictionary.contains(word.toLowerCase())) {
			dictionary.remove(word.toLowerCase());
			return true;
		}
		return false;
	}
	
	public static boolean shorten() {
		return true;
	}
}
