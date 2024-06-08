package pt.ipp.isep.dei.g312.domain;

public enum TaskPosition {
    TODOLIST("ToDoList", 1),
    AGENDA("Agenda", 2);
    private final String description;
    private final int level;

    private TaskPosition(String description, int level) {
        this.description = description;
        this.level = level;
    }

    @Override
    public String toString() {
        return description;
    }

}
