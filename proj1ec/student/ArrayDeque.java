package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T> {

	private T[] items;
	private int size;
	private int nextFirst;
	private int nextLast;
	private int start;
	
	public ArrayDeque() {
		this.size = 0;
		this.items = (T[]) new Object[8];
		int point = this.items.length / 2;
		this.nextFirst = point;
		this.nextLast = point;
		this.start = point;
	}
	
	public void addFirst(T item) {
		if (this.items.length == this.size()) {
			this.resize(this.items.length * 2);
		}
		
		if (this.size() == 0) {
			this.items[this.nextFirst] = item;
			this.nextLast++;
			this.nextFirst--;
			this.size++;
			return;
		}
		
		this.items[this.nextFirst] = item;
		this.nextFirst--;
		this.start--;
		if (this.nextFirst < 0) {
			this.nextFirst = this.items.length - 1;
		}
		
		if (this.start < 0) {
			this.start = this.items.length - 1;
		}
		
		this.size++;
	}
	
	public void addLast(T item) {
		if (this.items.length == this.size()) {
			this.resize(this.items.length * 2);
		}
		
		if (this.size() == 0) {
			this.items[this.nextLast] = item;
			this.nextLast++;
			this.nextFirst--;
			this.size++;
			return;
		}
		
		this.items[this.nextLast] = item;
		this.nextLast++;
		if (this.nextLast > this.items.length - 1) {
			this.nextLast = 0;
		}
		
		this.size++;
	}
	
	private void resize(int capacity) {
		T[] resized_array = (T[]) new Object[capacity];
		int new_start_pt = resized_array.length / 2;
		System.arraycopy(this.items, this.start, resized_array, new_start_pt, this.size);
		this.items = resized_array;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	public int size() {
		return this.size;
	}
	
	public void printDeque() {
		int i = 0;
		int j = this.start;
		while (i <= this.size() - 1) {
			if (j >= this.items.length) {
				j = 0;
			}
			if (j == this.nextLast - 1) {
				System.out.println(this.items[j]);
				return;
			}
			System.out.print(this.items[j] + " ");
			i++;
			j++;
		}
	}
	
	public T removeFirst() {
		if (this.isEmpty()) { return null; }
		
		int remove_index = this.start;
		if (remove_index >= this.items.length) {
			remove_index = 0;
			this.start = 0;
		}
		
		T value = this.items[remove_index];
		this.items[remove_index] = null;
		this.size--;
		this.start++;
		this.nextFirst = this.start - 1;
		if (this.isEmpty()) { this.resetStartPoint(); }
		
		double factor = this.size() / this.items.length;
		if (factor < 0.25 && this.items.length >= 16) {
			this.resize(this.items.length / 2);
		}
		
		return value;
	}
	
	public T removeLast() {
		if (this.isEmpty()) { return null; }
		
		this.nextLast--;
		int remove_index = this.nextLast;
		if (remove_index < 0) {
			remove_index = this.items.length - 1;
			this.nextLast = remove_index;
		}
		
		T value = this.items[remove_index];
		this.items[remove_index] = null;
		this.size--;
		if (this.isEmpty()) { this.resetStartPoint(); }
		
		double factor = this.size() / this.items.length;
		if (factor < 0.25 && this.items.length >= 16) {
			this.resize(this.items.length / 2);
		}
		
		return value;
	}
	
	private void resetStartPoint() {
		int point = this.items.length / 2;
		this.nextFirst = point;
		this.nextLast = point;
		this.start = point;
	}
	
	public T get(int index) {
		if (index < 0 || index > this.items.length - 1) {
			return null;
		}
		
		int array_index = this.start + index;
		if (array_index > this.items.length - 1) {
			array_index -= this.items.length;
		}
		
		T value = this.items[array_index];
		return value;
	}
	
	public Iterator<T> iterator() {
		return new ArrayIterator(this.items);
	}
	
	private class ArrayIterator implements Iterator<T> {
		private int index;
		private T[] array;
		public ArrayIterator(T[] items) {
			this.index = 0;
			this.array = items;
		}
		
		@Override
		public boolean hasNext() {
			return this.array[this.index+1] != null;
		}
		
		@Override
		public T next() {
			this.index++;
			if (this.index > this.array.length - 1) {
				this.index = 0;
			}
			return this.array[this.index];
		}
	}
	
	public boolean equals(Object o) {
		return true;
	}
}
