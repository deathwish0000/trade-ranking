import java.util.Comparator;

public class DLLCompImp<T extends Comparable<T>> extends DLLImp<T> implements DLLComp<T> {

    @Override
    public void sort(boolean increasing) {
        if (size() > 1) {
            boolean wasChanged;
            do {
                current = head;
                wasChanged = false;
                while (current != null && current.next != null) {
                    if ((increasing && current.data.compareTo(current.next.data) > 0) ||
                        (!increasing && current.data.compareTo(current.next.data) < 0)) {
                        T temp = current.data;
                        current.data = current.next.data;
                        current.next.data = temp;
                        wasChanged = true;
                    }
                    current = current.next;
                }
            } while (wasChanged);
        }
    }

    @Override
    public T getMax() {
        if (empty()) throw new IllegalStateException("List is empty");
        return getMaxMinHelper(Comparator.naturalOrder());
    }

    @Override
    public T getMin() {
        if (empty()) throw new IllegalStateException("List is empty");
        return getMaxMinHelper(Comparator.reverseOrder());
    }

    private T getMaxMinHelper(Comparator<T> comparator) {
        T result = head.data;  // Start with the head of the list.
        Node<T> temp = head.next;
        while (temp != null) {
            if (comparator.compare(result, temp.data) < 0) {
                result = temp.data;
            }
            temp = temp.next;
        }
        return result;
    }
}
