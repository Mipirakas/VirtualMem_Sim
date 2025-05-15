package be.virtualmem.logic.process.memory;


import be.virtualmem.global.Constants;
import be.virtualmem.global.address.Address;
import be.virtualmem.logic.process.memory.reallocation.IAlgorithm;
import be.virtualmem.logic.process.memory.reallocation.SecondChanceAlgorithm;
import be.virtualmem.logic.process.memory.reallocation.WeightedAgeFrequencyAlgorithm;
import be.virtualmem.logic.statistics.Statistics;
import be.virtualmem.logic.storage.BackingStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        algorithm = new WeightedAgeFrequencyAlgorithm(frames);
    }

    public static PhysicalMemory getInstance() {
        return instance;
    }

    public Integer swapPage(Page page, int pid) {
        // Return the frame where the page was inserted based on
        Integer frameId = algorithm.frameIdToReallocate();
        Page oldPage;

        if (frameId != null) {
            Frame frame = frames.get(frameId);
            if (frame != null) {
                oldPage = frame.getPage();
                // Frame was not empty
                if (oldPage != null) {
                    BackingStore.getInstance().addPage(frame.getPid(), oldPage.getAddress(), oldPage);
                    Statistics.getInstance().incrementPageEvictionCount();
                    // If the page was written to
                    if (oldPage.getDirty() == 1)
                        Statistics.getInstance().incrementPageOutCount();
                    oldPage.pageOut();
                }
                frame.setPage(page);
                frame.setPid(pid);
            }
        }
        return frameId;
    }

    public Map<Integer, Frame> getFrames() {
        return frames;
    }

    public Page accessPage(int pid, Address address) throws Exception {
        Optional<Frame> frame = frames.values().stream()
                .filter(e -> e.getPid() == pid && e.getPage().getAddress().equals(address))
                .findFirst();

        if (frame.isPresent())
            return frame.get().accessPage();
        else
            throw new Exception("Page Fault was thrown");
    }

    public void removePID(int pid) {
        List<Integer> framesToRemove = frames.entrySet().stream()
                .filter(e -> e.getValue().getPid() == pid)
                .map(Map.Entry::getKey).toList();

        removeFrames(framesToRemove);
    }

    public void removeFrames(List<Integer> pfnList) {
        for (Integer pfn : pfnList) {
            removeFrame(pfn);
        }
    }

    public void removeFrame(int pfn) {
        frames.get(pfn).setPage(null);
    }

    public int getAlgoStartIndex() {
        return algorithm.getStartIndex();
    }
}
