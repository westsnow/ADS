
import java.util.*;

public class Graph {
	private LinkedList<Vertex> vertices;
	private HashMap<Integer, Vertex> map;
	public Graph(){
		vertices = new LinkedList<Vertex>();
		map = new HashMap<Integer, Vertex>();
	}
	public LinkedList<Vertex> getVertices(){
		return vertices;
	}
	public boolean addVertex(int label){
		if(map.containsKey(label))
				return false;
		Vertex v = new Vertex(label);
		vertices.add(v);
		map.put(label, v);
		return true;
	}
	public boolean addEdge(int n1, int n2, int weight){
		Vertex v1 = map.get(n1);
		Vertex v2 = map.get(n2);
		if(null == v1 || null == v2)
			return false;
		v1.addEdge(v2, weight);
		v2.addEdge(v1, weight);
		return true;
	}
	public Vertex getVertex(int label){
		return map.get(label);
	}
}
