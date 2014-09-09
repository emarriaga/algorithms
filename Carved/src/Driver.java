public class Driver {
	
	public static void main(String[] args) {
		//TopoSortTest();
		carve();
	}
	
	//Milestone1
	public static void TopoSortTest() {
		//Make the graph
		Graph dag = new Graph(5);
		
		// a = 0, b = 1, c = 2, d = 3, e = 4
		dag.addEdge(0, 1); //a -> b
		dag.addEdge(0, 2); //a -> c
		dag.addEdge(1, 3); //b -> d
		dag.addEdge(3, 2); //d -> c
		dag.addEdge(2, 4); //c -> e
		//dag.addEdge(4, 1); //e -> d (CREATES A CYCLE)
		
		//Print out the graph
		System.out.println(dag.toString());
		
		TopologicalSort sort = new TopologicalSort();
		
		//Sort the graph
		int[] result = sort.sort(dag);

		//Print out the result
		String print = "";
		for(int i = 0; i < result.length; i++) {
			print += result[i];
			if(i < result.length-1) {
				print += " -> ";
			}
		}
		
		System.out.println(print);
	}

	public static void carve() {
		Picture pic = new Picture("overlayimagewithhiddenmessage.png");
		SeamCarver carver = new SeamCarver(pic);
		
		for(int i = 0; i < 190; i++) {
			int [] array = carver.findVerticalSeam();
			carver.removeVerticalSeam(array);
		}
		
		for(int i = 0; i < 165; i++) {
			int [] array = carver.findHorizontalSeam();
			carver.removeHorizontalSeam(array);
		}
		
		
		carver.getPicture().show();
	}
}
