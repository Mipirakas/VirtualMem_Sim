package be.virtualmem.logic.process.memory.table;


import be.virtualmem.logic.process.memory.entry.IPageEntry;

public interface IPageTable {
    IPageEntry getEntry(Long id);
    void removeEntries();
    boolean isEmpty();
}
