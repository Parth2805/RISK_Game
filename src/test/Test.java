package test;
 
import java.util.LinkedList;

public class Test {
	public static void main(String args[]) {
		
		int V = 5;
		Graph graph = new Graph(V);
		addEdge(graph, 0, 1);
		addEdge(graph, 0, 4);
		addEdge(graph, 1, 2);
		addEdge(graph, 1, 3);
		addEdge(graph, 1, 4);
		addEdge(graph, 2, 3);
		addEdge(graph, 3, 4);

		printGraph(graph);
	}
	// Size of array will be V (number of vertices
	// in graph)
	static class Graph {
		int V;
		LinkedList<Integer> adjListArray[];
		// constructor
		Graph(int V) {
			this.V = V;
			adjListArray = new LinkedList[V];
			for (int i = 0; i < V; i++) {
				adjListArray[i] = new LinkedList<>();
			}
		}
	}

	static void addEdge(Graph graph, int x, int y) {
		// Add an edge from src to dest.
		graph.adjListArray[x].add(y);
	}

	static void printGraph(Graph graph) {
		for (int v = 0; v < graph.V; v++) {
			System.out.println("Adjacency list of vertex " + v);
			System.out.print("head");
			for (Integer pCrawl : graph.adjListArray[v]) {
				System.out.print(" -> " + pCrawl);
			}
			System.out.println("\n");
		}
	}
}