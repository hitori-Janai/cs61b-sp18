public class ArrayDeque<T> {
    private int size;
    private int nextFirst;
    private Object[] items;
    private int nextLast;
    static final double USAGE_FACTOR = 0.25;

    public ArrayDeque() {
        items = new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst + items.length - 1) % items.length;
        size++;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size++;

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        int i = (nextFirst + 1) % items.length; // start
        int end = (nextLast + items.length - 1) % items.length;
        for (; i != end; i = (i + 1) % items.length) {
            System.out.print(items[i] + " ");
        }
        System.out.print(items[end]);
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        if (!usageCheck()) {
            resize(items.length / 2);
        }

        T rs = get(0);
        nextFirst = (nextFirst + 1) % items.length;
        size--;
        return rs;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (!usageCheck()) {
            resize(items.length / 2);
        }
        T rs = get(size - 1);
        nextLast = (nextLast + items.length - 1) % items.length;
        size--;
        return rs;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (!isElementIndex(index) || isEmpty()) {
            return null;
        }
        index = (nextFirst + index + 1) % items.length;
        return (T) items[index];
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void resize(int newSize) {
        Object[] newItems = new Object[newSize];
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        nextFirst = newSize - 1;
        nextLast = size;
        items = newItems;
    }

    // 利用率检查是否大于0.25;
    private boolean usageCheck() {
        return items.length <= 16 || size > items.length * USAGE_FACTOR;
    }
}
