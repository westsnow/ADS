import java.io.*;
import java.util.*;

public class routing {

	public static void main(String[] args) throws Exception {

		// routing algm
		if (args.length == 4) {

			Graph g = new Graph();
			Scanner scanner = new Scanner(new File(args[0]));
			Scanner ipscanner = new Scanner(new File(args[1]));
			int nodeNum = scanner.nextInt();
			int edgeNum = scanner.nextInt();

			int startNode = Integer.parseInt(args[2]);
			int endNode = Integer.parseInt(args[3]);

			for (int i = 0; i < nodeNum; ++i) {
				g.addVertex(i);
			}
			for (int i = 0; i < edgeNum; ++i) {
				int from = scanner.nextInt();
				int to = scanner.nextInt();
				int weight = scanner.nextInt();
				g.addEdge(from, to, weight);
			}
			scanner.close();

			// path is the optimized path from startNode to endNode
			LinkedList<Vertex> path = new LinkedList<Vertex>();
			// dist will save the ssp information
			HashMap<Vertex, Integer> dist = new HashMap<Vertex, Integer>();

			System.out.println(new DijkstraAlgm2().solution(g,
					g.getVertex(startNode), g.getVertex(endNode), path, dist));

			// firstly, assign each vertex with the corresponding IP address
			String[] ips = new String[nodeNum];

			for (int i = 0; i < ips.length; ++i) {
				ips[i] = ipscanner.next();
			}
			ipscanner.close();

			// then we build BinaryTrie for each router.
			BinaryTrie[] tries = new BinaryTrie[nodeNum];
			for (Vertex v : path) {
				int nodeId = v.label;
				tries[nodeId] = new BinaryTrie();
				int[] nextHops = new int[nodeNum];
				new DijkstraAlgm2().getSSPNextHop(g, g.getVertex(nodeId),
						nextHops);
				for (int i = 0; i < nodeNum; ++i) {
					if (i != nodeId)
						tries[nodeId].insert(new IP(ips[i]), new IP(
								ips[nextHops[i]]));
				}
				tries[nodeId].optimize();
			}

			// to Simulate the real world routing. we need to look up the
			// routing table with a given ip address
			HashMap<String, BinaryTrie> ipToRouteTable = new HashMap<String, BinaryTrie>();
			for (int i = 0; i < ips.length; ++i) {
				ipToRouteTable.put(ips[i], tries[i]);
			}

			/*
			 * next, simulate the package delivery from startNode to endNode.
			 * each time we reach a router, we retrive the nextHop information
			 * from routing table, which is a binary tree.
			 */
			String curIP = ips[startNode];
			String endIP = ips[endNode];
			BinaryTrie currentRouteTable = ipToRouteTable.get(curIP);
			// System.out.println(curIP+ " " +
			// currentRouteTable.getPrefix(new IP(curIP)));
			while (true) {
				String nextIP = currentRouteTable.get(new IP(endIP)).origin;
				System.out.print(currentRouteTable.getPrefix(new IP(endIP))
						+ " ");

				if (nextIP.equals(endIP))
					break;
				currentRouteTable = ipToRouteTable.get(nextIP);
			}

		} else {
			System.out.println("parameter error");
		}
	}

}
