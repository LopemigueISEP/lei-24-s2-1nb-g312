package pt.ipp.isep.dei.g312.domain;
/**
 * Represents the status of a task within a system.
 * A task can have different statuses such as "Pending", "Planned", "Postponed", "Canceled", and "Done".
 */
public enum TaskStatus {
    PENDING("Pending", 1),
    PLANNED("Planned", 2),
    POSTPONED("Postponed", 2),
    CANCELED("Canceled", 2),
    DONE("Done", 2);
    private final String description;
    private final int level;
    /**
     * Constructs a new TaskStatus with the specified description and level.
     *
     * @param description A brief description of the task status.
     * @param level       The level or ranking of the task status.
     */
    private TaskStatus(String description, int level) {
        this.description = description;
        this.level = level;
    }
    /**
     * Returns a string representation of the task status.
     *
     * @return The description of the task status.
     */
    @Override
    public String toString() {
        return description;
    }


}
