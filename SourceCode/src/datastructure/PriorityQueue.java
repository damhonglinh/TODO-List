package datastructure;

/**
 *
 * @author denis
 */
public interface PriorityQueue<T> extends Queue<T> {
    public void enqueue(T obj, int priority);
}
