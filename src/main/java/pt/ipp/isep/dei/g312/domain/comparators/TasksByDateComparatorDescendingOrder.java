package pt.ipp.isep.dei.g312.domain.comparators;

import pt.ipp.isep.dei.g312.domain.Task;

import java.util.Comparator;
import java.util.Date;

/**
 * Comparator class to compare tasks by start date in descending order.
 */
public class TasksByDateComparatorDescendingOrder implements Comparator<Task> {

    /**
     * Compares two tasks by start date in descending order.
     *
     * @param o1 the first task to compare
     * @param o2 the second task to compare
     * @return a negative integer, zero, or a positive integer if the first task is less than, equal to, or greater
     * than the second task, respectively
     */
    @Override
    public int compare(Task o1, Task o2) {

        int i = o2.getStartDate().compareTo(o1.getStartDate());
        if(i == 0){
            return 0;
        } else if (i>0) {
            return 1;

        }else {
            return -1;
        }


    }
}
