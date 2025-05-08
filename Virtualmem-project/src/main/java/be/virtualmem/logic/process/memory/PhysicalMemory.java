package be.virtualmem.logic.process.memory;


import be.virtualmem.global.Constants;
import be.virtualmem.logic.process.memory.reallocation.IAlgorithm;
import be.virtualmem.logic.process.memory.reallocation.SecondChanceAlgorithm;

import java.util.HashMap;
import java.util.Map;

// Singleton
public class PhysicalMemory {
    private static final PhysicalMemory instance = new PhysicalMemory();
    private IAlgorithm algorithm;

    private Map<Integer, Frame> frames;
    // Reallocation algorithm

    private PhysicalMemory() {
        frames = new HashMap<>();

        for (int i = 0; i < Constants.FRAMES_IN_RAM; i++){
            frames.put(i, new Frame());
        }

        algorithm = new SecondChanceAlgorithm(frames);
    }

    public static PhysicalMemory getInstance() {
        return instance;
    }

    public Page swapPage(Page page) {
        // Return the frame where the page was inserted based on
        Integer frameId = algorithm.frameIdToReallocate();
        Page oldPage = null;

        if (frameId != null) {
            Frame frame = frames.get(frameId);
            if (frame != null) {
                oldPage = frame.getPage();
                frame.setPage(page);
            }
        }
        return oldPage;
    }

    public Map<Integer, Frame> getFrames() {
        return frames;
    }
}
