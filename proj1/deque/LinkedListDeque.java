package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
	private class Node {
		public T value;
		public Node next;
		public Node prev;
		
		public Node(T value) {
			this.value = value;
			this.next = null;
			this.prev = null;
		}
	}
	private Node sentinel;
	private int size;
	
	public LinkedListDeque() {
		this.size = 0;
		this.sentinel = new Node(null);
	}
	
	@Override
	public void addFirst(T item) {
		Node new_head = new Node(item);
		if (this.size() == 0) {
			this.sentinel.prev = new_head;
			this.sentinel.next = new_head;
			new_head.next = this.sentinel;
			new_head.prev = this.sentinel;
			this.size++;
			return;
		}
		
		Node old_head = this.sentinel.next;
		
		new_head.next = old_head;
		new_head.prev = this.sentinel;
		
		this.sentinel.next = new_head;
		old_head.prev = new_head;
		this.size++;
	}
	
	@Override
	public void addLast(T item) {
		Node new_tail = new Node(item);
		if (this.size() == 0) {
			this.sentinel.prev = new_tail;
			this.sentinel.next = new_tail;
			new_tail.next = this.sentinel;
			new_tail.prev = this.sentinel;
			this.size++;
			return;
		}
		
		Node old_tail = this.sentinel.prev;
		
		new_tail.prev = old_tail;
		new_tail.next = this.sentinel;
		
		this.sentinel.prev = new_tail;
		old_tail.next = new_tail;
		this.size++;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public void printDeque() {
		Node p = this.sentinel;
		while (p.next != this.sentinel) {
			p = p.next;
			if (p.next == this.sentinel) {
				System.out.println(p.value);
				return;
			}
			System.out.print(p.value + " ");
		}
	}
	
	@Override
	public T removeFirst() {
		if (this.isEmpty()) {
			return null;
		}
		
		Node p = this.sentinel;
		p = p.next;
		T node_rm_value = p.value;
		this.sentinel.next = p.next;
		p.next.prev = this.sentinel;
		p.next = null;
		p.prev = null;
		this.size--;
		return node_rm_value;
	}
	
	@Override
	public T removeLast() {
		if (this.isEmpty()) {
			return null;
		}
		
		Node p = this.sentinel;
		p = p.prev;
		T node_rm_value = p.value;
		this.sentinel.prev = p.prev;
		p.prev.next = this.sentinel;
		p.next = null;
		p.prev = null;
		this.size--;
		return node_rm_value;
	}
	
	@Override
	public T get(int index) {
		if (index < 0 || index > this.size-1) {
			return null;
		}

		Node p = this.sentinel;
		int i = 0;
		
		while (i <= index) {
			p = p.next;
			i++;
		}
		
		return p.value;
	}
	
	public T getRecursive(int index) {
		if (index < 0 || index > this.size-1) {
			return null;
		}
		
		Node p = this.sentinel;
		return this.recursiveHelper(p.next, index);
	}
	
	public T recursiveHelper(Node n, int index) {
		if (index == 0) {
			return n.value;
		}
		
		return recursiveHelper(n.next, index-1);
	}
	
	public Iterator<T> iterator() {
		return new LLIterator();
	}
	
	private class LLIterator implements Iterator<T> {
		private Node pointer;
		
		public LLIterator() {
			this.pointer = sentinel.next;
		}
		
		@Override
		public boolean hasNext() {
			if (this.pointer != sentinel) {
				return true;
			}
			return false;
		}
		
		@Override
		public T next() {
			T value = this.pointer.value;
			this.pointer = this.pointer.next;
			return value;
		}
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (!(o instanceof LinkedListDeque)) {
			return false;
		}
		
		LinkedListDeque<T> L = (LinkedListDeque<T>) o;
		
		if (L.size() != this.size()) {
			return false;
		}
		
		for (int i = 0; i < this.size(); i++) {
			if (L.get(i) != this.get(i)) {
				return false;
			}
		}
		
		return true;
	}
}