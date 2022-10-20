package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> implements Iterable<T> {
	
	private ArrayDeque<T> arrayDeque;
	private Comparator<T> comparator;
	
	public MaxArrayDeque(Comparator<T> c) {
		this.arrayDeque = new ArrayDeque<T>();
		this.comparator = c;
	}
	
	public T max() {
		return this.max(this.comparator);
	}
	
	public T max(Comparator<T> c) {
		if (this.isEmpty()) { return null; }
		T max_value = this.arrayDeque.get(0);
		Comparator<T> comparator = c;
		
		int i = 0;
		while (i < this.size()) {
			T item = this.arrayDeque.get(i);
			int comp = comparator.compare(item, max_value);
			if (comp >= 0) {
				max_value = item;
			}
			
			i++;
		}
		
		return max_value;
	}
	
	public void addFirst(T item) { this.arrayDeque.addLast(item); }
	
	public void addLast(T item) { this.arrayDeque.addLast(item); }
	
	private void resize(int capacity) { this.arrayDeque.resize(capacity); }
	
	public boolean isEmpty() { return this.arrayDeque.isEmpty(); }
	
	public int size() { return this.arrayDeque.size(); }
	
	public void printDeque() { this.arrayDeque.printDeque(); }
	
	public T removeFirst() { return this.arrayDeque.removeFirst(); }
	
	public T removeLast() { return this.arrayDeque.removeLast(); }
	
	private void resetStartPoint() { this.arrayDeque.resetStartPoint(); }
	
	public T get(int index) { return this.arrayDeque.get(index); }
	
	public Iterator<T> iterator() { return this.arrayDeque.iterator(); }
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (!(o instanceof MaxArrayDeque)) {
			return false;
		}
		
		MaxArrayDeque<T> MAD = (MaxArrayDeque<T>) o;
		
		if (MAD.size() != this.size()) {
			return false;
		}
		
		for (int i = 0; i < this.size(); i++) {
			if (MAD.get(i) != this.get(i)) {
				return false;
			}
		}
		
		return true;
	}
}
