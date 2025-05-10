package be.virtualmem.logic.statistics.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class WriteAction implements IAction{
    private final static ActionType actionType = ActionType.WRITE;
    private Map<Property, String> propertyStringMap;

    public WriteAction(){
        propertyStringMap = new LinkedHashMap<>();
        propertyStringMap.put(Property.ACTION_TYPE, actionType.getValue());
    }

    @Override
    public void addProperty(Property property, String value){
        propertyStringMap.put(property, value);
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public Map<Property, String> getPropertyStringMap() {
        return propertyStringMap;
    }
}
