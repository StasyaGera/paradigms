import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Penguinni on 08.03.2016.
 * Penguinni does not promise that this code's going to work.
 */
public abstract class AbstractQueue implements Queue {
    protected abstract void doEnqueue(Object element);
    protected abstract Object doElement();
    protected abstract Object doDequeue();
    protected abstract AbstractQueue doCreate();

    public AbstractQueue filter(Predicate p) {
        assert p != null;
        AbstractQueue result = doCreate();

        int size = size();
        Object e;

        while (size > 0) {
            e = dequeue();
            if (p.test(e)) {
                result.enqueue(e);
            }
            enqueue(e);
            size--;
        }

        return result;
    }

    public AbstractQueue map(Function f) {
        assert f != null;
        AbstractQueue result = doCreate();

        int size = size();
        Object e;

        while (size > 0) {
            e = dequeue();
            result.enqueue(f.apply(e));
            enqueue(e);
            size--;
        }

        return result;
    }

    public void enqueue(Object element) {
        assert element != null;

        doEnqueue(element);
    }

    public Object element() {
        assert size() > 0;

        return doElement();
    }

    public Object dequeue() {
        assert size() > 0;

        return doDequeue();
    }

    public abstract int size();

    public boolean isEmpty() {
        return size() == 0;
    }

    public abstract void clear();
}
