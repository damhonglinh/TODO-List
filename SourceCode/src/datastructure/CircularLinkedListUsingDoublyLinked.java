package datastructure;

import java.util.Iterator;

/**
 *
 * @author Dam Linh - s3372757
 */
public class CircularLinkedListUsingDoublyLinked<T> implements List<T> {

    //it is a circular so there should not be differences between a head and a tail
    private Node head;

    @Override
    public void addToHead(T o) {
        if (head == null) {
            head = new Node(o, null, null);
            head.next = head;
            head.prev = head;
        } else {
            head = new Node(o, head.prev, head);
            head.next.prev = head;
            head.prev.next = head;
        }
    }

    //it is a circular so there should not be differences between a head and a tail
    @Override
    public void addToTail(T o) {
        addToHead(o);
    }

    @Override
    public T getFromHead() {
        if (head == null) {
            return null;
        }
        return head.obj;
    }

    //it is a circular so there should not be differences between a head and a tail
    @Override
    public T getFromTail() {
        return getFromHead();
    }

    @Override
    public T removeFromHead() {
        if (head == null) {
            return null;
        }
        Node temp = head;
        head.next.prev = head.prev;
        head.prev.next = head.next;
        if (head == head.next) {
            head = null;
        } else {
            head = head.next;
        }
        temp.prev = null;
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
        private Node prev;

        Node(T obj, Node prev, Node next) {
            this.obj = obj;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return "[" + obj + "]";
        }
    }

    class CLLIterator implements Iterator {

        private Node current;
        private boolean circular;
        private int count;

        public CLLIterator() {
            current = head;
            circular = true;
        }

        public CLLIterator(boolean circular) {
            current = head;
            this.circular = circular;
        }

        @Override
        public boolean hasNext() {
            if (circular) {
                return current != null;
            } else {
                if ((current == null) || (current == head && count > 1)) {
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
                Object obj = current.obj;
                current = current.next;
                return obj;
            }
        }

        @Override
        public void remove() {
            if (hasNext()) {
                Node temp = current;
                current.next.prev = current.prev;
                current.prev.next = current.next;
                current = current.next;
                temp.next = null;
                temp.prev = null;
            }
        }
    }
}
