package queue;

public abstract class AbstractQueue implements Queue {
    protected int size = 0;


    public Object element() {
        assert size() > 0;
        return elementImp();
    }

    protected abstract Object elementImp();


    public void enqueue(Object element) {
        // :NOTE: почему return?
        if (element == null) return;
        enqueueImp(element);
    }

    protected abstract void enqueueImp(Object element);

    public Object dequeue() {
        assert size() > 0;
        return dequeueImp();
    }

    protected abstract Object dequeueImp();

    public boolean isEmpty() {
        return size() == 0;
    }


    public int size() {
        return size;
    }


    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }


    public void dedup() {
        // :NOTE: если вы начинаете ифать крайние случаи, то вы что-то делаете не так
        if (size() < 2) return;
        // :NOTE: dedup все еще можно реализовать в абстрактном классе
        dedupImp();
    }

    protected abstract void dedupImp();
}

