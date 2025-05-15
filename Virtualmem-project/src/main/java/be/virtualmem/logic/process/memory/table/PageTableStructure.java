package be.virtualmem.logic.process.memory.table;

import be.virtualmem.global.address.Address;
import be.virtualmem.global.address.AddressTranslator;
import be.virtualmem.logic.process.memory.Page;
import be.virtualmem.logic.process.memory.PhysicalMemory;
import be.virtualmem.logic.process.memory.entry.IPageEntry;
import be.virtualmem.logic.process.memory.entry.PageDirectoryEntry;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;

import java.util.*;

public class PageTableStructure {
    private PageTable baseTable;
    private int levels;
    // for debugggggggg

    public PageTableStructure(int levels) {
        // Create initial page table for a process
        baseTable = new PageTable(0, PageDirectoryEntry::new);
        this.levels = levels;
    }

    public PageTableEntry getPageTableEntry(Address address) {
        // Recursively look in Page Table Directories until Page Table Entry is found
        IPageTable pageTable = baseTable;
        PageTableEntry pageTableEntry = null;

        for (int i = 0; i < levels; i++) {
            Long pageTableOffset = AddressTranslator.fromAddressToPageTableLevelEntryId(address, i);

            if (pageTableOffset != null && pageTable != null) {
                IPageEntry pageEntry = pageTable.getEntry(pageTableOffset);

                if (pageEntry instanceof PageDirectoryEntry pde)
                    pageTable = pde.getPointer();
                else if (pageEntry instanceof PageTableEntry pte)
                    pageTableEntry = pte;
            }
        }

        return pageTableEntry;
    }

    public void mapPageTables(List<Page> list) {
        // Also create necessary Page Table Directories
        for (Page page : list) {
            IPageTable pageTable = baseTable;

            for (int i = 0; i < levels; i++) {
                Long pageTableIndex = AddressTranslator.fromAddressToPageTableLevelEntryId(page.getAddress(), i);

                if (pageTableIndex != null) {
                    pageTable.addEntry(pageTableIndex);

                    if (i < levels - 1) {
                        PageDirectoryEntry pageDirectoryEntry = (PageDirectoryEntry) pageTable.getEntry(pageTableIndex);
                        pageTable = pageDirectoryEntry.getPointer();

                        if (pageTable == null) {
                            PageTable pageTableToAdd = new PageTable(i + 1,
                                    i == levels - 2 ? PageTableEntry::new : PageDirectoryEntry::new);
                            pageDirectoryEntry.setPointer(pageTableToAdd);
                            pageTable = pageTableToAdd;
                        }
                    }
                }
            }
        }
    }

    public void unmapPageTables(List<Page> list) {
        PageTable pageTable;

        // First, remove all the PTEs on the lowest levels (the last tables)
        // Next, go from the second-lowest level to the highest level and check on each level
        // if the page table below it is empty and remove it accordingly
        for (int depth = levels; depth > 0; depth--) {
            for (Page page : list) {
                pageTable = baseTable;

                for (int i = 0; i < depth; i++) {
                    Long pageTableOffset = AddressTranslator.fromAddressToPageTableLevelEntryId(page.getAddress(), i);

                    if (i < depth - 1) {
                        pageTable = (PageTable) ((PageDirectoryEntry) pageTable.getEntry(pageTableOffset)).getPointer();
                    } else {
                        if (i == levels - 1) {
                            PageTableEntry pte = (PageTableEntry) pageTable.getEntry(pageTableOffset);

                            // Als page in frame zit, haal page uit frame
                            if (pte.getPfn() != null) {
                                page.pageOut();
                                PhysicalMemory.getInstance().removeFrame(pte.getPfn());
                            }

                            pageTable.removeEntry(pageTableOffset);
                        } else {
                            PageDirectoryEntry pde = (PageDirectoryEntry) pageTable.getEntry(pageTableOffset);

                            // pde zou nooit null mogen zijn maar is dat toch??
                            if (pde != null) {
                                PageTable pt = (PageTable) pde.getPointer();

                                if (pt != null && pt.isEmpty()) {
                                    pageTable.removeEntry(pageTableOffset);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Address getPhysicalAddress(Page page, Address virtualAddress) {
        // Calculate the physical address
        return null;
    }

    public PageTable getBaseTable() {
        return baseTable;
    }
}
