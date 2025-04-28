package be.virtualmem.global.process;

import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.process.memory.PageTableStructure;

public class ProcessMemory {
    private PageTableStructure pageTableStructure;

    public ProcessMemory() {
        pageTableStructure = new PageTableStructure();
        // Create memory
    }

    public void read(IAddress address) {

    }

    public void write(IAddress address) {

    }

    public void map(IAddress address, int size) {

    }

    public void unmap(IAddress address, int size) {

    }

    public void free() {
        // Free memory
    }
}
