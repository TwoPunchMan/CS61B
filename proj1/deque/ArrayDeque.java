package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {
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

    public void resize(int capacity) {
        T[] resizedArray = (T[]) new Object[capacity];
        int newStartPt = resizedArray.length / 2;
        int oldStartPt = this.start;
        for (int i = 0; i < this.size(); i++) {
            if (oldStartPt >= this.size()) {
                oldStartPt = 0;
            }
            resizedArray[newStartPt+i] = this.items[oldStartPt];
            oldStartPt += 1;
        }

        this.start = newStartPt;
        this.nextFirst = newStartPt - 1;
        this.nextLast = 0;
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
        if (this.isEmpty()) { return null; }

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
        if (this.isEmpty()) { this.resetStartPoint(); }

        double factor = this.size() / this.items.length;
        if (factor < 0.25 && this.items.length >= 16) {
            this.resize(this.items.length / 2);
        }

        return value;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) { return null; }

        this.nextLast--;
        int removeIndex = this.nextLast;
        if (removeIndex < 0) {
            removeIndex = this.items.length - 1;
            this.nextLast = removeIndex;
        }

        T value = this.items[removeIndex];
        this.items[removeIndex] = null;
        this.size--;
        if (this.isEmpty()) { this.resetStartPoint(); }

        double factor = this.size() / this.items.length;
        if (factor < 0.25 && this.items.length >= 16) {
            this.resize(this.items.length / 2);
        }

        return value;
    }

    protected void resetStartPoint() {
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

    protected class ArrayIterator implements Iterator<T> {
        private int index;

        public ArrayIterator() {
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

        ArrayDeque<T> A = (ArrayDeque<T>) o;

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
