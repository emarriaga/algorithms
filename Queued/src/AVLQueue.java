public class AVLQueue<T extends Comparable<T>> {
	private AVLNode<T> root;
	
	public AVLQueue() {
		root = new AVLNode<T>(null);
		root.parent = null;
	}

	public boolean offer(T data) {
		boolean result = offer(data, root); // log n
		balance(); //
		return result;
	}
	
	//placement possibilities are split in half each call. average case (log n)
	private boolean offer(T data, AVLNode<T> node) {
		if(node.getValue() == null) {
			node.setValue(data);
			return true;
		}
		
		int comparison = data.compareTo(node.getValue());
		if(comparison == 0) { return false; }
		
		if(comparison < 0) {
			if(node.left == null) {
				node.left = new AVLNode<T>(null);
				node.left.parent = node;
			}
		} else {
			if(node.right == null) {
				node.right = new AVLNode<T>(null);
				node.right.parent = node;
			}
		}
		
		AVLNode<T> next = (comparison < 0) ? node.left : node.right;
		
		return offer(data, next);
	}
	
	private void balance() {
		calculateBalance(root);
		//System.out.println();
	}
	
	//function calls n times (goes through every node)
	//always checks height of both subtrees (2n)?
	private void calculateBalance(AVLNode<T> node) {
		if(node == null) {
			return;
		}
		
		calculateBalance(node.left);
		calculateBalance(node.right);
		
		node.setBalance(getHeight(node.left) - getHeight(node.right)); //2n
		if(node.getBalance() <= -2 || 2 <= node.getBalance()) {
			//System.out.println("Pre Rotation:   " + node.getValue() + ": " + node.getBalance());
			performRotation(node); //constant
		}
		//System.out.println(node.getValue() + ": " + node.getBalance());
	}
	
	//n
	public int height() {
		return getHeight(root);
	}
	
	//performs n operations (checks every node)
	private int getHeight(AVLNode<T> node) {
		if(node == null) {
			return -1;
		}
		return Math.max( getHeight(node.left), getHeight(node.right) ) + 1;
	}
	
	//all rotations are constant time
	private void performRotation(AVLNode<T> node) {
		if(node.getBalance() >= 2) {
			if(node.left.getBalance() == -1) {
				leftRightRotation(node);
			} else {
				rightRotation(node);
			}
		} else {
			if(node.right.getBalance() == 1) {
				rightLeftRotation(node);
			} 
			else {
				leftRotation(node);
			}
		}
	}
	
	private void rightLeftRotation(AVLNode<T> groot) {
		rightRotation(groot.right);
		leftRotation(groot);
	}
	
	private void leftRightRotation(AVLNode<T> groot) {
		leftRotation(groot.left);
		rightRotation(groot);
	}
	
	private void leftRotation(AVLNode<T> groot) {
		AVLNode<T> newLeft = groot;
		AVLNode<T> newRoot = groot.right;
		AVLNode<T> rootParent = groot.parent;
		
		newLeft.right = null;
		if(newRoot.left != null) {
			newLeft.right = newRoot.left;
			newLeft.right.parent = newLeft;
		}
		newRoot.left = newLeft;
		newLeft.parent = newRoot;

		
		if(rootParent == null) {
			root = newRoot;
		} else {
			if(rootParent.left == groot) {
				rootParent.left = newRoot;
			} else {
				rootParent.right = newRoot;
			}
		}
		newRoot.parent = rootParent;
	}
	
	private void rightRotation(AVLNode<T> groot) {
		AVLNode<T> newRight = groot;
		AVLNode<T> newRoot = groot.left;
		AVLNode<T> rootParent = groot.parent;
		
		newRight.left = null;
		if(newRoot.right != null) {
			newRight.left = newRoot.right;
			newRight.left.parent = newRight;
		}
		newRoot.right = newRight;
		newRight.parent = newRoot;

		
		if(rootParent == null) {
			root = newRoot;
		} else {
			if(rootParent.left == groot) {
				rootParent.left = newRoot;
			} else {
				rootParent.right = newRoot;
			}
		}
		newRoot.parent = rootParent;
	}
	
	public void print() {
		preorder(root);
	}
	
	//n
	private void preorder(AVLNode<T> node) {
		if(node == null) { return; }
		
		System.out.println(node.toString());
		preorder(node.left);
		preorder(node.right);
	}
	
	//constant
	public T peek() {
		return root.getValue();
	}
	
	
	public T poll() {
		T value = getHighest(root);
		balance();
		return value;
	}
	
	//constant
	private void deleteNode(AVLNode<T> node) {
		if(node.left != null) {
			node.parent.right = node.left;
			node.left.parent = node.parent;
		} else {
			node.parent.right = null;
		}
		
		node.parent = null;
	}
	
	//goes all the way to the right (half the tree). log n
	private T getHighest(AVLNode<T> node) {
		if(node.right == null) {
			T value = node.getValue();
			deleteNode(node);
			return value;
		}
		return getHighest(node.right);
	}
}
