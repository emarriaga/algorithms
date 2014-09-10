import java.util.List;

public class Driver {
	public static void main(String[]args) {
		//DFSTest();
		//BFSTest();
		//DotBox();
	}
	
	public static void DotBox() {
		DotsAndBoxes db = new DotsAndBoxes(2,2);
	}
	
	public static void DFSTest() {
		Graph g = new Graph(6);
		DFSGraphTraversal dfs = new DFSGraphTraversal();
		
		g.addEdge(0, 1, 1);
		g.addEdge(0, 4, 1);
		g.addEdge(1, 2, 1);
		g.addEdge(2, 4, 1);
		g.addEdge(3, 5, 1);
		
		List<List<Integer>> result = dfs.traverse(g);
		System.out.println("DFS TEST");
		printListOfLists(result);
	}
	
	public static void BFSTest() {
		Graph g = new Graph(6);
		BFSGraphTraversal bfs = new BFSGraphTraversal();
		
		g.addEdge(0, 1, 1);
		g.addEdge(0, 4, 1);
		g.addEdge(1, 2, 1);
		g.addEdge(2, 4, 1);
		g.addEdge(3, 5, 1);
		
		List<List<Integer>> result = bfs.traverse(g);
		System.out.println("BFS TEST");
		printListOfLists(result);
	}
	
	public static void printListOfLists(List<List<Integer>> list) {
		String print = "";
		for(int i = 0; i < list.size(); i++) {
			print += "Component " + (i+1) + ": ";
			List<Integer> component = list.get(i);
			for(int j = 0; j < component.size(); j++) {
				print += component.get(j);
				if(j < component.size()-1) {
					print += " -> ";
				}
			}
			print += "\n";
		}
		System.out.println(print);
	}
}
