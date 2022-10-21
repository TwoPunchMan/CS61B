package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class Node {
        private T value;
        private Node next;
        private Node prev;

        private Node(T value) {
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
        Node newHead = new Node(item);
        if (this.size() == 0) {
            this.sentinel.prev = newHead;
            this.sentinel.next = newHead;
            newHead.next = this.sentinel;
            newHead.prev = this.sentinel;
            this.size++;
            return;
        }

        Node oldHead = this.sentinel.next;

        newHead.next = oldHead;
        newHead.prev = this.sentinel;
        this.sentinel.next = newHead;
        oldHead.prev = newHead;
        this.size++;
    }

    @Override
    public void addLast(T item) {
        Node newTail = new Node(item);
        if (this.size() == 0) {
            this.sentinel.prev = newTail;
            this.sentinel.next = newTail;
            newTail.next = this.sentinel;
            newTail.prev = this.sentinel;
            this.size += 1;
            return;
        }

        Node oldTail = this.sentinel.prev;

        newTail.prev = oldTail;
        newTail.next = this.sentinel;

        this.sentinel.prev = newTail;
        oldTail.next = newTail;
        this.size += 1;
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
        T nodeRmValue = p.value;
        this.sentinel.next = p.next;
        p.next.prev = this.sentinel;
        p.next = null;
        p.prev = null;
        this.size--;
        return nodeRmValue;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }

        Node p = this.sentinel;
        p = p.prev;
        T nodeRmValue = p.value;
        this.sentinel.prev = p.prev;
        p.prev.next = this.sentinel;
        p.next = null;
        p.prev = null;
        this.size--;
        return nodeRmValue;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > this.size - 1) {
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
        if (index < 0 || index > this.size - 1) {
            return null;
        }

        Node p = this.sentinel;
        return this.recursiveHelper(p.next, index);
    }

    private T recursiveHelper(Node n, int index) {
        if (index == 0) {
            return n.value;
        }

        return recursiveHelper(n.next, index - 1);
    }

    public Iterator<T> iterator() {
        return new LLIterator();
    }

    private class LLIterator implements Iterator<T> {
        private Node pointer;

        private LLIterator() {
            this.pointer = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            if (this.pointer == sentinel || this.pointer == null) {
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            T value = this.pointer.value;
            this.pointer = this.pointer.next;
            return value;
        }
    }

    private boolean contains(T item) {
        for (int i = 0; i < this.size(); i++) {
            T linkedListItem = this.get(i);
            if (linkedListItem.equals(item)) {
                return true;
            }
        }

        return false;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<T> D = (Deque<T>) o;

        if (D.size() != this.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i++) {
            T item = D.get(i);
            if (!(this.contains(item))) {
                return false;
            }
        }

        return true;
    }
}
