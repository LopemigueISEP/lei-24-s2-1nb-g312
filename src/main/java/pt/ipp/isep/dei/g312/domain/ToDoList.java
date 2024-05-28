package pt.ipp.isep.dei.g312.domain;

import pt.ipp.isep.dei.g312.repository.Repositories;

public class ToDoList implements Comparable<ToDoList> {
    private String task;
    private String greenSpace;
    private final String status = "Pending";

    public ToDoList(String task, String greenSpace) {
        this.task = task;
        this.greenSpace = greenSpace;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getGreenSpace() {
        return greenSpace;
    }

    public void setGreenSpace(String greenSpace) {
        this.greenSpace = greenSpace;
    }
    public String getStatus() {
        return status; // Retorna o status fixo
    }
    public boolean add(ToDoList toDoEntryList) {
        // This method is not intended for adding entries to the repository
        // It's meant for internal logic within the ToDoList class (if needed)
        return false;
    }

    public static ToDoList addEntryToDoList(String task, String greenSpace, boolean userValidation) {
        if (userValidation) {
            ToDoList toDoEntryList = new ToDoList(task, greenSpace);
            if (Repositories.getInstance().getToDoRepository().addEntryToDoList(toDoEntryList)) {
                return toDoEntryList;
            } else {
                // Handle case where adding to repository fails (e.g., log error)
                return null;
            }
        } else {
            System.out.println("This user doesn't have permissions to register entries in the to-do list");
            return null;
        }
    }

    @Override
    public ToDoList clone() {
        return new ToDoList(this.task, this.greenSpace);
    }

    @Override
    public int compareTo(ToDoList o) {
        return this.task.compareTo(o.getTask());
    }

    @Override
    public String toString() {
        return String.format("Task: %s, Green Space: %s", task, greenSpace);
    }
}