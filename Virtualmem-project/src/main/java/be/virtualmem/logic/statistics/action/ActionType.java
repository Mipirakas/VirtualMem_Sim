package be.virtualmem.logic.statistics.action;

public enum ActionType {
    READ("READ"),
    WRITE("WRITE");

    private final String value;

    // Constructor
    ActionType(String value) {
        this.value = value;
    }

    // Getter
    public String getValue() {
        return value;
    }
}
