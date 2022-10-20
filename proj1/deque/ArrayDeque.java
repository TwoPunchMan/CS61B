package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
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

    @Override
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

    @Override
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
        T[] resizedArray = (T[]) new Object[capacity];
        int newStartPt = resizedArray.length / 2;
        for (int i = 0; i < this.size(); i++) {
            resizedArray[newStartPt + i] = this.get(i);
        }

        this.start = newStartPt;
        this.nextFirst = newStartPt - 1;
        this.nextLast = this.start + this.size();
        if (this.nextLast >= resizedArray.length) {
            this.nextLast = 0;
        }
        this.items = resizedArray;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
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

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }

        int removeIndex = this.start;
        if (removeIndex >= this.items.length) {
            removeIndex = 0;
            this.start = 0;
        }

        T value = this.items[removeIndex];
        this.items[removeIndex] = null;
        this.size--;
        this.start++;
        this.nextFirst = this.start - 1;
        if (this.isEmpty()) {
            this.resetStartPoint();
        }

        double factor = (double) this.size() / this.items.length;
        if (factor < 0.25 && this.items.length >= 16) {
            this.resize(this.items.length / 2);
        }

        return value;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }

        this.nextLast--;
        int removeIndex = this.nextLast;
        if (removeIndex < 0) {
            removeIndex = this.items.length - 1;
            this.nextLast = removeIndex;
        }

        T value = this.items[removeIndex];
        this.items[removeIndex] = null;
        this.size--;
        if (this.isEmpty()) {
            this.resetStartPoint();
        }

        double factor = (double) this.size() / this.items.length;
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

    @Override
    public T get(int index) {
        if (index < 0 || index > this.items.length - 1) {
            return null;
        }

        int arrayIndex = this.start + index;
        if (arrayIndex > this.items.length - 1) {
            arrayIndex -= this.items.length;
        }

        T value = this.items[arrayIndex];
        return value;
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int index;

        private ArrayIterator() {
            this.index = start;
        }

        @Override
        public boolean hasNext() {
            return items[this.index] != null;
        }

        @Override
        public T next() {
            T value = items[this.index];
            this.index++;
            if (this.index > items.length - 1) {
                this.index = 0;
            }
            return value;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ArrayDeque)) {
            return false;
        }

        Deque<T> A = (Deque<T>) o;

        if (A.size() != this.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i++) {
            if (A.get(i) != this.get(i)) {
                return false;
            }
        }

        return true;
    }
}
