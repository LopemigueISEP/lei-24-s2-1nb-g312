package pt.ipp.isep.dei.g312.domain;

public enum TaskStatus {
    PENDING("Pending", 1),
    PLANNED("Planned", 2),
    POSTPONED("Postponed", 2),
    CANCELED("Canceled", 2),
    DONE("Done", 2);
    private final String description;
    private final int level;

    private TaskStatus(String description, int level) {
        this.description = description;
        this.level = level;
    }

    @Override
    public String toString() {
        return description;
    }


}
