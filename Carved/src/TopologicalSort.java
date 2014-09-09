public class TopologicalSort {
	private int[] visitOrder;
	private int currentVisited;
	
	//Topo sort by removal
	public TopologicalSort() {
		currentVisited = 0;
	}
	
	public int[] sort(Graph dag) {
		Graph tempGraph = new Graph(dag);
		visitOrder = new int[dag.size()];
		
		removeSources(tempGraph);
		
		return visitOrder;
	}
	
	private void removeSources(Graph dag) {
		while(currentVisited < dag.size()) { //while not all vertices have been visited
			int entryPoint = 0; //start at 0
			boolean validEdge;
			
			//Find a vertex with no incoming edges
			do {
				validEdge = true; //assume edge is valid
				for(int i = 0; i < dag.size(); i++) {
					if(dag.checkEdge(i, entryPoint) || dag.isVisited(entryPoint)) { //if an incoming edge is valid or the target vertex has been visited;
						i = dag.size(); //exit loop;
						validEdge = false;
						entryPoint++;
					}
				}
			} while(entryPoint < dag.size() && !validEdge);
			
			//if no vertex without incoming edge (source) is found, no solution.
			if(entryPoint == dag.size()) {
				visitOrder[currentVisited] = -1;
				currentVisited = dag.size();
			} else {
				visitOrder[currentVisited] = entryPoint;
				dag.visit(entryPoint);
				currentVisited++;
				for(int i = 0; i < dag.size(); i++) {
					dag.removeEdge(entryPoint, i);
				}
			}
		}
	}
}
