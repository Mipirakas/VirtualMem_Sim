package be.virtualmem.logic.statistics.action;

import java.util.Map;

public interface IAction {
    void addProperty(Property property, String value);
    ActionType getActionType();
    Map<Property, String> getPropertyStringMap();
}
