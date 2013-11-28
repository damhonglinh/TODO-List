package todolistapp;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Dam Linh
 */
public class TaskPriorityComparator implements Comparator<Task>, Serializable {

    private boolean ascendingOrder;

    public TaskPriorityComparator(boolean ascendingOrder) {
        this.ascendingOrder = ascendingOrder;
    }

    @Override
    public int compare(Task o1, Task o2) {
        int result;
        if (o1.getPriority() > o2.getPriority()) {
            result = 1;
        } else if (o1.getPriority() < o2.getPriority()) {
            result = -1;
        } else {
            result = 0;
        }
        //the tasks that are finished should be the least
        if (o1.isFinished() && !o2.isFinished()) {
            result = 1;
        } else if (!o1.isFinished() && o2.isFinished()) {
            result = -1;
        }

        if (ascendingOrder) {
            return result;
        } else {
            return -result;
        }
    }
}
