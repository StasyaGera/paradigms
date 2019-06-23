public class ArrayQueueTest {
    public static void fillCycled(ArrayQueue queue) {
        for (int i = 0; i < 1; i++) {
            queue.enqueue(i);
            queue.dequeue();
        }
        fill(queue);
    }

    public static void fill(ArrayQueue queue) {
        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.size() + " " +
                            queue.element() + " " +
                            queue.dequeue()
            );
        }
    }

    public static void main(String args[]) {
        ArrayQueue queue = new ArrayQueue();
//        fillCycled(queue);
//        System.out.println("Cycled: ");
//        dump(queue);
        fill(queue);
        System.out.println("Straight: ");
        dump(queue);
        System.out.println("call");
        queue.toArray();
        fill(queue);
        System.out.println("call");
        queue.toArray();
        queue.clear();
        System.out.println("call");
        queue.toArray();
        System.out.println(queue.isEmpty());
        fill(queue);
        System.out.println("call");
        queue.toArray();
        System.out.println(queue.isEmpty());
        dump(queue);
        System.out.println("call");
        queue.toArray();
    }
}
