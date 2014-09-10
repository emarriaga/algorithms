import java.util.ArrayList;
import java.util.List;

public class BFSGraphTraversal {
	private static final int VISITED = 1;
	private static final int UNVISITED = 0;
	
	private int[] marks;

	public List<List<Integer>> traverse(Graph g) {
		List<List<Integer>> allComponents = new ArrayList<List<Integer>>();
		
		marks = new int[g.vcount()];
		
		for(int i = 0; i < g.vcount(); i++) {
			if(marks[i] == UNVISITED) {
				List<Integer> component = new ArrayList<Integer>();
				BFS(g, i, component);
				allComponents.add(component);
			}
		}
		
		return allComponents;
	}
	
	private void BFS(Graph g, int v, List<Integer> visitOrder) {
		List<Integer> neighbors = new ArrayList<Integer>();
		
		if(marks[v] == UNVISITED) {
			marks[v] = VISITED;
			visitOrder.add(v);
		}
		
		for(int i = g.first(v); i < g.vcount(); i = g.next(v, i)) {
			if(marks[i] == UNVISITED) {
				marks[i] = VISITED;
				neighbors.add(i);
				visitOrder.add(i);
			}
		}
			
		for(int i = 0; i < neighbors.size(); i++) {
			BFS(g, neighbors.get(i), visitOrder);
		}
	}
}
