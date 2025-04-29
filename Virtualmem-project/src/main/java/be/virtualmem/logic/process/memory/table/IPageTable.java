package be.virtualmem.logic.process.memory.table;


import be.virtualmem.logic.process.memory.entry.IPageEntry;

import java.math.BigInteger;

public interface IPageTable {
    IPageEntry getEntry(BigInteger id);
    void removeEntries();
    boolean isEmpty();
}
