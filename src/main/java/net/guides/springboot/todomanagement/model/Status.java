package net.guides.springboot.todomanagement.model;

public enum Status {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getCssClass() {
        switch (this) {
            case TODO: return "label-danger";
            case IN_PROGRESS: return "label-warning";
            case COMPLETED: return "label-success";
            default: return "label-default";
        }
    }
}

