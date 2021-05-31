public class LinkedListDeque<T> implements Deque<T> {
    class Node {
        Node prev = null;
        Node next = null;
        T item = null;

        Node(LinkedListDeque<T>.Node prev, LinkedListDeque<T>.Node next, T item) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }

    private int size = 0;
    private Node sentinel = null;

    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    /**
     * must not involve any looping or recursion. take “constant time”
     *
     * @param item
     */
    @Override
    public void addFirst(T item) {
        Node n = new Node(sentinel.prev, sentinel.next, item);
        n.next = sentinel.next;
        n.prev = sentinel;
        sentinel.next.prev = n;
        sentinel.next = n;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node n = new Node(sentinel.prev, sentinel.next, item);
        n.next = sentinel;
        n.prev = sentinel.prev;
        sentinel.prev.next = n;
        sentinel.prev = n;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * constant time
     *
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node t = sentinel.next;
        for (int i = 0; i < size - 1; i++) {
            System.out.print(t.item + " ");
            t = t.next;
        }
        System.out.print(t.item);
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node r = sentinel.next;
        r.next.prev = sentinel;
        sentinel.next = r.next;
        size--;
        return r.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node r = sentinel.prev;
        r.prev.next = sentinel;
        sentinel.prev = r.prev;
        size--;
        return r.item;
    }

    /**
     * iteration
     *
     * @param index [0,size)
     * @return
     */
    @Override
    public T get(int index) {
        if (!isElementIndex(index)) {
            return null;
        }
        Node n = sentinel;
        for (int i = 0; i < index + 1; i++) {
            n = n.next;
        }
        return n.item;
    }

    /**
     * Same as get, but uses recursion.
     *
     * @param index
     * @return
     */
    public T getRecursive(int index) {
        if (!isElementIndex(index)) {
            return null;
        }
        return getRecursive(sentinel.next, index);
    }

    private T getRecursive(Node n, int index) {
        if (index == 0) {
            return n.item;
        }
        return getRecursive(n.next, --index);
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
}
