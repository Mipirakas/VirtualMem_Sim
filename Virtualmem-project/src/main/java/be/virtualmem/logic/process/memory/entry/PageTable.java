package be.virtualmem.logic.process.memory.entry;

import be.virtualmem.logic.process.memory.IPageTable;

import java.util.HashMap;
import java.util.Map;

public class PageTable implements IPageTable {
    private Map<Integer, IPageEntry> entries;

    public PageTable() {
        entries = new HashMap<>();
    }

    public IPageEntry getEntry(int id) {
        return entries.get(id);
    }

}
