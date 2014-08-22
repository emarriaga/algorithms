public class HuffmanCompressor {
	
	public byte[] compress(HuffmanTree tree, byte[] b) {
		Bits bit = new Bits();
		
		for(int i = 0; i < b.length; i++) {
			tree.fromByte(b[i], bit);
		}
		
		int compressedSize = bit.size()/8;
		compressedSize += (bit.size() % 8 == 0) ? 0 : 1; //if there are extra bits, add another byte
		
		byte[] compressed = new byte[compressedSize];
		
		int size = bit.size();
		String temp = "";
		for(int i = 0; i < size; i++) {
			int parsed = (bit.poll()) ? 1 : 0;
			temp += parsed;
			if(i % 7 == 0 && i != 0) {
				temp += " ";
			}
		}
		
		//System.out.println(temp);
		
		String [] ready = temp.split(" ");
		
		//add padding
		int paddingNeeded = 8 - ready[ready.length-1].length();
		if(paddingNeeded != 0) {
			for(int i = 0; i < paddingNeeded; i++) {
				ready[ready.length-1] += "0";
			}
		}
		
		for(int i = 0; i < compressedSize; i++) {
			compressed[i] = (byte)Integer.parseInt(ready[i], 2);
			//System.out.println(String.format("%8s", Integer.toBinaryString(compressed[i] & 0xFF)).replace(' ', '0'));
			//System.out.println(compressed[i]);
		}
		
		return compressed;
	}
	
	public byte[] decompress(HuffmanTree tree, int uncompressedLength, byte[] b) {
		Bits bit = new Bits();
		
		String temp = "";
		for(int i = 0; i < b.length; i++) {
			temp += String.format("%8s", Integer.toBinaryString(b[i] & 0xFF)).replace(' ', '0');
		}
		
		for(int i = 0; i < temp.length(); i++) {
			bit.add((temp.charAt(i) == '1') ? true : false);
		}
			
		byte[] result = new byte[uncompressedLength];
		
		for(int i = 0; i < result.length; i++) {
			result[i] = tree.toByte(bit);
		}
		
		return result;
	}
}
