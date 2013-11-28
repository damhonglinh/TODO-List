package datastructure;

import java.util.Iterator;

/**
 *
 * @author denis
 */
public interface Queue<T> {
    public void enqueue(T obj);
    public T dequeue();
    public Iterator getIterator();
}
