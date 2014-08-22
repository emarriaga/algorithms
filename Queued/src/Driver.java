public class Driver {

	public static void main(String[] args) {
		avlTest();
		//heapTest();
	}
	
	public static void avlTest() {
		AVLQueue<Integer> q = new AVLQueue<Integer>();
		q.offer(5);
		q.offer(7);
		q.offer(3);
		q.offer(6);
		q.offer(4);
		q.offer(2);
		q.offer(1);
		//q.print();
		System.out.println(q.poll());
		//q.print();
		System.out.println(q.poll());
		//q.print();
		System.out.println(q.poll());
		//q.print();
		System.out.println(q.poll());
		//q.print();
		System.out.println(q.poll());
		//q.print();
	}
	
	public static void heapTest() {
		HeapQueue<Integer> q = new HeapQueue<Integer>(6);
		
		q.offer(2);
		q.print();
		q.offer(4);
		q.print();
		q.offer(3);
		q.print();
		q.offer(5);
		q.print();
		q.offer(1);
		q.print();
		q.offer(7);
		q.print();
		q.poll();
		q.print();
		q.poll();
		q.print();
		q.poll();
		q.print();
		q.poll();
		q.print();
		q.poll();
		q.print();
	}
}
