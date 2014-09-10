import java.util.ArrayList;
import java.util.List;

public class DotsAndBoxes {
	private Graph graph;
	private int rowSize, columnSize;
	
	public DotsAndBoxes(int rows, int columns) {
		rowSize = rows+2;
		columnSize = columns+2;
		
		graph = new Graph(rowSize * columnSize);
		initializeGraphEdges(rowSize, columnSize);
	}
	
	private void initializeGraphEdges(int rows, int columns) {
		int limit = graph.vcount() - rows;
		
		for(int i = 0; i < limit; i++) {
			//Initialize |
			//   columns v
			if(i % rows != 0 && i % rows != (rows-1)) {
				graph.addEdge(i, i+rows, 1);
			}
			//Initialize rows ->
			if(i >= rows) {
				if((i+1) % rows != 0) {
					graph.addEdge(i, i+1, 1);
				}
			}
			
			//System.out.println(i + ": " + graph.degree(i));
		}
	}
	
	// draws a line from (x1, y1) to (x2, y2) (0,0) is in the upper-left corner, returning how many points were earned, if any
	public int drawLine(int player, int x1, int y1, int x2, int y2) {
		//(x2, y2) //second coordinate determines starting point
		int startingNode = (y2 * rowSize) + x2;
		//(x1, y1) //first coordinate determines target
		int targetNode = 0;
		
		if(x1 - x2 != 0) { //horizontal line, but vertical edge deletion
			targetNode = startingNode + rowSize;
		} 
		else { //vertical draw, but horizontal edge deletion
			targetNode = startingNode + 1;
		}
		
		int points = 0;
		
		if(graph.isEdge(startingNode, targetNode)) {
			graph.removeEdge(startingNode, targetNode);
		}
		
		if(graph.degree(targetNode) == 0) {
			if(targetNode < graph.vcount()-rowSize && targetNode % rowSize != (rowSize-1)) { //don't give points for padding verts
				graph.setMark(targetNode, player);
				points++;
			}
		}
		if(graph.degree(startingNode) == 0) {
			if(startingNode > rowSize && startingNode % rowSize != 0) { //don't give points for padding verts
				graph.setMark(startingNode, player);
				points++;
			}
		}
		
		return points;
	}
	
	public int score(int player) { // returns the score for a player
		int score = 0;
		for(int i = 0; i < graph.vcount(); i++) {
			if(graph.getMark(i) == player) {
				score++;
			}
		}
		return score;
	}
	
	public boolean areMovesLeft() { // returns whether or not there are any lines to be drawn
		return (graph.ecount() > 0);
	}
	
	public int countDoubleCrosses() { // returns the number of double-crosses on the board
		DFSGraphTraversal dfs = new DFSGraphTraversal();
		List<List<Integer>> result = dfs.traverse(graph);
		
		int count = 0;
		for(int i = 0; i < result.size(); i++) {
			List<Integer> component = result.get(i);
			if(component.size() == 2) {
				count++;
			}
		}
		return count;
	}
	
	public int countCycles() { // returns the number of cycles on the board
		DFSGraphTraversal dfs = new DFSGraphTraversal();
		List<List<Integer>> result = dfs.traverse(graph);
		
		int count = 0;
		for(int i = 0; i < result.size(); i++) {
			List<Integer> component = result.get(i);
			
			int size = component.size();
			boolean cycle = (size % 2 == 0) ? true : false;
			for(int j = 0; j < size; j++) {
				if(graph.degree(component.get(j)) != 2) { //not a cycle if a vertex has more than two edge
					cycle = false;
					j = component.size();
				}
			}
			
			if(cycle) {
				int componentStartPoint = component.get(0);
				int componentLastVertex = component.get(component.size()-1);
				if(graph.isEdge(componentStartPoint, componentLastVertex)) { //if first and last verts in component are connected, cycle
					count++;
				}
			}
		}
		return count;
	}
	
	public int countOpenChains() {  // returns the number of open chains on the board
		DFSGraphTraversal dfs = new DFSGraphTraversal();
		List<List<Integer>> result = dfs.traverse(graph);
		
		int count = 0;
		for(int i = 0; i < result.size(); i++) {
			List<Integer> component = result.get(i);
			
			int size = component.size();
			
			if(size > 2) {
				List<Integer> chainStartPoints = new ArrayList<Integer>();
				int chainLength = 0;
				int componentFirst = -1;
				int componentLast = -1;
				
				for(int j = 0; j < size; j++) {
					int currentVert = component.get(j);
					
					if(chainLength == 0) {
						if(graph.degree(currentVert) == 2) {
							componentFirst = currentVert;
							chainLength++;
						}
					}
					else {
						chainLength++;
						if(graph.degree(currentVert) != 2) { //cycle ends if current vert has more or less than 2 edges
							componentLast = component.get(j-1);
							if(checkChain(componentFirst, componentLast, chainLength-1, chainStartPoints)) {
								chainStartPoints.add(componentFirst);
								count++;
							}
							chainLength = 0;
						}
					}
				}
				
				boolean newVert = false;
				if(componentFirst == -1) {
					componentFirst = component.get(0);
					newVert = true;
				}
				if(componentLast == -1) {
					componentLast = component.get(component.size()-1);
					newVert = true;
				}
				if(newVert) {
					if(checkChain(componentFirst, componentLast, chainLength, chainStartPoints)) {
						chainStartPoints.add(componentFirst);
						count++;
					}
				}
			}
		}
		return count;
	}
	
	public boolean checkChain(int first, int last, int length, List<Integer> previousChains) {
		System.out.println("Start: " + first);
		System.out.println("End: " + last);
		for(int i = 0; i < previousChains.size(); i++) {
			if( graph.isEdge(last, previousChains.get(i)) ) {
				return false;
			}
		}
		return (!graph.isEdge(first, last) && length > 2); //if first and last are not connected, not a cycle
	}
}
