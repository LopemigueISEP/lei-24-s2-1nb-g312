package pt.ipp.isep.dei.g312.domain;


import pt.ipp.isep.dei.g312.repository.Repositories;

public class ToDoList implements Comparable<ToDoList>{
    private String name;
    private String greenSpace;

    private final String status = "Pending"; // Encapsulate status as it's final

    public ToDoList(String name, String greenSpace) {
        this.name = name;
        this.greenSpace = greenSpace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }
    public String getGreenSpace() {
        return greenSpace;
    }

    public void setGreenSpace(String greenSpace) {
        this.greenSpace = greenSpace;
    }

    public boolean add(ToDoList toDoEntryList) {
        // This method is not intended for adding entries to the repository
        // It's meant for internal logic within the ToDoList class (if needed)
        return false;
    }
    public static ToDoList addEntryToDoList(String name, String greenSpace, boolean userValidation) {
        if (userValidation) {
            ToDoList toDoEntryList = new ToDoList(name, greenSpace);
            if (Repositories.getInstance().getToDoRepository().addEntryToDoList(toDoEntryList)) {
                return toDoEntryList;
            } else {
                // Handle case where adding to repository fails (e.g., log error)
                return null;
            }
        } else {
            System.out.println("This user don't have permissions to register entries in to-do list");
            return null;
        }
    }

    @Override
    public ToDoList clone() {
        return new ToDoList(this.name,  this.greenSpace);
    }

    @Override
    public int compareTo(ToDoList o) {
        return this.name.compareTo(o.getName());
    }
}

