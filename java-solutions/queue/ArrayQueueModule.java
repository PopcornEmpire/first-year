package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

// Model: a[1]..a[n], n = size(a)
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public class ArrayQueueModule {
    private static Object[] elements = new Object[5];
    private static int head = 0;
    private static int tail = 0;

    // Pre: element != null
    // Post: size' = size + 1 && immutable(size)
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size() + 2);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }

    // Pre: size > 0
    // Post: R && size' = size - 1 && immutable(size')
    public static Object dequeue() {
        assert size() > 0;
        Object result = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return result;
    }

    // Pre: true
    // Post: immutable(size)
    private static void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            int newSize = elements.length * 2;
            Object[] newElements = new Object[newSize];
            if (head > tail) {
                System.arraycopy(elements, head, newElements, 0, elements.length - head);
                System.arraycopy(elements, 0, newElements, elements.length - head, tail);
            } else {
                System.arraycopy(elements, head, newElements, 0, size());
            }
            tail = size();
            head = 0;
            elements = newElements;
        }
    }


    // Pre: size > 0
    // Post: R && immutable(size)
    public static Object element() {
        assert size() > 0;
        return elements[head];
    }

    // Pre: true
    // Post: R = size && immutable(size)
    public static int size() {
        return (tail - head + elements.length)% elements.length;
    }

    // Pre: true
    // Post: R && immutable(size)
    public static boolean isEmpty() {
        return size() == 0;
    }

    // Pre: true
    // Post: size' = 0
    public static void clear() {
        // :NOTE: пересоздание нового объекта на каждый вызов clear
        elements = new Object[5];
        head = 0;
        tail = 0;
    }

    // Pre: predicate != null
    // Post: R
    public static int countIf(Predicate<Object> predicate) {
        assert predicate != null;
        int count = 0;
        for (int i = 0; i < size(); i++) {
            int index = (head + i) % elements.length;
            if (predicate.test(elements[index])) {
                count++;
            }
        }
        return count;
    }
}
