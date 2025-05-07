package be.virtualmem.logic.process.memory;


import be.virtualmem.global.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Singleton
public class PhysicalMemory {
    private static final PhysicalMemory instance = new PhysicalMemory();
    private Map<Integer, Frame> frames;
    // Reallocation algorithm

    private PhysicalMemory() {
        frames = new HashMap<>();

        for (int i = 0; i < Constants.FRAMES_IN_RAM; i++){
            frames.put(i, new Frame());
        }
    }

    public static PhysicalMemory getInstance() {
        return instance;
    }

    public Frame insertPage(Page page) {
        // Return the frame where the page was inserted based on
        return null;
    }

    public Map<Integer, Frame> getFrames() {
        return frames;
    }
}
