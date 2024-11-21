package queue;

import java.util.Objects;
import java.util.function.Predicate;

// Model: a[1]..a[n] , n = size
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public class ArrayQueue extends AbstractQueue {
    protected Object[] elements;
    protected int head;
    protected int tail;

    // Pre: true
    // Post: size = 0
    public ArrayQueue() {
        this.elements = new Object[5];
        this.head = 0;
        this.tail = 0;
    }


    // :NOTE: это некорректный контракт
    // Pre: true
    // Post: immutable(size)

    public void enqueueImp(Object element) {
        Objects.requireNonNull(element);
        if ( size() + 2 > this.elements.length )this.ensureCapacity(size() + 2);
        this.elements[tail] = element;
        tail = (tail + 1) % this.elements.length;
    }


    // Pre: size > 0
    // Post: size' = size - 1 && immutable(size')
    public Object dequeueImp() {
        Object value = this.elements[head];
        this.elements[head] = null;
        head = (head + 1) % this.elements.length;
        return value;
    }

    // Pre: capacity > elements.length
    // Post: immutable(size)
    private void ensureCapacity(int capacity) {
            int newSize = this.elements.length * 2;
            Object[] newElements = new Object[newSize];
            if (head > tail) {
                System.arraycopy(this.elements, head, newElements, 0, this.elements.length - head);
                System.arraycopy(this.elements, 0, newElements, this.elements.length - head, tail);
            } else {
                System.arraycopy(this.elements, head, newElements, 0, size());
            }
            tail = size();
            head = 0;
            this.elements = newElements;
    }

    // Pre: true
    // Post: size' = size && immutable(size)
    public int size() {
        return (tail - head + this.elements.length) % this.elements.length;
    }

    // Pre: true
    // Post: size' = 0
    /*public void clear() {
        elements = new Object[5];
        head = 0;
        tail = 0;
    }*/

    // Pre: true
    // Post: R && size' = size && immutable(size)
    /*public boolean isEmpty() {
        return size() == 0;
    }*/

    // Pre: true
    // Post: size' = 0
    /*public void clear() {
        elements = new Object[5];
        head = 0;
        tail = 0;
    }*/

    // Pre: true
    // Post: R && size' = size && immutable(size)
    /*public boolean isEmpty() {
        return size() == 0;
    }*/


    // Pre: size > 0
    // Post: R && immutable(size) && size' = size
    public Object elementImp() {
        return elements[head];
    }

    // Pre: predicate != null
    // Post: R
    public int countIf(Predicate<Object> predicate) {
        assert predicate != null;
        int count = 0;
        for (int i = 0; i < size(); i++) {
            int index = (head + i) % this.elements.length;
            if (predicate.test(this.elements[index])) {
                count++;
            }
        }
        return count;
    }

    public void dedupImp() {
        int currentSize = size();
        int index = head;
        int nextIndex;
        for (int i = 1; i < currentSize; i++) {
            nextIndex = (head + i) % elements.length;
            if (!elements[index].equals(elements[nextIndex])) {
                index = (index + 1) % elements.length;
                elements[index] = elements[nextIndex];
            } else {
                tail = (tail - 1 + elements.length) % elements.length;
            }
        }
    }
}
