//inv: if not mentioned, the elements' values stay unchanged
//     if there is at least one element, head != tail
//     elements[head] is the first element and elements[tail - 1] is the last
public class ArrayQueueADT {
    private Object[] elements = new Object[5];
    private int head = 0;
    private int tail = head;

    //pre: queue is not null
    //post: elements.length == 2 * elements'.length
    //      elements array contains all the same values as before in straight order
    //      head == 0
    //      tail == head + size, where size is the number of elements in the queue
    private static void ensureCapacity(ArrayQueueADT queue) {
        int size = size(queue);
        if (size < queue.elements.length - 1) {
            return;
        }

        Object[] newElements = new Object[2 * queue.elements.length];

        System.arraycopy(toArray(queue), 0, newElements, 0, size);
        queue.elements = newElements;
        queue.head = 0;
        queue.tail = queue.head + size;
    }

    //pre: queue is not null
    //post: result contains all the elements in straight order
    public static Object[] toArray(ArrayQueueADT queue) {
        Object[] result = new Object[size(queue)];

        if (queue.tail < queue.head) {
            System.arraycopy(queue.elements, queue.head, result, 0, queue.elements.length - queue.head);
            System.arraycopy(queue.elements, 0, result, queue.elements.length - queue.head, queue.tail);
        }
        else {
            System.arraycopy(queue.elements, queue.head, result, 0, queue.tail - queue.head);
        }
        return result;
    }

    //pre: element != null
    //     queue is not null
    //post: elements[head] == element
    //      head == head' - 1 or head == elements.length
    //      tail != head
    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null;

        ensureCapacity(queue);
        if (queue.head == 0) {
            queue.head = queue.elements.length;
        }
        queue.elements[--queue.head] = element;
    }

    //pre: queue is not empty
    //     queue is not null
    //post: result == elements[tail - 1] or result == elements[elements.length - 1]
    public static Object peek(ArrayQueueADT queue) {
        assert queue.tail != queue.head;

        if (queue.tail > 0) {
            return queue.elements[queue.tail - 1];
        } else {
            return queue.elements[queue.elements.length - 1];
        }
    }

    //pre: queue is not empty
    //     queue is not null
    //post: result == elements[tail' - 1] or result == elements[elements.length - 1]
    public static Object remove(ArrayQueueADT queue) {
        assert queue.tail != queue.head;

        queue.tail = (queue.tail > 0) ? (queue.tail - 1) : queue.elements.length - 1;
        return queue.elements[queue.tail];
    }

    //pre: element != null
    //     queue is not null
    //post: tail == tail' + 1 or tail == 0
    //      elements[tail - 1] == element or elements[elements.length - 1] == element
    //      tail != head
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null;

        ensureCapacity(queue);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail < queue.elements.length - 1 ? queue.tail + 1 : 0);
    }

    //pre: queue is not empty
    //     queue is not null
    //post: result == elements[head]
    public static Object element(ArrayQueueADT queue) {
        assert queue.head != queue.tail;

        return queue.elements[queue.head];
    }

    //pre: queue is not empty
    //     queue is not null
    //post: head == head' + 1 or head == 0
    //      result == elements[head']
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.head != queue.tail;

        if (queue.head < queue.elements.length - 1) {
            return queue.elements[queue.head++];
        } else {
            queue.head = 0;
            return queue.elements[queue.elements.length - 1];
        }
    }

    //pre: queue is not null
    //post: result == tail - head or result == elements.length - (head - tail)
    public static int size(ArrayQueueADT queue) {
        return ((queue.tail < queue.head) ? (queue.elements.length - (queue.head - queue.tail)) : (queue.tail - queue.head));
    }

    //pre: queue is not null
    //post: result == (head == tail)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.head == queue.tail;
    }

    //pre: queue is not null
    //post: head == tail == 0
    public static void clear(ArrayQueueADT queue) {
        queue.head = queue.tail = 0;
    }
}
