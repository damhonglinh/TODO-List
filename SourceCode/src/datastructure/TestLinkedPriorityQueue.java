package datastructure;

import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author Dam Linh - s3372757
 */
public class TestLinkedPriorityQueue {

    public static void main(String[] args) {
        LinkedPriorityQueue<Integer> queue = new LinkedPriorityQueue<>();

        queue.enqueue(0, 10);
        queue.enqueue(1, 1);
        queue.enqueue(2, 1);
        queue.enqueue(3, 5);
        queue.enqueue(4, 5);
        queue.enqueue(5, 3);
        queue.enqueue(6, 4);
        queue.enqueue(7, 2);
        queue.enqueue(8, 4);
        queue.enqueue(9, 2);
        queue.enqueue(10, 10);
        queue.enqueue(11, 10);
        queue.enqueue(12, 10);
        queue.enqueue(8, 9);
        queue.enqueue(13, 9);
        queue.enqueue(14, 1);
        queue.enqueue(8, 3);
        queue.enqueue(15, 7);

        System.out.println(queue);

        //testing remove
        queue.remove(0);
        queue.remove(8);
        queue.remove(3);
        System.out.println(queue);

        //testing dequeue
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();

        queue.enqueue(16, 8);
        queue.enqueue(17, 8);
        queue.enqueue(18, 2);
        queue.enqueue(19, 2);

        System.out.println(queue);
    }
}