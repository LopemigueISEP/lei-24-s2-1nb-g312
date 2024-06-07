package pt.ipp.isep.dei.g312.domain;

public enum GreenSpaceTypology {
    GARDEN("Garden", 1),
    MEDIUM("Medium-Sized Park", 2),
    LARGE("Large-Sized Park", 2);
    private final String description;
    private final int level;
    /**
     * Constructs a new GreenSpaceTypology with the specified description and level.
     *
     * @param description a brief description of the green space typology
     * @param level the level or ranking of the green space typology
     */
    private GreenSpaceTypology(String description, int level) {
        this.description = description;
        this.level = level;
    }
    /**
     * Returns a string representation of the green space typology.
     *
     * @return the description of the green space typology
     */
    @Override
    public String toString() {
        return description;
    }
}
