

import java.util.*;

//import com.shuaiwu.app.DijDijkstraAlgm;

/*
 1  function Dijkstra(Graph, source):
 2
 3      dist[source] ← 0                       // Distance from source to source
 4      prev[source] ← undefined               // Previous node in optimal path initialization
 5
 6      for each vertex v in Graph:  // Initialization
 7          if v ≠ source            // Where v has not yet been removed from Q (unvisited nodes)
 8              dist[v] ← infinity             // Unknown distance function from source to v
 9              prev[v] ← undefined            // Previous node in optimal path from source
10          end if 
11          add v to Q                     // All nodes initially in Q (unvisited nodes)
12      end for
13      
14      while Q is not empty:
15          u ← vertex in Q with min dist[u]  // Source node in first case
16          remove u from Q 
17          
18          for each neighbor v of u:           // where v is still in Q.
19              alt ← dist[u] + length(u, v)
20              if alt < dist[v]:               // A shorter path to v has been found
21                  dist[v] ← alt 
22                  prev[v] ← u 
23              end if
24          end for
25      end while
26
27      return dist[], prev[]
28
29  end function
 */

public class DijkstraAlgm2 {
	//			new DijDijkstraAlgm().getSSPNextHop(g, g.getVertex(nodeId), nextHops);
	public void getSSPNextHop(Graph graph, Vertex s, int[] nextHops){
		final HashMap<Vertex, Integer> dist = new HashMap<Vertex, Integer>();
		LinkedList<Vertex> vertices = graph.getVertices();
		for(Vertex v: vertices){
			dist.put(v, Integer.MAX_VALUE);
		}
		dist.put(s, 0);
		Comparator<Vertex> FiboComparator = new Comparator<Vertex>(){     
	        @Override
	        public int compare(Vertex v1, Vertex v2) {
	            return (int) (dist.get(v1) - dist.get(v2));
	        }
	    };
	    
		FibonacciHeap<Vertex> queue =  new FibonacciHeap<Vertex>(FiboComparator);
		for(Vertex v: vertices){
			queue.offer(v);
		}
		while(!queue.isEmpty()){
			Vertex minV = queue.poll();
			LinkedList<Vertex> neighbors = minV.getNeighbors();
			for(Vertex v: neighbors){
				int newValue = dist.get(minV) + minV.getWeightTo(v);
				if(dist.get(v) > dist.get(minV) + minV.getWeightTo(v)){
					dist.put(v, newValue);
					v.previous = minV;
					queue.remove(v);
					queue.offer(v);
				}
			}
		}
		for(int i = 0; i < nextHops.length; ++i){
			if(i == s.label)
				nextHops[i] = i;
			else{
				Vertex v = graph.getVertex(i);
				while(v.previous != s)
					v = v.previous;
				nextHops[i] = v.label;
			}
		}
	}
	
	
	public int solution(Graph graph, Vertex s, Vertex end, LinkedList<Vertex> path,
		final HashMap<Vertex, Integer> dist){
		LinkedList<Vertex> vertices = graph.getVertices();
		for(Vertex v: vertices){
			dist.put(v, Integer.MAX_VALUE);
		}
		dist.put(s, 0);
		Comparator<Vertex> FiboComparator = new Comparator<Vertex>(){     
	        @Override
	        public int compare(Vertex v1, Vertex v2) {
	            return (int) (dist.get(v1) - dist.get(v2));
	        }
	    };
	    
		FibonacciHeap<Vertex> queue =  new FibonacciHeap<Vertex>(FiboComparator);
		for(Vertex v: vertices){
			queue.offer(v);
		}
		while(!queue.isEmpty()){
			Vertex minV = queue.poll();
			LinkedList<Vertex> neighbors = minV.getNeighbors();
			for(Vertex v: neighbors){
				int newValue = dist.get(minV) + minV.getWeightTo(v);
				if(dist.get(v) > dist.get(minV) + minV.getWeightTo(v)){
					dist.put(v, newValue);
					v.previous = minV;
					queue.remove(v);
					queue.offer(v);
				}
			}
		}
		path.add(end);
		int minValue = dist.get(end);
		while(end.previous != null){
			path.addFirst(end.previous);
			end = end.previous;
		}
		return minValue;
	}
}


