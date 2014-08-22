public class Driver {

	public static void main(String[] args) {
		byte [] data = {-3, 1, 5, 1, 5, 5, 7, 8, 4, 8};
		
		HuffmanTree tree = new HuffmanTree(data);
		tree.printTree();
		
		byte [] toPress = {5, 5, 1, -3, 5};
		
		HuffmanCompressor presser = new HuffmanCompressor();
		
		byte[] compressed = presser.compress(tree, toPress);
		
		System.out.println("Compressed:");
		for(int i = 0; i < compressed.length; i++) {
			System.out.print(compressed[i] + ", ");
		}
		
		byte[] uncompressed = presser.decompress(tree, 5, compressed);

		System.out.println("\nUncompressed:");
		for(int i = 0; i < uncompressed.length; i++) {
			System.out.print(uncompressed[i] + ", ");
		}
	}

}
