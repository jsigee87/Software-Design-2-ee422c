package polymorphism;

public class LinkedList<T> {
	private LinkedListNodeClass<T> head;
	
	@SuppressWarnings("unchecked")
	public void insert(LinkedListNodeClass<T> node) {
		if (head != null) {
			LinkedListNodeClass<T> ptr = head;
			while(((LinkedListNodeClass<T>) ptr).getNext() != null) {
				ptr = (LinkedListNodeClass<T>) ptr.getNext();
			}
			ptr.setNext(node);
			node.setNext(null);
		}
		else {
			head = node;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void remove() {
		head = (LinkedListNodeClass<T>) head.getNext();
	}
	
	@SuppressWarnings("unchecked")
	public void printList() {
		LinkedListNodeClass<T> ptr = head;
		System.out.println(head);
		while (ptr.getNext() != null) {
			ptr = (LinkedListNodeClass<T>) ptr.getNext();
			System.out.println(ptr);
		}
	}
}
