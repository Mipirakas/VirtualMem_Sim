package be.virtualmem.logic.process.memory;

import be.virtualmem.global.Constants;
import be.virtualmem.global.address.IAddress;

import java.util.HashMap;
import java.util.Map;

public class ProcessMemory {
    private PageTableStructure pageTableStructure;
    private Map<Integer, Page> pages;

    public ProcessMemory() {
        pageTableStructure = new PageTableStructure(Constants.PAGE_TABLE_LEVEL);
        pages = new HashMap<>();
        // Create memory
    }

    public void read(IAddress address) {
        // If memory not mapped yet, end process (Segmentation fault)
        // If memory is mapped, but not in physical memory, reallocate
    }

    public void write(IAddress address) {
        // If memory not mapped yet, end process (Segmentation fault)
        // If memory is mapped, but not in physical memory, reallocate
    }

    public void map(IAddress address, int size) {

    }

    public void unmap(IAddress address, int size) {

    }

    public void free() {
        // Free memory
    }
}
