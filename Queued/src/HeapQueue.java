import java.util.ArrayList;

public class HeapQueue<T extends Comparable<T>> {
	ArrayList<T> heap;
	private int size;
	private int actualSize;

	public HeapQueue(int initialSize) {
		heap = new ArrayList<T>();
		heap.ensureCapacity(initialSize);
		heap.add(0, null);
		
		actualSize = initialSize;
		size = 0;
	}
	
	public void print() {
		System.out.println(heap.toString());
	}
	
	private void resize() {
		actualSize = actualSize * 2;
		heap.ensureCapacity(actualSize);
	}

	public boolean offer(T data) {
		if(size == actualSize) {
			resize();
		}
		
		boolean result = offer(data, 1);
		
		if(size > 1) {
			rebalanceUp(size, size/2);
		}
		
		return result;
	}
	
	private boolean offer(T data, int index) {
		if(index > size) {
			heap.add(index, data);
			size++;
			return true;
		}
		
		if(heap.get(index).compareTo(data) == 0) {
			return false;
		}
		
		return offer(data, index+1);
	}
	
	//going "up" the heap
	private void rebalanceUp(int index, int parentIndex) {
		if(parentIndex == 0) { return; }
		
		int comparison = heap.get(index).compareTo(heap.get(parentIndex)); //comparing child to parent
		
		if(comparison < 0) { return; }//if child is less than parent
		
		swap(index, parentIndex);
		
		rebalanceUp(parentIndex, parentIndex/2);
	}
	
	// see, but do not remove the highest priority value
	public T peek() {
		return heap.get(1);
	}
	
	//remove and return the highest priority value
	public T poll() {
		T result = heap.get(1);
		swap(1, size);
		heap.remove(size);
		size--;
		
		rebalanceDown(1);
		
		return result;
	}
	
	//going "down" the heap
	private void rebalanceDown(int index) {
		int leftIndex = index * 2;
		int leftComparison  = (leftIndex < size) ? heap.get(index).compareTo(heap.get(leftIndex)) : 0;
		int rightIndex = leftIndex + 1;
		int rightComparison = (rightIndex <= size) ? heap.get(index).compareTo(heap.get(rightIndex)) : 0;
		
		if(leftComparison >= 0 && rightComparison >= 0) { return; } //if both children are smaller than the parent
		
		int newIndex = (heap.get(leftIndex).compareTo(heap.get(rightIndex)) > 0) ? leftIndex : rightIndex;
		
		swap(index, newIndex);
		rebalanceDown(newIndex);
	}
	
	private void swap(int first, int second) {
		T temp = heap.get(first);
		heap.set(first, heap.get(second));
		heap.set(second, temp);
	}
}
