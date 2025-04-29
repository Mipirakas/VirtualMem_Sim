package be.virtualmem.logic.process.memory;


import be.virtualmem.logic.process.memory.entry.IPageEntry;

public interface IPageTable {
    IPageEntry getEntry(int id);
}
