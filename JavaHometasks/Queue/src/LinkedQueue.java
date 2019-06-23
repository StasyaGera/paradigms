public class LinkedQueue extends AbstractQueue {
    private class Node {
        final Object value;
        Node next;

        public Node(Object value, Node next) {
            assert value != null;

            this.value = value;
            this.next = next;
        }
    }

    private int size = 0;
    private Node head = null;
    private Node tail = null;

    protected LinkedQueue doCreate() {
        return new LinkedQueue();
    }

    protected void doEnqueue(Object element) {
        if (size != 0) {
            tail = tail.next = new Node(element, null);
        } else {
            head = tail = new Node(element, null);
        }
        size++;
    }

    protected Object doElement() {
        return head.value;
    }

    protected Object doDequeue() {
        Object ans = head.value;
        head = head.next;
        size--;
        return ans;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
        head = tail = null;
    }
}
