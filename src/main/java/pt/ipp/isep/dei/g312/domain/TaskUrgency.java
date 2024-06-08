package pt.ipp.isep.dei.g312.domain;
/**
 * Represents the urgency level of a task within a system.
 * A task can have different urgencies such as "Low urgency", "Medium urgency", and "High urgency".
 */
public enum TaskUrgency {
        LOW("Low urgency", 1),
        MEDIUM("Medium urgency", 2),
        HIGH("High urgency", 3);

        private final String description;
        private final int level;
        /**
         * Constructs a new TaskUrgency with the specified description and level.
         *
         * @param description A brief description of the task urgency.
         * @param level       The level or ranking of the task urgency.
         */
        private TaskUrgency(String description, int level) {
                this.description = description;
                this.level = level;
        }
        /**
         * Returns a string representation of the task urgency.
         *
         * @return The description of the task urgency.
         */
        @Override
        public String toString() {
                return description;
        }

}
