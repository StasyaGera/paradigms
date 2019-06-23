import java.util.function.Function;
import java.util.function.Predicate;

//inv: if not mentioned, the elements' values stay unchanged
//     size >= 0
//     if there is at least one element, head != tail
//     head element is the first element and tail element is the last
public interface Queue {

    //pre: predicate is not null
    //post: result queue consists of all those main queue elements for which the predicate is true
    //      result queue elements are in the same order as in main queue
    AbstractQueue filter(Predicate p);

    //pre: function is not null
    //post: result queue consists of the result of application function to the main queue elements
    //      result queue elements are in the same order as in main queue
    AbstractQueue map(Function f);

    //pre: element != null
    //post: element is appended to the end of the queue
    //      new element is the tail element
    void enqueue(Object element);

    //pre: queue is not empty
    //post: result is the first element of the queue
    Object element();

    //pre: queue is not empty
    //post: the second element in the queue is now the head element or the queue is empty
    Object dequeue();

    //pre:
    //post: result is the number of elements in the queue
    int size();

    //pre:
    //post: result == true if there are one or more elements, false otherwise
    boolean isEmpty();

    //pre:
    //post: there are no elements in the queue
    void clear();
}