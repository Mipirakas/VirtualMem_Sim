package be.virtualmem.logic.process.memory.reallocation;

import be.virtualmem.logic.process.memory.Frame;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SecondChanceAlgorithm implements IAlgorithm {
    private TreeMap<Integer, Frame> frames;
    private ReorderingSet<Integer> reorderingSet;

    public SecondChanceAlgorithm(Map<Integer, Frame> frames) {
        this.frames = new TreeMap<>(frames);
        reorderingSet = new ReorderingSet<>();
    }

    public int getStartIndex() {
        return reorderingSet.peek() == null ? 0 : reorderingSet.peek();
    }

    public Integer frameIdToReallocate() {
        // Check if a frame has no pages
        for (Entry<Integer, Frame> entry : frames.entrySet())
            if (entry.getValue().getPage() == null) {
                reorderingSet.add(entry.getKey());
                return entry.getKey();
            }

        // Create states, no frame with null values
        Map<Integer, PageState> states = new LinkedHashMap<>();

        // First: process entries from startIndex and up
        for (Entry<Integer, Frame> entry : frames.tailMap(reorderingSet.peek()).entrySet()) {
            states.put(entry.getKey(), new PageState(entry.getValue().getPage()));
        }

        // Then: process entries before startIndex
        for (Entry<Integer, Frame> entry : frames.headMap(reorderingSet.peek()).entrySet()) {
            states.put(entry.getKey(), new PageState(entry.getValue().getPage()));
        }

        // Works worse?
        /*for (Integer i : reorderingSet.toSet()) {
            states.put(i, new PageState(frames.get(i).getPage()));
        }*/

        return frameLoop(states);
    }

    private Integer frameLoop(Map<Integer, PageState> states) {
        for (int i = 0; i < 2; i++) {
            int offset = 0;
            for (Entry<Integer, PageState> state : states.entrySet()) {
                offset += i;
                PageState pageState = state.getValue();

                switch (getClass(pageState)) {
                    case 0:
                        reorderingSet.add(offset);
                        return state.getKey();
                    case 1:
                        state.getValue().requestCopy().setDirty(0); break;
                    // If class is 2 or 3 set accesed to 0
                    case 2, 3:
                        state.getValue().setAccessed(0); break;
                    default:
                        throw new IllegalStateException("Unexpected class value: " + getClass(pageState));
                }
            }
        }
        throw new IllegalStateException("No frame found");
    }

    private Integer getClass(PageState pageState) {
        if (pageState.requestCopy().getAccessed() == 0 && pageState.requestCopy().getDirty() == 0)
            return 0;
        else if (pageState.requestCopy().getAccessed() == 0 && pageState.requestCopy().getDirty() == 1)
            return 1;
        else if (pageState.requestCopy().getAccessed() == 1 && pageState.requestCopy().getDirty() == 0)
            return 2;
        else if (pageState.requestCopy().getAccessed() == 1 && pageState.requestCopy().getDirty() == 1)
            return 3;
        else
            throw new IllegalStateException("Fault in pageState");
    }
}
