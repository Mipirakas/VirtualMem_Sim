package be.virtualmem.logic.process.memory.table;

import be.virtualmem.global.Constants;
import be.virtualmem.logic.process.memory.entry.IPageEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PageTable implements IPageTable {
    private Map<Long, IPageEntry> entries;
    private int level;
    private int pageTableSize;
    private Supplier<IPageEntry> pageEntrySupplier;

    public PageTable(int level, Supplier<IPageEntry> pageEntrySupplier) {
        entries = new HashMap<>();
        this.level = level;
        pageTableSize = (int) Math.pow(2, Constants.PAGE_TABLE_ENTRIES[level]);
        this.pageEntrySupplier = pageEntrySupplier;
    }

    public IPageEntry getEntry(Long id) {
        return entries.get(id);
    }

    public void addEntry(Long id) {
        entries.putIfAbsent(id, pageEntrySupplier.get());
    }

    public void removeEntry(Long id) {
        entries.remove(id);
    }

    public int getNrOfEntries() {
        return entries.size();
    }

    public boolean isEmpty() {
        for (Map.Entry<Long, IPageEntry> entry : entries.entrySet()) {
            if (entry.getValue() != null)
                return false;
        }
        return true;
    }

    public void removeEntries() {
        for (Map.Entry<Long, IPageEntry> entry : entries.entrySet()) {
            entry.setValue(null);
        }
    }
}
