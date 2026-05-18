package net.guides.springboot.todomanagement.model;

public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent");

    private final String label;

    Priority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getCssClass() {
        switch (this) {
            case LOW: return "label-default";
            case MEDIUM: return "label-info";
            case HIGH: return "label-warning";
            case URGENT: return "label-danger";
            default: return "label-default";
        }
    }
}

