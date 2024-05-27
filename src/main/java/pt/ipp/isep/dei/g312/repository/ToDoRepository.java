package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.ToDoList;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;




public class ToDoRepository {

    private List<ToDoList> toDoList = new ArrayList<>();

    public List<ToDoList> getToDoListEntries() {
        return new ArrayList<>(toDoList); // Return a copy to avoid modification
    }

    public boolean addEntryToDoList(ToDoList toDoList) {
        Optional<ToDoList> newEntry;
        boolean operationSuccess = false;

        if (containsToDoListWithName(String.valueOf(toDoList))) {
            newEntry = Optional.of(toDoList.clone());
            operationSuccess = toDoList.add(newEntry.get());
            sort(toDoList);

        }
        if (!operationSuccess) {
            newEntry = Optional.empty();
        }
        return true;
    }

    private void sort(ToDoList toDoList) {
    }

    public boolean containsToDoListWithName(String name) {
        for (ToDoList item : toDoList) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

