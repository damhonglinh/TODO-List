package datastructure;

import java.util.Iterator;

/**
 *
 * @author Denis Rinfret
 */
public interface List<T> {

    public void addToHead(T obj);

    public void addToTail(T obj);

    public T getFromHead();

    public T getFromTail();

    public Iterator getIterator();

    public T removeFromHead();

    public T removeFromTail();
}
