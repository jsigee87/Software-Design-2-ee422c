package polymorphism;

public class LinkedListNodeClass<T> {
	private T value;
	private LinkedListNodeClass<T> next;
	
	public LinkedListNodeClass(T obj){
		this.value = (T) value;
	}
	

	@SuppressWarnings("unchecked")
	public void setNext(LinkedListNodeClass<T> node) {
		if (node != null) {
			this.next = node;
		} 
		else {
			//throw error
		}
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	public LinkedListNodeClass<T> getNext() {
		return next;
	}


}
