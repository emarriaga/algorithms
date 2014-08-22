
public class HuffNode {
	private byte[] childData;
	private int leftChildren;
	
	private byte data;
	public HuffNode left;
	public HuffNode right;
	private int weight;
	
	public HuffNode(int weight) {
		this.weight = weight;
	}
	
	public HuffNode(byte key, int weight) {
		this.data = key;
		this.weight = weight;
		childData = new byte[1];
		childData[0] = key;
	}
	
	public int getWeight() {
		return weight;
	}
	public byte getKey() {
		return data;
	}
	
	public void addChildrenData() {
		addChildKeys(left.childData);
		addChildKeys(right.childData);
		leftChildren = left.childData.length;
	}
	
	private void addChildKeys(byte[] keys) {
		int currentLength = 0;
		if(childData != null) {
			currentLength = childData.length;
		}
		
		int newLength = currentLength + keys.length;
		
		byte[] newData = new byte[newLength];

		for(int i = 0; i < currentLength; i++) {
			newData[i] = childData[i];
		}
		
		int j = 0;
		for(int i = currentLength; i < newLength; i++) {
			newData[i] = keys[j++];
		}
		childData = newData;
	}
	
	//returns -1 if nothing is found, 0 if in left subtree, and 1 if in right subtree.
	public int search(byte b) {
		int onRight = -1;
		
		for(int i = 0; i < childData.length; i++) {
			if(b == childData[i]) {
				onRight = (i+1 > leftChildren) ? 1 : 0;
			}
		}
		return onRight;
	}
	
	public boolean isLeaf() {
		return (left == null && right == null);
	}
	
	public String getContainedValues() {
		String result = "(";
		
		for(int i = 0; i < childData.length; i++) {
			result += childData[i];
			if(i < childData.length-1) {
				result += ", ";
			}
		}
		result += ")";

		return result;
	}
	
	public String toString() {
		String result = "";
		result += "Frequency:" + weight + "";
		result += " Key(s): " + getContainedValues();
		
		return result;
	}
}
