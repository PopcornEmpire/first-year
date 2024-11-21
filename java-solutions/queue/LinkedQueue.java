package queue;

public class LinkedQueue extends AbstractQueue {

    private Node head = null;
    private Node tail = null;
    private static class Node {
        Object value;
        Node next;

        Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public void enqueueImp(Object element) {
        Node newNode = new Node( element, null);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public Object dequeueImp() {
        Object value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    public Object elementImp() {
        return head.value;
    }

    public void dedupImp() {
        Node current = head;
        while (current.next != null) {
            if (current.value.equals(current.next.value)) {
                current.next = current.next.next;
                if (current.next == null) {
                    tail = current;
                }
                size--;
            } else {
                current = current.next;
            }
        }
    }
}

