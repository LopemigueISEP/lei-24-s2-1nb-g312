package pt.ipp.isep.dei.g312.domain;

public enum TaskUrgency {
        LOW("Low urgency", 1),
        MEDIUM("Medium urgency", 2),
        HIGH("High urgency", 3);

        private final String description;
        private final int level;

        private TaskUrgency(String description, int level) {
                this.description = description;
                this.level = level;
        }

        @Override
        public String toString() {
                return description;
        }

}
