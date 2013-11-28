package datastructure;

import java.util.Iterator;

/**
 *
 * @author Dam Linh - s3372757
 */
public class CircularLinkedList<T> implements List<T> {

    //it is a circular so there should not be differences between a head and a tail
    //tail here is just a previous node of the head
    //therefore, tail.next is actually head
    private Node tail;

    @Override
    public void addToHead(T o) {
        if (tail == null) {
            tail = new Node(o, null);
            tail.next = tail;
        } else {
            tail.next = new Node(o, tail.next);
        }
    }

    //it is a circular so there should not be differences between a head and a tail
    @Override
    public void addToTail(T o) {
        addToHead(o);
    }

    @Override
    public T getFromHead() {
        if (tail == null) {
            return null;
        }
        return tail.next.obj;
    }

    //it is a circular so there should not be differences between a head and a tail
    @Override
    public T getFromTail() {
        return getFromHead();
    }

    @Override
    public T removeFromHead() {
        if (tail == null) {
            return null;
        }
        Node temp = tail.next;
        if (tail == tail.next) {// if the list has only 1 element
            tail = null;
        } else {
            tail.next = temp.next;
        }
        temp.next = null;

        return temp.obj;
    }

    //it is a circular so there should not be differences between a head and a tail
    @Override
    public T removeFromTail() {
        return removeFromHead();
    }

    @Override
    public Iterator getIterator() {
        return new CLLIterator();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("head--> ");
        Iterator iterator = new CLLIterator(false);

        while (iterator.hasNext()) {
            buffer.append(iterator.next().toString());
            if (iterator.hasNext()) {
                buffer.append(" <--> ");
            }
        }
        buffer.append(" <-->...");

        return buffer.toString();
    }

    class Node {

        private T obj;
        private Node next;

        Node(T obj, Node next) {
            this.obj = obj;
            this.next = next;
        }

        @Override
        public String toString() {
            return "[" + obj + "]";
        }
    }

    class CLLIterator implements Iterator {

        private Node current;
        private Node previous;
        private boolean circular;
        private int count;

        public CLLIterator() {
            if (tail != null) {
                current = tail.next;
            }
            circular = true;
        }

        public CLLIterator(boolean circular) {
            this();
            this.circular = circular;
        }

        @Override
        public boolean hasNext() {
            if (circular) {
                //if cicular then the iterator will only stop when list is empty
                return current != null;
            } else {
                if ((current == null) || (current == tail.next && count > 1)) {
                    return false;
                } else {
                    count++;
                    return true;
                }
            }
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                return null;
            } else {
                previous = current;
                current = current.next;
                return previous.obj;
            }
        }

        @Override
        public void remove() {
            if (hasNext()) {
                Node temp = current;
                if (previous != null) {
                    previous.next = current.next;
                } else {
                    tail = current.next;
                }
                current = current.next;
                temp.next = null;
            }
        }
    }
}
