import java.util.ArrayList;
import java.util.List;

public class DFSGraphTraversal {
	private static final int VISITED = 1;
	private static final int UNVISITED = 0;
	
	private int[] marks;
	
	public List<List<Integer>> traverse(Graph g) {
		
		List<List<Integer>> allComponents = new ArrayList<List<Integer>>();
		marks = new int[g.vcount()];
		
		for(int i = 0; i < g.vcount(); i++) {
			if(marks[i] == UNVISITED) {
				List<Integer> component = new ArrayList<Integer>();
				DFS(g, i, component);
				allComponents.add(component);
			}
		}
		
		return allComponents;
	}
	
	private void DFS(Graph g, int v, List<Integer> list) {
		list.add(v);
		marks[v] = VISITED;
		for(int i = g.first(v); i < g.vcount(); i = g.next(v, i)) {
			if(marks[i] == UNVISITED) { 
				DFS(g, i, list);
			}
		}
	}
}
