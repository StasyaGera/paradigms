public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[5];
    private int head = 0;
    private int tail = head;

    protected ArrayQueue doCreate() {
        return new ArrayQueue();
    }

    public void doEnqueue(Object element) {
        ensureCapacity();
        elements[tail] = element;
        tail = (tail < elements.length - 1 ? tail + 1 : 0);
    }

    public Object doElement() {
        return elements[head];
    }

    public Object doDequeue() {
        if (head < elements.length - 1) {
            return elements[head++];
        } else {
            head = 0;
            return elements[elements.length - 1];
        }
    }

    public int size() {
        return ((tail < head) ? (elements.length - (head - tail)) : (tail - head));
    }

    public void clear() {
        head = tail = 0;
    }

    //pre:
    //post: elements.length == 2 * elements'.length
    //      elements array contains all the same values as before in straight order
    //      head == 0
    //      tail == head + size, where size is the number of elements in the queue
    private void ensureCapacity() {
        int size = size();
        if (size < elements.length - 1) {
            return;
        }

        Object[] newElements = new Object[2 * elements.length];
        System.arraycopy(toArray(), 0, newElements, 0, size);
        elements = newElements;
        head = 0;
        tail = head + size;
    }

    //pre:
    //post: result contains all the elements in straight order
    public Object[] toArray() {
        Object[] result = new Object[size()];

        if (tail < head) {
            System.arraycopy(elements, head, result, 0, elements.length - head);
            System.arraycopy(elements, 0, result, elements.length - head, tail);
        }
        else {
            System.arraycopy(elements, head, result, 0, tail - head);
        }
        return result;
    }

    //pre: element != null
    //post: elements[head] == element
    //      head == head' - 1 or head == elements.length
    //      tail != head
    public void push(Object element) {
        assert element != null;

        ensureCapacity();
        if (head == 0) {
            head = elements.length;
        }
        elements[--head] = element;
    }

    //pre: queue is not empty
    //post: result == elements[tail - 1] or result == elements[elements.length - 1]
    public Object peek() {
        assert tail != head;

        if (tail > 0) {
            return elements[tail - 1];
        } else {
            return elements[elements.length - 1];
        }
    }

    //pre: queue is not empty
    //post: result == elements[tail' - 1] or result == elements[elements.length - 1]
    public Object remove() {
        assert tail != head;

        tail = (tail > 0) ? (tail - 1) : (elements.length - 1);
        return elements[tail];
    }
}
