public class Graph {
	private final int DNE = 0;
	private int pathMatrix[][];
	private int marks[];
	
	public Graph(int numVertices) {
		pathMatrix = new int[numVertices][numVertices];
		marks = new int[numVertices];
		
		int size = pathMatrix.length;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				pathMatrix[i][j] = DNE;
			}
			marks[i] = 0;
		}
	}
	
	public Graph(Graph graph) {
		pathMatrix = graph.getGraph();
		marks = new int[graph.size()];
		
		for(int i = 0; i < graph.size(); i++) {
			marks[i] = 0;
		}
	}
	
	public void visit(int vertex) {
		marks[vertex] = 1;
	}
	
	public boolean isVisited(int vertex) {
		return (marks[vertex] == 1);
	}
	
	public boolean checkEdge(int fromVertex, int toVertex) {
		return (pathMatrix[fromVertex][toVertex] != DNE);
	}
	
	public void addEdge(int fromVertex, int toVertex) {
		pathMatrix[fromVertex][toVertex] = 1;
	}
	
	public void removeEdge(int fromVertex, int toVertex) {
		pathMatrix[fromVertex][toVertex] = DNE;
	}
	
	public int size() {
		return pathMatrix.length;
	}
	
	public int[][] getGraph() {
		return pathMatrix.clone();
	}
	
	public String toString() {
		String result = "       ";
		
		for(int i = 0; i < pathMatrix.length; i++) {
			result += i + "  ";
		}
		result += "\n      ";
		for(int i = 0; i < pathMatrix.length; i++) {
			result += " - ";
		}
		result += "\n";
		
		for(int i = 0; i < pathMatrix.length; i++) {
			result +=  i + " | [  ";
			for(int j = 0; j < pathMatrix.length; j++) {
				result += pathMatrix[i][j] + "  ";
			}
			result += "]\n";
		}
		
		return result;
	}
}
