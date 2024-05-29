package pt.ipp.isep.dei.g312.repository;


import pt.ipp.isep.dei.g312.domain.ToDoEntry;
import java.util.ArrayList;

import java.util.List;


public class ToDoListRepository {

    private List<ToDoEntry> toDoList = new ArrayList<>();

    public List<ToDoEntry> getToDoList() {
        return new ArrayList<>(toDoList); // Return a copy to avoid modification
    }

    public boolean addEntryToDoList(ToDoEntry toDoEntry) {
        if (toDoEntry == null || containsToDoListWithName(toDoEntry.getTask())) {
            return false;
        }
        return toDoList.add(toDoEntry);
    }


    public boolean containsToDoListWithName(String name) {
        for (ToDoEntry item : toDoList) {
            if (item.getTask().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

