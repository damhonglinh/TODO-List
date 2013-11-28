package todolistapp;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Dam Linh
 */
public class TaskTimeComparator implements Comparator<Task>, Serializable {

    private boolean ascendingOrder;

    public TaskTimeComparator(boolean ascendingOrder) {
        this.ascendingOrder = ascendingOrder;
    }

    @Override
    public int compare(Task o1, Task o2) {
        int result = (o1.getTimeString().compareToIgnoreCase(o2.getTimeString()));
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
