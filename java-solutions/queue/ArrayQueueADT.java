package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

// Model: a[1]..a[n] , n = size
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public class ArrayQueueADT {
    private int head = 0;
    private int tail = 0;
    private Object[] elements;

    // Pre: true
    // Post:  size = 0
    public ArrayQueueADT() {
        elements = new Object[5];
    }

    // Pre: queue != null && element != null
    // Post: size' = size + 1 && immutable(size) && elements.length <= elements'.length
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, size(queue) + 2);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
    }

    // Pre: queue != null && size > 0
    // Post: R && size' = size - 1 && immutable(size - 1)
    public static Object dequeue(ArrayQueueADT queue) {
        assert size(queue) > 0;
        Object value = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        return value;
    }

    // Pre: queue != null && capacity > elements.length
    // Post: immutable(size)
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity > queue.elements.length) {
            int newSize = queue.elements.length * 2;
            Object[] newElements = new Object[newSize];
            if (queue.head > queue.tail) {
                System.arraycopy(queue.elements, queue.head, newElements, 0, queue.elements.length - queue.head);
                System.arraycopy(queue.elements, 0, newElements, queue.elements.length - queue.head, queue.tail);
            } else {
                System.arraycopy(queue.elements, queue.head, newElements, 0, size(queue));
            }
            queue.tail = size(queue);
            queue.head = 0;
            queue.elements = newElements;
        }
    }


    // Pre: queue != null
    // Post: R && size' = size && immutable(size)
    public static int size(ArrayQueueADT queue) {
        return (queue.tail - queue.head + queue.elements.length)% queue.elements.length;
    }

    // Pre: queue != null
    // Post: R && size' = size && immutable(size)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return size(queue) == 0;
    }

    // Pre: queue != null
    // Post: size' = 0
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[5];
        queue.head = 0;
        queue.tail = 0;
    }

    // Pre: queue != null && size > 0
    // Post: R && size' = size && immutable(size)
    public static Object element(ArrayQueueADT queue) {
        assert size(queue) > 0;
        return queue.elements[queue.head];
    }

    // Pre: queue != null && predicate != null
    // Post: R
    public static int countIf(ArrayQueueADT queue, Predicate<Object> predicate) {
        assert predicate != null;
        int count = 0;
        for (int i = 0; i < size(queue); i++) {
            int index = (queue.head + i) % queue.elements.length;
            if (predicate.test(queue.elements[index])) {
                count++;
            }
        }
        return count;
    }
}
