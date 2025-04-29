package be.virtualmem.logic.process.memory.entry;

import be.virtualmem.global.Constants;
import be.virtualmem.logic.process.memory.IPageTable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PageTable implements IPageTable {
    private Map<Integer, IPageEntry> entries;
    private int pageTableSize;

    public PageTable(int level, Supplier<IPageEntry> pageEntrySupplier) {
        entries = new HashMap<>();
        pageTableSize = (int) Math.pow(2, Constants.PAGE_TABLE_ENTRIES[level]);

        for (int i = 0; i < pageTableSize; i++) {
            entries.put(i, pageEntrySupplier.get());
        }
    }

    public IPageEntry getEntry(int id) {
        return entries.get(id);
    }

}
