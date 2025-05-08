package be.virtualmem.logic.process.memory.reallocation;

import be.virtualmem.logic.process.memory.Frame;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SecondChanceAlgorithm  implements IAlgorithm {
    private Map<Integer, Frame> frames;

    public SecondChanceAlgorithm(Map<Integer, Frame> frames) {
        this.frames = frames;
    }

    public Integer frameIdToReallocate() {
        // Check if a frame has no pages
        for (Entry<Integer, Frame> entry : frames.entrySet())
            if (entry.getValue().getPage() == null)
                return entry.getKey();

        // Create states, no frame with null values
        Map<Integer, PageState> states = new HashMap<>();
        for (Entry<Integer, Frame> entry : frames.entrySet())
            states.put(entry.getKey(), new PageState(entry.getValue().getPage()));

        return frameLoop(states);
    }

    private Integer frameLoop(Map<Integer, PageState> states) {
        for (int i = 0; i < 2; i++) {
            for (Entry<Integer, PageState> state : states.entrySet()) {
                PageState pageState = state.getValue();

                switch (getClass(pageState)) {
                    case 0:
                        return state.getKey();
                    case 1:
                        state.getValue().requestCopy().setDirty(0); break;
                    // If class is 2 or 3 set accesed to 0
                    case 2:
                    case 3:
                        state.getValue().requestCopy().setAccessed(0); break;
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
