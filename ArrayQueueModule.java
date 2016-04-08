/**
 * Created by Penguinni on 27.02.2016.
 * Penguinni does not promise that this code's going to work.
 */

//inv: if not mentioned, the elements' values stay unchanged
//     if there is at least one element, head != tail
//     elements[head] is the first element and elements[tail - 1] is the last
public class ArrayQueueModule {
    private static Object[] elements = new Object[5];
    private static int head = 0;
    private static int tail = head;

    //pre:
    //post: elements.length == 2 * elements'.length
    //      elements array contains all the same values as before in straight order
    //      head == 0
    //      tail == head + size, where size is the number of elements in the queue
    private static void ensureCapacity() {
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
    public static Object[] toArray() {
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
    public static void push(Object element) {
        assert element != null;

        ensureCapacity();
        if (head == 0) {
            head = elements.length;
        }
        elements[--head] = element;
    }

    //pre: queue is not empty
    //post: result == elements[tail - 1] or result == elements[elements.length - 1]
    public static Object peek() {
        assert tail != head;

        if (tail > 0) {
            return elements[tail - 1];
        } else {
            return elements[elements.length - 1];
        }
    }

    //pre: queue is not empty
    //post: result == elements[tail' - 1] or result == elements[elements.length - 1]
    public static Object remove() {
        assert tail != head;

        tail = (tail > 0) ? (tail - 1) : elements.length - 1;
        return elements[tail];
    }

    //pre: element != null
    //post: tail == tail' + 1 or tail == 0
    //      elements[tail - 1] == element or elements[elements.length - 1] == element
    //      tail != head
    public static void enqueue(Object element) {
        assert element != null;

        ensureCapacity();
        elements[tail] = element;
        tail = (tail < elements.length - 1 ? tail + 1 : 0);
    }

    //pre: queue is not empty
    //post: result == elements[head]
    public static Object element() {
        assert head != tail;

        return elements[head];
    }

    //pre: queue is not empty
    //post: head == head' + 1 or head == 0
    //      result == elements[head']
    public static Object dequeue() {
        assert head != tail;

        if (head < elements.length - 1) {
            return elements[head++];
        } else {
            head = 0;
            return elements[elements.length - 1];
        }
    }

    //pre:
    //post: result == tail - head or result == elements.length - (head - tail)
    public static int size() {
        return ((tail < head) ? (elements.length - (head - tail)) : (tail - head));
    }

    //pre:
    //post: result == (head == tail)
    public static boolean isEmpty() {
        return head == tail;
    }

    //pre:
    //post: head == tail == 0
    public static void clear() {
        head = tail = 0;
    }
}
