package be.virtualmem.logic.process.memory.table;

import be.virtualmem.global.address.Address;
import be.virtualmem.global.address.AddressTranslator;
import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.process.memory.Page;
import be.virtualmem.logic.process.memory.entry.IPageEntry;
import be.virtualmem.logic.process.memory.entry.PageDirectoryEntry;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;

import java.util.*;

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
            // 1 because base table is counted as the first level
            // Rethink method of page tables
            for (int i = 0; i < levels; i++) {
                Long pageTableOffset = AddressTranslator.fromAddressToPageTableLevelEntryId(page.getAddress(), i);
                PageDirectoryEntry pageDirectoryEntry = null;

                if (pageTableOffset != null) {
                    pageTable.addEntry(pageTableOffset);
                    IPageEntry pageEntry = pageTable.getEntry(pageTableOffset);

                    if (pageEntry instanceof PageDirectoryEntry) {
                        pageDirectoryEntry = (PageDirectoryEntry) pageEntry;
                        pageTable = pageDirectoryEntry.getPointer();
                    }
                }

                // If page table does not exist, create one
                if (pageTable == null && pageDirectoryEntry != null) {
                    // If the page table is the last level, it should use page table entries and not page directory entries
                    PageTable pageTableToAdd = new PageTable(i + 1, i == levels - 2 ? PageTableEntry::new : PageDirectoryEntry::new);
                    pageTable = pageTableToAdd;
                    pageDirectoryEntry.setPointer(pageTableToAdd);
                }
            }
        }
    }

    public void unmapPageTables(List<Page> list) {
        PageTable pageTable;

        // First, remove all the PTEs on the lowest levels (the last tables)
        // Next, go from the second-lowest level to the highest level and check on each level
        // if the page table below it is empty and remove it accordingly
        for (int j = levels; j > 0; j--) {
            for (Page page : list) {
                Address address = (Address) page.getAddress();
                pageTable = baseTable;

                for (int i = 0; i < j; i++) {
                    Long pageTableOffset = AddressTranslator.fromAddressToPageTableLevelEntryId(address, i);

                    if (i < j - 1) {
                        pageTable = (PageTable) ((PageDirectoryEntry) pageTable.getEntry(pageTableOffset)).getPointer();
                    } else {
                        if (i == levels - 1) {
                            // TODO: If pfn is set, Possibly need to add the frame to the free pool
                            pageTable.removeEntry(pageTableOffset);
                        } else {
                            PageDirectoryEntry pde = (PageDirectoryEntry) pageTable.getEntry(pageTableOffset);

                            if (pde != null) {
                                PageTable pt = (PageTable) pde.getPointer();

                                if (pt != null && pt.getNrOfEntries() == 0) {
                                    pageTable.removeEntry(pageTableOffset);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public IAddress getPhysicalAddress(Page page, IAddress virtualAddress) {
        // Calculate the physical address
        return null;
    }

    public PageTable getBaseTable() {
        return baseTable;
    }
}
