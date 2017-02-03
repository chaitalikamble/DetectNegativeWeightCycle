import java.io.*;
import java.util.*;
/* 
 * NegativeCycle.java 
 * 
 * Version: 
 *     $1.0$ 
 * 
 * Revisions: 
 *     $initial$ 
 *     
 * This program reads the vertices and egdes and detects negative
 * weight cycle if present.  
 * 
 * @author	Chaitali Kamble		Section 03
 * @author	Vaibhavi Awghate	Section 03
 */
public class NegativeCycle {

	public static void main(String[] args) throws IOException {
		/*
		 * Main method, takes input vertices and edges
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String number = br.readLine();
		String[] l = new String[3];
		l = number.split(" ");
		int TotalVertices = Integer.parseInt(l[0]);
		int TotalEdges = Integer.parseInt(l[1]);
		int[][] matrix = new int[TotalVertices][TotalVertices];
		
		// Initializing adjacency matrix with infinity
		for (int i = 0; i < TotalVertices; i++) {
			for (int j = 0; j < TotalVertices; j++) {
				matrix[i][j] = 999999999;
			}
		}
		
		// adding cost to proper edge in adjacency matrix
		for (int i = 0; i < TotalEdges; i++) {
			String line = br.readLine();
			l = line.split(" ");
			int u = Integer.parseInt(l[0]);
			int v = Integer.parseInt(l[1]);
			int c = Integer.parseInt(l[2]);
			matrix[u][v] = c;
		}
		
		// Check if negative edge exists or not
		boolean flag = false;
		int start = 0;
		int end = 0;
		int edgecost = 0;
		for (int i = 0; i < TotalVertices; i++) {
			for (int j = 0; j < TotalVertices; j++) {
				if (matrix[i][j] < 0) {
					flag = true;
					start = j;
					end = i;
					edgecost = matrix[i][j];
				}
			}
		}
		
		// if negative weight exists, then detect negative weight cycle.
		if(flag == true){
			isCycle(matrix, start, end, edgecost, TotalVertices);
		}
	}

	private static void isCycle(int[][] matrix, int start, int end, int edgecost, int TotalVertices) {
		/*
		 * This method detects the negative weight cycle if present.
		 * It will print "YES" if it detects negative the cycle else "NO".
		 * @param:	adjacency matrix, source, destination, negative edge cost, total number of vertices.
		 * @return: None
		 */
		int[] dist = new int[TotalVertices]; // store total distances
		int[] parent = new int[TotalVertices]; // store parent
		Set<Integer> visited = new HashSet<Integer>(); // keep track of visited vertices
		
		// Initialize distance and parent arrays
		for (int i = 0; i < TotalVertices; i++) {
			dist[i] = 999999999;
			parent[i] = -1;
		}
		
		dist[start] = 0;  // make 0 to the start vertex in distance array
		parent[start] = -1; // make -1 to the parent

		dist = update(start, TotalVertices, dist, parent, matrix, visited);
		visited.add(start);

		// iterations to keep updating minimum cost
		for (int j = 1; j < TotalVertices; j++) {
			int u = getMin(dist, visited);
			dist = update(u, TotalVertices, dist, parent, matrix, visited);
			visited.add(u);
		}
			
		// calculate total distance
		int total = dist[end] + edgecost ;
		if (total < 0) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}

	private static int getMin(int[] dist, Set<Integer> visited) {
		/*
		 * This method retrieves the minimum value from array
		 * 
		 * @param:	distance array, visited set
		 * @return: index of minimum value in array
		 */
		int min = 999999999;
		int index = 0;
		for (int j = 0; j < dist.length; j++) {
			if (!visited.contains(j)) {
				if (dist[j] < min) {
					min = dist[j];
					index = j;
				}
			}
		}
		return index;
	}

	private static  int[] update(int u, int TotalVertices, int[] dist, int[] parent, int[][] matrix,
			Set<Integer> visited) {
		/*
		 * This method updates the minimum cost and its parent
		 * 
		 * @param:	Total number of vertices, distance array, parent array, adjacency matrix
		 * @return: None
		 */
		for (int v = 0; v < TotalVertices; v++) {
			if (!visited.contains(v)) {
				if (dist[v] > dist[u] + matrix[u][v]) {
					dist[v] = dist[u] + matrix[u][v];
					parent[v] = u;
				}
			}
		}
		return dist;
	}
}
