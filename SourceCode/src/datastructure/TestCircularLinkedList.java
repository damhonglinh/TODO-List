package datastructure;

/**
 *
 * @author Dam Linh - s3372757
 */
public class TestCircularLinkedList {

    public static void main(String[] args) {
        CircularLinkedList<Integer> list = new CircularLinkedList();
//        CircularLinkedListUsingDoublyLinked<Integer> list = new CircularLinkedListUsingDoublyLinked();


        for (int i = 0; i < 10; i++) {
            System.out.print("Add to head " + i + ". ");
            list.addToHead(i);
        }
        System.out.println("\n" + list);

        System.out.println("Get from head " + list.getFromHead());

        for (int i = 0; i < 4; i++) {
            System.out.println("Remove from head " + list.removeFromHead());
        }
        System.out.println(list);
        System.out.println("Get from tail " + list.getFromTail());

        for (int i = 10; i < 20; i++) {
            list.addToTail(i);
            System.out.print("Add to tail " + i + ". ");
        }
        System.out.println("\n" + list);

        for (int i = 0; i < 24; i++) {
            System.out.println("Remove from tail " + list.removeFromTail());
        }
        System.out.println("\n" + list);
    }
}
