package be.virtualmem.logic.process.memory;

import be.virtualmem.global.address.AddressTranslator;
import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.process.memory.entry.IPageEntry;
import be.virtualmem.logic.process.memory.entry.PageDirectoryEntry;
import be.virtualmem.logic.process.memory.entry.PageTable;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;

public class PageTableStructure {
    private PageTable baseTable;
    private int levels;

    public PageTableStructure(int levels) {
        // Create initial page table for a process
        baseTable = new PageTable(0, PageDirectoryEntry::new);
        this.levels = levels;
    }

    public PageTableEntry getPageTableEntry(IAddress address) {
        // Recursively look in Page Table Directories until Page Table Entry is found
        IPageTable pageTable = baseTable;
        PageTableEntry pageTableEntry = null;

        for (int i = 0; i < levels; i++) {
            Integer pageTableOffset = AddressTranslator.fromAddressToPageTableLevelEntryId(address, i);

            if (pageTableOffset != null) {
                IPageEntry pageEntry = pageTable.getEntry(pageTableOffset);

                if (pageEntry instanceof PageDirectoryEntry)
                    pageTable = ((PageDirectoryEntry) pageEntry).getPointer();
                else if (pageEntry instanceof PageTableEntry)
                    pageTableEntry = (PageTableEntry) pageEntry;
            }
        }

        return pageTableEntry;
    }

    public void mapPageTables(IAddress address, int size) {
        // Also create necessary Page Table Directories
    }

    public void unmapPageTables(IAddress address, int size) {
        // Also create necessary Page Table Directories
    }

    public IAddress getPhysicalAddress(Page page, IAddress virtualAddress) {
        // Calculate the physical address
        return null;
    }


}
