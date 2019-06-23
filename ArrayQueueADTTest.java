/**
 * Created by Penguinni on 28.02.2016.
 * Penguinni does not promise that this code's going to work.
 */
public class ArrayQueueADTTest {
    public static void fillCycled(ArrayQueueADT queue) {
        for (int i = 0; i < 1; i++) {
            ArrayQueueADT.enqueue(queue, i);
            ArrayQueueADT.dequeue(queue);
        }
        fill(queue);
    }

    public static void fill(ArrayQueueADT queue) {
        for (int i = 0; i < 5; i++) {
            ArrayQueueADT.enqueue(queue, i);
        }
    }

    public static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " " +
                            ArrayQueueADT.element(queue) + " " +
                            ArrayQueueADT.dequeue(queue)
            );
        }
    }

    public static void main(String args[]) {
        ArrayQueueADT queue = new ArrayQueueADT();
        fillCycled(queue);
        System.out.println("Cycled: ");
        dump(queue);
        fill(queue);
        System.out.println("Straight: ");
        dump(queue);
        fill(queue);
        ArrayQueueADT.clear(queue);
        System.out.println(ArrayQueueADT.isEmpty(queue));
    }
}
