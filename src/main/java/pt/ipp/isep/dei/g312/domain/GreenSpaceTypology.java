package pt.ipp.isep.dei.g312.domain;

public enum GreenSpaceTypology {
    GARDEN("Garden", 1),
    MEDIUM("Medium-Sized Park", 2),
    LARGE("Large-Sized Park", 2);
    private final String description;
    private final int level;

    private GreenSpaceTypology(String description, int level) {
        this.description = description;
        this.level = level;
    }

    @Override
    public String toString() {
        return description;
    }
}
