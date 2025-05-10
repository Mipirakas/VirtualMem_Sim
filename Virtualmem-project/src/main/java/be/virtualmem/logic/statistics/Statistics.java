package be.virtualmem.logic.statistics;

import be.virtualmem.logic.statistics.action.ActionType;
import be.virtualmem.logic.statistics.action.IAction;

import java.util.*;

public class Statistics {
    private final static Statistics instance = new Statistics();
    int pageInCount; // Disk -> Memory
    int pageOutCount; // Memory -> Disk (does not happen when page not dirty)
    int pageEvictionCount; // Memory page removes
    Deque<IAction> actions;

    private Statistics() {
        actions = new ArrayDeque<>();
        pageInCount = 0;
        pageOutCount = 0;
        pageEvictionCount = 0;
    }

    public static Statistics getInstance() {
        return instance;
    }

    public void incrementPageInCount() {
        pageInCount++;
    }
    public void incrementPageOutCount() {
        pageOutCount++;
    }
    public void incrementPageEvictionCount() {
        pageEvictionCount++;
    }

    public void addAction(IAction action) {
        actions.push(action);
    }

    public IAction[] getActions() {
        return actions.toArray(new IAction[0]);
    }

    public IAction getLastActionOfType(ActionType type) {
        for (IAction action : actions) {
            if (action.getActionType() == type)
                return action;
        }
        return null;
    }

    public Map<String, Integer> map() {
        Map<String, Integer> map = new HashMap<>();
        map.put("pageInCount", pageInCount);
        map.put("pageOutCount", pageOutCount);
        map.put("pageEvictionCount", pageEvictionCount);
        return map;
    }
}
