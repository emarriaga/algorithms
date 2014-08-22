import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class HuffmanTree {
	private HuffNode root;
	private List<HuffNode> nodes;
	
	/*
	 * Absolute Best Case (All Data is Uniform):
	 * 		n operations making hashmap
	 * 		1 operation making node
	 * 		1 operation organizing and adding node to list
	 * 		1 operation making the tree
	 * 
	 * Absolute Best Case Operations : n
	 * 
	 */
	
	/*
	 * Absolute Worst Case (All Data is Different):
	 * 		n operations in making hashmap
	 * 		n operations making every key into a node * n operations (adding node into list, with nodes in opposite order)
	 * 		n-1 operations making tree
	 * 		
	 * Absolute Worst Case Operations : n + (n^2) + n-1 ---> n^2 + 2n - 1 ---> n^2
	 */
	
	public HuffmanTree(byte [] data) {
		nodes = new ArrayList<HuffNode>();
		fillList( mapFrequency(data) );
		sortList();
		makeTree();
	}
	
	//byte array of size n
	//for loop goes through whole array and performs one operation
	//number of operations : n * 1
	private HashMap<Byte, Integer> mapFrequency(byte [] data) {
		HashMap<Byte, Integer> map = new HashMap<Byte, Integer>();
		for(int i = 0; i < data.length; i++) {
			if(map.containsKey(data[i])) {
				map.put(data[i], map.get(data[i])+1 );
			} else {
				map.put(data[i], 1);
			}
		}
		return map;
	}
	
	//while loop goes for n (n being the number of unique values)
	//one operation
	//number of operations : n * 1
	private void fillList(HashMap<Byte, Integer> map) {
		Iterator<Byte> values = map.keySet().iterator();
		
		while(values.hasNext()) {
			byte val = values.next();
			HuffNode node = new HuffNode(val, map.get(val));
			nodes.add(node);
		}
	}
	
	//while loop is performed n times (n being number of unique values)
	//number of operations: n * (addNode's performance)
	private void sortList() {
		List<HuffNode> organized = new ArrayList<HuffNode>();
		Iterator<HuffNode> iterator = nodes.iterator();
		
		organized.add(iterator.next());
		
		while(iterator.hasNext()) {
			addNode(iterator.next(), organized);
		}
		
		nodes = organized;
	}

	public HuffNode getTree() {
		return root;
	}
	
	//worst case: for loop goes through entire list.(n)
	//best case: for loop goes through one time. (1)
	private void addNode(HuffNode node, List<HuffNode> list) {
		boolean added = false;
		for(int i = 0; i < list.size(); i++) {
			if(node.getWeight() <= list.get(i).getWeight()) {
				list.add(i, node);
				i = list.size();
				added = true;
			}
		}
		if(!added) {
			list.add(node);
		}
	}
	
	//list starts
	//Number of operations : n-1 * 1
	private void makeTree() {
		while(nodes.size() > 1) {
			HuffNode leftNode = nodes.get(0);
			HuffNode rightNode = nodes.get(1);
				
			HuffNode joint = new HuffNode(leftNode.getWeight() + rightNode.getWeight());
				
			joint.left = leftNode;
			joint.right = rightNode;
			joint.addChildrenData();
				
			nodes.remove(leftNode);
			nodes.remove(rightNode);
				
			addNode(joint, nodes); //n/2
		}
		root = nodes.get(0);
	}
	
	//Used for printing
	public void printTree() {
		preOrder(root);
	}
	
	private void preOrder(HuffNode node) {
		if(node == null) {
			return;
		}
		System.out.println(node.toString());
		preOrder(node.left);
		preOrder(node.right);
	}

	// reads the next byte from a queue of bits by traversing the Huffman tree
	public byte toByte(Bits bits) {
		HuffNode node = root;
		
		while(!node.isLeaf()) {
			node = (bits.poll()) ? node.right : node.left;
		}
		
		return node.getKey();
	}

	// writes the bits indicated by the Huffman tree for the given byte
	public void fromByte(byte b, Bits bits) {
		HuffNode node = root;
		int result;
		while(!node.isLeaf()) {
			result = node.search(b);
			
			if(result != -1) {
				boolean direction = (result > 0) ? true : false; //Right : Left
				bits.addLast(direction);
				node = (direction) ? node.right : node.left;
			}
		}
	}
}