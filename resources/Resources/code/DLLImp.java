public class DLLImp<T> implements DLL<T> {
    protected Node<T> head;
    protected Node<T> tail;
    protected Node<T> current;
    private int size;

    static class Node<T> {
        T data;
        Node<T> next, prev;

        Node(T data) {
            this.data = data;
        }
    }

    public DLLImp() {
        head = tail = current = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    @Override
    public boolean last() {
        return current == tail;
    }

    @Override
    public boolean first() {
        return current == head;
    }

    @Override
    public void findFirst() {
        current = head;
    }

    @Override
    public void findNext() {
        if (current != null) current = current.next;
    }

    @Override
    public void findPrevious() {
        if (current != null) current = current.prev;
    }

    @Override
    public T retrieve() {
        return current != null ? current.data : null;
    }

    @Override
    public void update(T val) {
        if (current != null) current.data = val;
    }

    @Override
    public void insert(T val) {
        Node<T> newNode = new Node<>(val);
        if (empty()) {
            head = tail = current = newNode;
        } else {
            newNode.next = current.next;
            newNode.prev = current;
            if (current.next != null) {
                current.next.prev = newNode;
            } else {
                tail = newNode;
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void remove() {
        if (current == null) return;

        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }

        current = current.next;
        size--;
    }
    
}
