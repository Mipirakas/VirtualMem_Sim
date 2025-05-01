package be.virtualmem.logic.process.memory.table;

import be.virtualmem.global.address.AddressTranslator;
import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.process.memory.Page;
import be.virtualmem.logic.process.memory.entry.IPageEntry;
import be.virtualmem.logic.process.memory.entry.PageDirectoryEntry;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;

import java.math.BigInteger;
import java.util.List;

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
            Long pageTableOffset = AddressTranslator.fromAddressToPageTableLevelEntryId(address, i);

            if (pageTableOffset != null && pageTable != null) {
                IPageEntry pageEntry = pageTable.getEntry(pageTableOffset);

                if (pageEntry instanceof PageDirectoryEntry)
                    pageTable = ((PageDirectoryEntry) pageEntry).getPointer();
                else if (pageEntry instanceof PageTableEntry)
                    pageTableEntry = (PageTableEntry) pageEntry;
            }
        }

        return pageTableEntry;
    }

    public void mapPageTables(List<Page> list) {
        // Also create necessary Page Table Directories
        for (Page page : list) {
            IPageTable pageTable = baseTable;

            // Map page per page (can be done more efficiently)
            for (int i = 0; i < levels; i++) {
                Long pageTableOffset = AddressTranslator.fromAddressToPageTableLevelEntryId(page.getAddress(), i);
                PageDirectoryEntry pageDirectoryEntry = null;

                if (pageTableOffset != null) {
                    IPageEntry pageEntry = pageTable.getEntry(pageTableOffset);

                    if (pageEntry instanceof PageDirectoryEntry) {
                        pageDirectoryEntry = (PageDirectoryEntry) pageEntry;
                        pageTable = pageDirectoryEntry.getPointer();
                    }
                }

                // If page table does not exist, create one
                if (pageTable == null && pageDirectoryEntry != null) {
                    // If the page table is the last level, it should use page table entries and not page directory entries
                    PageTable pageTableToAdd = new PageTable(i, i == levels - 1 ? PageTableEntry::new : PageDirectoryEntry::new);
                    pageTable = pageTableToAdd;
                    pageDirectoryEntry.setPointer(pageTableToAdd);
                }
            }
        }

    }

    public void unmapPageTables(List<Page> list) {
        for (Page page : list) {
            IPageTable[] tables = new IPageTable[levels];
            PageDirectoryEntry[] entries = new PageDirectoryEntry[levels];
            tables[0] = baseTable;


            for (int i = 0; i < levels; i++) {
                Long pageTableOffset = AddressTranslator.fromAddressToPageTableLevelEntryId(page.getAddress(), i);

                if (pageTableOffset != null) {
                    IPageEntry pageEntry = tables[i].getEntry(pageTableOffset);

                    if (pageEntry instanceof PageDirectoryEntry) {
                        // +1 should not be a problem since the pageEntry of the last table is not and instance of
                        // PageDirectoryEntry but of PageTableEntry
                        tables[i + 1] = ((PageDirectoryEntry) pageEntry).getPointer();
                        entries[i] = (PageDirectoryEntry) pageEntry;
                    }
                }
            }

            for (int i = levels - 1; i >= 0; i--) {
                IPageTable pageTable = tables[i];
                PageDirectoryEntry pageEntry = entries[i];

                if (pageTable != null && i == levels - 1) {
                    pageTable.removeEntries();
                    pageEntry.clearPointer();
                } else if (pageTable != null) {
                    if (pageTable.isEmpty())
                        pageEntry.clearPointer();
                }
            }
        }
    }

    public IAddress getPhysicalAddress(Page page, IAddress virtualAddress) {
        // Calculate the physical address
        return null;
    }


}
