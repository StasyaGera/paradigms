/**
 * Created by Penguinni on 27.02.2016.
 * Penguinni does not promise that this code's going to work.
 */
public class ArrayQueueModuleTest {
    public static void fillCycled() {
        for (int i = 0; i < 8; i++) {
            ArrayQueueModule.enqueue(i);
            ArrayQueueModule.dequeue();
        }
        fill();
    }

    public static void fill() {
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.size() + " " +
                    ArrayQueueModule.element() + " " +
                    ArrayQueueModule.dequeue()
            );
        }
    }

    public static void main(String args[]) {
        fillCycled();
        System.out.println("Cycled: ");
        dump();
        fill();
        System.out.println("Straight: ");
        dump();
        fill();
        ArrayQueueModule.clear();
        System.out.println(ArrayQueueModule.isEmpty());
        fill();
        System.out.println("Straight: ");
        dump();

    }
}
