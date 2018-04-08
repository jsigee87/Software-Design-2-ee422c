package assignment3;

import java.util.*;

public class Node {
	private String name;
	private String pi;
	private List<String> edges;
	// 0 white, 1 gray, 2 black
	private int color;
	private int distance;
	
	Node(String word){
		this.setName(word);
		this.setPi(null);
		this.edges = new LinkedList<String>();
		this.color = 0;
		this.distance = -1;		
	}
	
	public void addEdge(String edge) {
		edges.add(edge);
	}
	
	public void setEdges(List<String> edges) {
		this.edges = edges;
	}
	
	public List<String> getEdges() {
		return this.edges;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public int getDistance() {
		return this.distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPi() {
		return pi;
	}

	public void setPi(String pi) {
		this.pi = pi;
	}
}
