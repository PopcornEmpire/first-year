/*package queue;

public class ArrayStackModuleTest {
    public static void fill() {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.push(i);
        }
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                ArrayQueueModule.size() + " " +
                ArrayQueueModule.peek() + " " +
                ArrayQueueModule.pop()
            );
        }
    }

    public static void main(String[] args) {
        fill();
        dump();
    }
}
*/
package queue;

public class ArrayStackModuleTest {
    public static void fill() {
        ArrayQueueModule.enqueue(5);
        ArrayQueueModule.enqueue(20);
        ArrayQueueModule.enqueue(7);
        ArrayQueueModule.enqueue(10);
        ArrayQueueModule.element();
    }

    public static void main(String[] args) {
        // :NOTE: assert
        fill();
        System.out.println(ArrayQueueModule.countIf(x -> (int) x >= 10));

    }
}
