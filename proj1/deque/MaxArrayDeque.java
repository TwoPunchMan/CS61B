package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

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
        if (this.isEmpty()) {
            return null;
        }

        T maxValue = this.arrayDeque.get(0);
        this.comparator = c;

        int i = 0;
        while (i < this.size()) {
            T item = this.arrayDeque.get(i);
            int comp = this.comparator.compare(item, maxValue);
            if (comp >= 0) {
                maxValue = item;
            }

            i += 1;
        }

        return maxValue;
    }
/*
    @Override
    public void addFirst(T item) {
        this.arrayDeque.addLast(item);
    }

    @Override
    public void addLast(T item) {
        this.arrayDeque.addLast(item);
    }

    @Override
    public void resize(int capacity) {
        this.arrayDeque.resize(capacity);
    }

    @Override
    public boolean isEmpty() {
        return this.arrayDeque.isEmpty();
    }

    @Override
    public int size() {
        return this.arrayDeque.size();
    }

    @Override
    public void printDeque() {
        this.arrayDeque.printDeque();
    }

    @Override
    public T removeFirst() {
        return this.arrayDeque.removeFirst();
    }

    @Override
    public T removeLast() {
        return this.arrayDeque.removeLast();
    }

    @Override
    public void resetStartPoint() {
        this.arrayDeque.resetStartPoint();
    }

    @Override
    public T get(int index) {
        return this.arrayDeque.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return this.arrayDeque.iterator();
    }
*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof MaxArrayDeque)) {
            return false;
        }

        MaxArrayDeque<T> M = (MaxArrayDeque<T>) o;

        if (M.size() != this.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i++) {
            if (M.get(i) != this.get(i)) {
                return false;
            }
        }

        return true;
    }
}
