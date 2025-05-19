package be.virtualmem.logic.process.memory;


import be.virtualmem.global.Constants;
import be.virtualmem.global.address.Address;
import be.virtualmem.logic.process.memory.reallocation.IAlgorithm;
import be.virtualmem.logic.process.memory.reallocation.SecondChanceAlgorithm;
import be.virtualmem.logic.process.memory.reallocation.WeightedAgeFrequencyAlgorithm;
import be.virtualmem.logic.statistics.Statistics;
import be.virtualmem.logic.storage.BackingStore;

import java.util.*;

// Singleton
public class PhysicalMemory {
    private static final PhysicalMemory instance = new PhysicalMemory();
    private IAlgorithm algorithm;
    private IAlgorithm setAlgorithm;

    private Map<Integer, Frame> frames;
    // Reallocation algorithm

    private PhysicalMemory() {
        init();
    }

    public void resetPhysicalMemory() {
        init();
    }

    private void init() {
        frames = new HashMap<>();

        for (int i = 0; i < Constants.FRAMES_IN_RAM; i++){
            frames.put(i, new Frame());
        }

        if (setAlgorithm != null)
            setAlgorithm(setAlgorithm);
        else
            algorithm = new WeightedAgeFrequencyAlgorithm(frames);
    }

    public void setAlgorithm(IAlgorithm algorithm) {
        if (algorithm != null) {
            setAlgorithm = algorithm;
            if (algorithm instanceof SecondChanceAlgorithm)
                this.algorithm = new SecondChanceAlgorithm(frames);
            else if (algorithm instanceof WeightedAgeFrequencyAlgorithm)
                this.algorithm = new WeightedAgeFrequencyAlgorithm(frames);
        }
    }

    public static PhysicalMemory getInstance() {
        return instance;
    }

    public void swapPage(Integer frameId, int pid, Page page) {
        Page oldPage;

        if (frameId != null) {
            Frame frame = frames.get(frameId);
            if (frame != null) {
                oldPage = frame.getPage();
                // Frame was not empty
                if (oldPage != null) {
                    Statistics.getInstance().incrementPageEvictionCount();
                    // If the page was written to
                    if (oldPage.getDirty() == 1) {
                        Statistics.getInstance().incrementPageOutCount();
                        BackingStore.getInstance().addPage(frame.getPid(), oldPage.getAddress(), oldPage);
                    }
                    oldPage.pageOut();
                }
                Statistics.getInstance().incrementPageInCount();
                frame.setPage(page);
                frame.setPid(pid);
            }
        }
    }

    public Integer frameIdToReallocate() {
        return algorithm.frameIdToReallocate();
    }

    public Map<Integer, Frame> getFrames() {
        return frames;
    }

    public Frame getFrame(int frameId) {
        return frames.get(frameId);
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
        Frame frame = frames.get(pfn);
        Page page = frame.getPage();
        if (page != null) {
            Statistics.getInstance().incrementPageEvictionCount();
            // If the page was written to
            if (page.getDirty() == 1) {
                Statistics.getInstance().incrementPageOutCount();
                BackingStore.getInstance().addPage(frame.getPid(), page.getAddress(), page);
            }
            page.pageOut();
        }
        frame.setPage(null);
    }

    public int getAlgoStartIndex() {
        return algorithm.getStartIndex();
    }
}
