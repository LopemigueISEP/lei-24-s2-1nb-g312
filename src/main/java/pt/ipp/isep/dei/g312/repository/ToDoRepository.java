package pt.ipp.isep.dei.g312.repository;


import pt.ipp.isep.dei.g312.domain.ToDoList;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class ToDoRepository {

    private List<ToDoList> toDoEntryList = new ArrayList<>();

    public List<ToDoList> getToDoListEntries() {
        return new ArrayList<>(toDoEntryList); // Return a copy to avoid modification
    }

    public boolean addEntryToDoList(ToDoList toDoEntry) {
        if (toDoEntry == null || containsToDoListWithName(toDoEntry.getTask())) {
            return false;
        }
        return toDoEntryList.add(toDoEntry);
    }




    private void sort(ToDoList toDoList) {
    }

    public boolean containsToDoListWithName(String name) {
        for (ToDoList item : toDoEntryList) {
            if (item.getTask().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

