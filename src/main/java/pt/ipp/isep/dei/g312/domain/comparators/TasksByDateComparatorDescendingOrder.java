package pt.ipp.isep.dei.g312.domain.comparators;

import pt.ipp.isep.dei.g312.domain.Task;

import java.util.Comparator;
import java.util.Date;

public class TasksByDateComparatorDescendingOrder implements Comparator<Task> {
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
