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

    public boolean addEntryToDoList(ToDoList toDoEntryList) {
        Optional<ToDoList> newEntry;
        boolean operationSuccess = false;

        if (containsToDoListWithName(String.valueOf(toDoEntryList))) {
            newEntry = Optional.of(toDoEntryList.clone());
            operationSuccess = toDoEntryList.add(newEntry.get());

        }
        if (!operationSuccess) {
            newEntry = Optional.empty();
        }
        return true;
    }




    private void sort(ToDoList toDoList) {
    }

    public boolean containsToDoListWithName(String name) {
        for (ToDoList item : toDoEntryList) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

