package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    public T max() {
        return this.max(this.comparator);
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }

        T maxValue = this.get(0);
        this.comparator = c;

        int i = 0;
        while (i < this.size()) {
            T item = this.get(i);
            if (item == null) {
                return maxValue;
            }
            int comp = this.comparator.compare(item, maxValue);
            if (comp >= 0) {
                maxValue = item;
            }

            i++;
        }

        return maxValue;
    }
}
