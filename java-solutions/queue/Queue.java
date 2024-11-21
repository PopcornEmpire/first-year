package queue;

// Model: a[1]..a[n] , n = size
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
// :NOTE: не пишите в контрактах блоки кода
// Let: fix(): forall i=1..n: a'[i] == null || a[i] , count = 1 , forall i=0..n: a'[i] != null => a'[++count'] = a[i] , n' = count' , size' < size
//
public interface Queue {

     // Pre: true
     // Post: immutable(size)
    // :NOTE: queue = [1,2,3]; enqueue(13); queue = [1,2,3]?

    void enqueue(Object element);



    // Pre: size > 0
    // Post: size' = size - 1 && immutable(size - 1)
    // :NOTE: queue = [1,2,3]; dequeue(); queue = [1,2,3]?

    Object dequeue();



    // Pre: size > 0
    // Post: immutable(size) && size' = size

    Object element();

    // Pre: true
    // Post: size' = size && immutable(size)
    // :NOTE: queue = [1,2,3]; int s = queue.size(); s == -1039?

    int size();



    // Pre: true
    // Post: size' = size && immutable(size)
    // :NOTE: queue = [1,2,3]; int s = queue.isEmpty(); s == true?

    boolean isEmpty();



    // Pre: true
    // Post: size' = 0
    void clear();

    // Pre: true
    // Post: fix()
    void dedup();
}
