/*package queue;

public class ArrayStackADTTest {
    public static void fill(ArrayQueueADT stack, String prefix) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(stack, prefix + i);
        }
    }

    public static void dump(ArrayQueueADT stack) {
        while (!ArrayQueueADT.isEmpty(stack)) {
            System.out.println(
                ArrayQueueADT.size(stack) + " " +
                ArrayQueueADT.peek(stack) + " " +
                ArrayQueueADT.pop(stack)
            );
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT stack1 = ArrayQueueADT.create();
        ArrayQueueADT stack2 = ArrayQueueADT.create();
        fill(stack1, "s1_");
        fill(stack2, "s2_");
        dump(stack1);
        dump(stack2);
    }
}
*/