/*package queue;

public class ArrayStackTest {
    public static void fill(ArrayQueue stack, String prefix) {
        for (int i = 0; i < 10; i++) {
            stack.push(prefix + i);
        }
    }

    public static void dump(ArrayQueue stack) {
        while (!stack.isEmpty()) {
            System.out.println(stack.size() + " " + stack.pop());
        }
    }

    public static void main(String[] args) {
        ArrayQueue stack1 = new ArrayQueue();
        ArrayQueue stack2 = new ArrayQueue();
        fill(stack1, "s1_");
        fill(stack2, "s2_");
        dump(stack1);
        dump(stack2);
    }
}
/*/