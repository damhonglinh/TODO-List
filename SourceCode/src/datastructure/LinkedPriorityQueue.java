package datastructure;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author s3372757 - Dam Linh
 */
public class LinkedPriorityQueue<T> implements PriorityQueue<T>, Serializable {

    private Node head;

    @Override
    public void enqueue(T obj, int priority) {
        if (priority > 10 || priority < 1) {
            return;
        }
        if (head == null) {//if empty queue
            head = new Node(obj, null, priority);
        } else {
            Node temp = new Node(null, head, 0);
            //finding the node that is right after the last node
            //with the priority greater or equal to the added priority
            //after this while loop, the "last node" is temp and
            //the node needs to be found is temp.next
            while (temp.next != null && temp.next.priority >= priority) {
                temp = temp.next;
            }
            if (temp.obj == null) {
                head = new Node(obj, head, priority);
            } else {
                temp.next = new Node(obj, temp.next, priority);
            }
        }
    }

    @Override
    public void enqueue(T obj) {
        enqueue(obj, 5);
    }

    @Override
    public T dequeue() {
        Node temp = head;
        head = head.next;
        temp.next = null;//clear the un-used reference
        return temp.obj;
    }

    public boolean remove(T obj) {
        Node temp = head;
        Node prev = null;
        //finding the node with temp.obj == obj
        while (temp != null) {
            if (temp.obj == obj) {
                if (prev == null) {//if head.obj = obj
                    head = head.next;
                } else {
                    prev.next = temp.next;
                }
                temp.next = null;
                return true;
            }
            prev = temp;
            temp = temp.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Head--> ");
        Iterator<T> iter = new LPQIterator();

        while (iter.hasNext()) {
            str.append(iter.next().toString());
            if (iter.hasNext()) {
                str.append(" <--> ");
            }
        }
        str.append(" <--tail");
        return str.toString();
    }

    @Override
    public Iterator<T> getIterator() {
        return new LPQIterator();
    }

    class Node implements Serializable {

        private T obj;
        private Node next;
        private int priority;

        Node(T obj, Node next, int priority) {
            this.obj = obj;
            this.next = next;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "[" + obj + "]";
        }
    }

    class LPQIterator implements Iterator<T> {

        private Node current;
        private Node previous;

        LPQIterator() {
            this.current = head;
            previous = null;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            } else {
                previous = current;
                current = current.next;
                return previous.obj;
            }
        }

        /**
         * @author Denis
         */
        @Override
        public void remove() {
            // if there is a node to remove
            if (current != null) {
                Node temp = current;
                // if there is a previous then make the reference skip over
                // the current node
                if (previous != null) {
                    previous.next = current.next;
                } else {
                    // if there is no previous, it means we must be removing 
                    // the head of the list
                    head = current.next;
                }
                // move the current to the next node
                current = current.next;
                // and clean up the next reference of the deleted node
                temp.next = null;
            }
        }
    }
    /**
     * the remain section is just an additional sorting feature of the
     * LinkedPriorityQueue
     */
    private Comparator<T> comparator;

    public void sort() {
        head = sort(head);
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * The following merge sort is adapted from the linked list merge sort
     * algorithm created by Jayadev Chandrasekhar which can be obtained from
     * http://www.dontforgettothink.com/2011/11/23/merge-sort-of-linked-list/
     */
    private Node sort(Node head) {
        if (comparator == null) {
            return head;
        }
        if (head == null || head.next == null) {
            return head;
        }

        Node middle = getMiddle(head);//get middle of the queue
        Node right = middle.next;// first node of the right half
        middle.next = null;// terminate connection between 2 halves

        return merge(sort(head), sort(right));
    }

    private Node merge(Node left, Node right) {
        // this Node does nothing but hold the first node of this sub list
        Node dummyHead = new Node(null, null, 0);
        Node current = dummyHead;// current pointer of the result list
        while (left != null && right != null) {
            if (comparator.compare(left.obj, right.obj) <= 0) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }
        //continue the node that is not null
        if (left == null) {
            current.next = right;
        } else {
            current.next = left;
        }
        return dummyHead.next;
    }

    private Node getMiddle(Node head) {
        if (head == null) {
            return head;
        }
        Node slow = head;
        Node fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
