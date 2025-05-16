package be.virtualmem.logic.process.memory.reallocation;

import be.virtualmem.global.Constants;
import be.virtualmem.logic.process.memory.Frame;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

// We can call it WAF
public class WeightedAgeFrequencyAlgorithm implements IAlgorithm {
    private TreeMap<Integer, Frame> frames;
    private int weight;

    public WeightedAgeFrequencyAlgorithm(){}

    public WeightedAgeFrequencyAlgorithm(Map<Integer, Frame> frames) {
        this.frames = new TreeMap<>(frames);
        this.weight = Constants.WAF_WEIGHT;
    }

    public int getStartIndex() {
        return 0;
    }

    private int calculateVictim() {
        int victim= 0;
        int highestCost = 0;

        for (Entry<Integer, Frame> entry : frames.entrySet()) {
            Frame frame = entry.getValue();
            int cost = (frame.calculateAge()) / (frame.getFrequency() + weight * frame.getPage().getDirty());

            if (cost > highestCost) {
                highestCost = cost;
                victim = entry.getKey();
            }
        }

        return victim;
    }

    public Integer frameIdToReallocate() {
        // Check if a frame has no pages
        for (Entry<Integer, Frame> entry : frames.entrySet())
            if (entry.getValue().getPage() == null) {
                return entry.getKey();
            }

        return calculateVictim();
    }
}
