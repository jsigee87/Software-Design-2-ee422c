/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * John Sigmon
 * js85773
 * <Student1 5-digit Unique No.>
 * Git URL: https://github.com/jsigee87
 * Spring 2018
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	public static boolean exit_flag = false;
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		
		initialize();
		
		while(exit_flag == false) {
			ArrayList<String> parsed = parse(kb);
			if (parsed.isEmpty()) {
				return;
			}
			
			String first_word = parsed.get(0);
			String second_word = parsed.get(1); 
			//ArrayList<String> bfs_ladder = getWordLadderBFS(first_word, second_word);
			ArrayList<String> dfs_ladder = getWordLadderDFS(first_word, second_word);
			//printLadder(bfs_ladder);
			printLadder(dfs_ladder);
		}
		
		
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> input = new ArrayList<String>();
		
		String first = keyboard.next();
		if(first.equals("/quit")){ 
			exit_flag = true;
			return input;
		}
		
		String second = keyboard.next();
		if(second.equals("/quit")){ 
			exit_flag = true;
			return input;
		}
		
		input.add(first);
		input.add(second);
		
		return input;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// If ladder is empty, return list with just start and end.
		// TODO some code
		start = start.toLowerCase();
		end = end.toLowerCase();
		
		Set<String> dictionary = makeDictionary();
		dictionary.remove(start);
		DFS dfs_search = new DFS();
		if (dfs_search.search(start, end, dictionary)) {
			//dfs_search.shorten(start.length());
			printLadder(dfs_search.ladder);
			return dfs_search.ladder;
		}
		else {
			ArrayList<String> dummy = new ArrayList<String>();
			dummy.add(0, start);
			dummy.add(1, end);
			printLadder(dummy);
			return dummy;
		}
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		Set<String> dict = makeDictionary();
		// remove start and add it to the beginning
		//search
		//remove end and add it to end.
		
		return null; // replace this line later with real return
	}
    
	
	public static void printLadder(ArrayList<String> ladder) {
		if(ladder.size() == 2){ 
			System.out.println("no word ladder can be found between " + ladder.get(0)
				+ " and " + ladder.get(1) + "." );
			return;
		}
		
		System.out.println("A " + (ladder.size()-2) + "-rung word ladder exists between " 
				+ ladder.get(0) + " and " + ladder.get(ladder.size() - 1) + "." );
		
		for(String key: ladder){
			System.out.println(key);
		}
	}



	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
