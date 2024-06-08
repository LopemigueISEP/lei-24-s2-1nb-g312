package pt.ipp.isep.dei.g312.domain;
/**
 * Represents the position of a task within a system.
 * A task can either be in the "ToDoList" or "Agenda".
 */
public enum TaskPosition {
    TODOLIST("ToDoList", 1),
    AGENDA("Agenda", 2);
    private final String description;
    private final int level;
    /**
     * Constructs a new TaskPosition with the specified description and level.
     *
     * @param description A brief description of the task position.
     * @param level       The level or ranking of the task position.
     */
    private TaskPosition(String description, int level) {
        this.description = description;
        this.level = level;
    }
    /**
     * Returns a string representation of the task position.
     *
     * @return The description of the task position.
     */
    @Override
    public String toString() {
        return description;
    }

}
