package assignment3;

import java.util.*;

public class DFS {
	
	public static final int WHITE = 0;
	public static final int GRAY = 1;
	public static final int BLACK = 2;
	
	public HashMap<String, Node> graph;
	public Deque<String> ladder;
	public int distance;
	
	/**
	 * Constructor creates graph
	 * @param dict
	 */
	DFS(Set<String> dict, String end){
		this.graph = new HashMap<String, Node>(); 
		makeGraph(dict, end);
		this.ladder = new LinkedList<String>();
	}
	
	/**
	 * Called by constructor, builds graph from dictionary
	 * @param dict Makes graph from dictionary
	 */
	private void makeGraph(Set<String> dict, String end) {
		// Make each node
		for (String word : dict) {
			Node Word = new Node(word);
			graph.put(word, Word);
		}
		
		//Then make each adjacency list
		for (Map.Entry<String, Node> entry : graph.entrySet()) {
			Node node = entry.getValue();
			node.setEdges(findOneOff(entry.getKey(), dict, end));
		}
	}
	
	//public void printGraph() {
	//	for (Map.Entry<String, Node> entry : graph.entrySet()) {
	//		Node node = entry.getValue();
	//		System.out.println(node.edges);
	//	}
	//}
	
	/**
	 * DFS Algorithm
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean searchDFS (String start, String end) {
		start = start.toUpperCase();
		end = end.toUpperCase();
		visitDFS(start);
		if (makeLadder(start, end)) {
			return true;
		}
		return false;
		//for (Map.Entry<String, Node> entry : this.graph.entrySet()) {
			// If node is colored white
			//if(entry.getValue().getColor() == 0) {
				//visitDFS(entry.getKey());
			//}
		//}
	}
	
	/**
	 * DFS Algorithm
	 * @param word
	 */
	public void visitDFS(String word) {
		this.distance ++;
		Node current_word = graph.get(word);
		current_word.setDistance(distance);
		current_word.setColor(GRAY);
		List<String> neighbors = current_word.getEdges();
		for (String neighbor_name : neighbors) {
			Node neighbor = graph.get(neighbor_name);
			if (neighbor.getColor() == 0) {
				neighbor.setPi(current_word.getName());
				visitDFS(neighbor.getName());
			}
		}
		current_word.setColor(BLACK);
		this.distance ++;
	}
	
	/**
	 * Builds the word ladder after dfs
	 * @param start
	 * @param end
	 * @return true if successful
	 */
	public boolean makeLadder(String start, String end) {
		if (graph.get(end).getPi() == null) {
			return false;
		}
		
		boolean return_flag = false;
		traceBack(graph.get(start).getName(), graph.get(end).getName(), return_flag);
		ladder.push(start.toLowerCase());
		return true;
	}
	
	/**
	 * Recursively trace back the steps in dfs
	 * @param start
	 * @param end
	 * @param return_flag breaks recursion
	 */
	public void traceBack(String start, String end, boolean return_flag) {
		if (return_flag == true) {
			return;
		}
		else if (start == end) {
			return_flag = true;
			return;
		}
		
		ladder.push(end.toLowerCase());
		traceBack(start, graph.get(end).getPi(), return_flag);
		
	}
	
	/**
	 * Builds adjacency list for graph
	 * @param word
	 * @param dict
	 * @param end
	 * @return List of Strings of neighbors
	 */
	public List<String> findOneOff(String word, Set<String> dict, String end){
		List<String> oneOffs = new LinkedList<String>();
		//char[] word_ = word.toCharArray();
		// Check all permutations of the word that differ by one and 
		// make a list of them if they are in the dictionary.
		for (int i = 0; i < 5; i ++) {
			char[] word_ = word.toCharArray();
			for (int letter = 'A'; letter < 'Z' + 1; letter ++) {
				word_[i] = (char)letter;
				String attempt = new String(word_);
				if (dict.contains(attempt)) {
					// got one or two duplicates, weird
					if (!oneOffs.contains(attempt) && word != attempt) {
						oneOffs.add(attempt);
					}
				}
			}
		}
		return sortNeighbors(oneOffs, end);
		//return oneOffs;
	}
	
	/**
	 * Sorts the neighbors by how much they differ from target
	 * @param neighbors
	 * @param end
	 * @return List of Strings of neighbors
	 */
	public List<String> sortNeighbors(List<String> neighbors, String end){
		Map<String, Integer> counts = new HashMap<String, Integer>();
		for (String neighbor : neighbors) {
			int count = 0;
			for (int i = 0 ; i < end.length() ; i ++){
		        count += neighbor.charAt(i) == end.charAt(i) ? 1 : 0;
		    }
			counts.put(neighbor, count);
		}
		List<String> ret_list = new LinkedList<String>();
		for (int i = 0; i < 6; i ++) {
			for (String neighbor : neighbors) {
				if(counts.get(neighbor)  == i) {
					ret_list.add(neighbor);
				}
			}
		}
		return ret_list;
	}

	/**
	 * Shortens the ladder by removing words that can be accomplished in one step
	 * @param al_ladder 
	 * @return Array List of Strings (Word Ladder)
	 */
	public ArrayList<String> shortenLadder(ArrayList<String> al_ladder) {
		for(int i = 0; i < (al_ladder.size() - 2); i ++){
            String beg = al_ladder.get(i);           // Get First Word First Letter
            String middle = al_ladder.get(i + 1);
            String after = al_ladder.get(i + 2);
            for(int j = 0; j < 5; j ++){
                if((beg.charAt(j) != middle.charAt(j)) && (middle.charAt(j) != after.charAt(j))){
                    al_ladder.remove(middle);
                    if (i > 0) i -- ;
                }
                
            }
        }
		return al_ladder;
	}
	
}
