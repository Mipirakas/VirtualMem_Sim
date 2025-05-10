package be.virtualmem.logic.statistics.action;

import be.virtualmem.presentation.tui.IPrintTUI;

public enum Property implements IPrintTUI {
    ACTION_TYPE("Action Type"),
    PHYSICAL_ADDRESS("Physical Address"),
    VIRTUAL_ADDRESS("Virtual Address");

    private final String value;

    // Constructor
    Property(String value) {
        this.value = value;
    }

    // Getter
    public String getValue() {
        return value;
    }

    @Override
    public String print() {
        return value;
    }
}
