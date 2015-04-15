import java.io.*;
import java.util.*;

public class ssp {

	public static void main(String[] args) throws Exception {

		if (args.length == 3) {

			String fileName = args[0];
			int startNode = Integer.parseInt(args[1]);
			int endNode = Integer.parseInt(args[2]);
			Graph g1 = new Graph();
			Scanner scanner = new Scanner(new File(fileName));
			int nodeNum = scanner.nextInt();
			int edgeNum = scanner.nextInt();
			for (int i = 0; i < nodeNum; ++i) {
				g1.addVertex(i);
			}
			for (int i = 0; i < edgeNum; ++i) {
				int from = scanner.nextInt();
				int to = scanner.nextInt();
				int weight = scanner.nextInt();
				g1.addEdge(from, to, weight);
			}
			LinkedList<Vertex> path = new LinkedList<Vertex>();
			System.out.println(new DijkstraAlgm().solution(g1,
					g1.getVertex(startNode), g1.getVertex(endNode), path));
			for (Vertex v : path) {
				System.out.print(v.label + " ");
			}
		}

		else {
			System.out.println("parameter error");
		}
	}

}
