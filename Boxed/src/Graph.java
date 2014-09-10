public class Graph {
	private int[][] matrix;
	private int[] marks;
	private int edges;
	
	public Graph(int v) {// initializes a graph of v vertices with no edges
		matrix = new int[v][v];
		marks = new int[v];
		
		for(int i = 0; i < matrix.length; i++) {
			marks[i] = 0;
			for(int j = 0; j < matrix.length; j++) {
				matrix[i][j] = 0;
			}
		}
		
		edges = 0;
	}
	
	public int vcount() { // returns number of vertices (whether connected or not) in the graph
		return matrix.length;
	}
	
	int ecount() { // returns the number of edges in the graph
		return edges;
	}
	
	int first(int v) { // returns the first vertex (in natural order) connected to vertex v.  If there are none, then vcount() is returned
		int result = vcount();
		
		for(int i = 0; i < matrix.length; i++) {
			if(matrix[v][i] != 0) {
				result = i;
				i = matrix.length;
			}
		}
		
		return result;
	}
	
	int next(int v, int w) { // returns the vertex (in natural order) connected to vertex v after vertex w.  If there are no more edges after w, vcount() is returned
		int result = vcount();
		
		for(int i = w+1; i < matrix.length; i++) {
			if(matrix[v][i] != 0) {
				result = i;
				i = matrix.length;
			}
		}
		return result;
	}
	
	void addEdge(int v, int w, int wt) { // adds an edge between vertex v and vertex w.
		if(wt != 0) {
			if(matrix[v][w] == 0) {
				edges++;
			}
			matrix[v][w] = wt;
			matrix[w][v] = wt;
		}
	}
	
	void removeEdge(int v, int w) { // removes edge between vertex v and vertex w.
		if(matrix[v][w] != 0) {
			edges--;
		}
		matrix[v][w] = 0;
		matrix[w][v] = 0;
	}
	
	boolean isEdge(int v, int w) { // returns whether there is a connection between vertex v and vertex w
		return (matrix[v][w] != 0);
	}
	
	int degree(int v) { // returns how many edges depart from vertex v
		int number = 0;
		for(int i = 0; i < matrix.length; i++) {
			if(matrix[v][i] != 0) {
				number++;
			}
		}
		return number;
	}
	
	int getMark(int v) { // returns any graph coloring for this vertex
		return marks[v];
	}
	
	void setMark(int v, int m) { // colors vertex v color m
		marks[v] = m;
	}
}
