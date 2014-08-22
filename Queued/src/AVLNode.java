public class AVLNode<T> {
	public AVLNode<T> left;
	public AVLNode<T> right;
	
	public AVLNode<T> parent;
	
	private T value;
	private int balance;
	
	public AVLNode(T value) {
		this.value = value;
		this.balance = 0;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int value) {
		this.balance = value;
	}
	
	public String toString() {
		String result = "";
		 if(parent == null) {
			result += "Parent: None (Root)\n";
		} else {
			result += "Parent: " + parent.getValue() + "\n";
		}
		 
		result += "Value: " + value + "\n";
		 
		boolean extraSpace = false;
		if(left != null) {
			result += "Left:  " + left.getValue() + "\n";
			extraSpace = true;
		} if(right != null) {
			result += "Right: " + right.getValue();
			extraSpace = true;
		}
		
		if(extraSpace) {
			result += "\n";
		}
		
		return result;
	}
}
