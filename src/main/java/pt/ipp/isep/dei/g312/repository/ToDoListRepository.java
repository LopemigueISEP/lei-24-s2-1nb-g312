package pt.ipp.isep.dei.g312.repository;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.g312.domain.ToDoEntry;
import java.util.ArrayList;

import java.util.List;


public class ToDoListRepository {

    private List<ToDoEntry> toDoList = new ArrayList<>();

    public ObservableList<ToDoEntry> getToDoList() {
        return FXCollections.observableArrayList(toDoList); // Return an ObservableList
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

