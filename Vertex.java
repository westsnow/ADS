
import java.util.*;

public class Vertex {
	public int label;
	public Vertex previous;//this value is used to track the ssp in dijkstra's algorithm
	private LinkedList<Vertex> neighbors;
	private HashMap<Vertex, Integer> weightsInfo;
	public Vertex(int x) {
		label = x; 
		neighbors = new LinkedList<Vertex>(); 
		weightsInfo = new HashMap<Vertex, Integer>(); 
		previous = null;
	}
	public void addEdge(Vertex des, int w){
		neighbors.add(des);
		weightsInfo.put(des, w);
	}	
	public LinkedList<Vertex> getNeighbors(){
		return neighbors;
	}
	public int getWeightTo(Vertex v){
		if(this == v)
			return 0;
		return weightsInfo.get(v);
	}
}