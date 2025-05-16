package be.virtualmem.logic.process.memory.table;

import be.virtualmem.global.address.Address;
import be.virtualmem.global.address.AddressTranslator;
import be.virtualmem.logic.process.memory.Page;
import be.virtualmem.logic.process.memory.PhysicalMemory;
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

    public PageTableEntry getPageTableEntry(Address address) {
        PageTable pageTable = getPageTableAtLevel(address, levels - 1);
        Long pageTableIndex = AddressTranslator.fromAddressToPageTableLevelEntryId(address, levels - 1);

        if (pageTable == null) return null;

        return (PageTableEntry) pageTable.getEntry(pageTableIndex);
    }

    public void mapPageTables(List<Page> list) {
        PageTable pageTable;

        // Also create necessary Page Table Directories
        for (Page page : list) {
            pageTable = baseTable;

            for (int i = 0; i < levels; i++) {
                Long pageTableIndex = AddressTranslator.fromAddressToPageTableLevelEntryId(page.getAddress(), i);

                if (pageTableIndex != null) {
                    pageTable.addEntry(pageTableIndex);

                    if (i < levels - 1) {
                        PageDirectoryEntry pageDirectoryEntry = (PageDirectoryEntry) pageTable.getEntry(pageTableIndex);
                        pageTable = pageDirectoryEntry.getPageTable();

                        // If pagetable does not exist yet make the page table
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
        for (Page page : list) {
            pageTable = getPageTableAtLevel(page.getAddress(), levels - 1);

            if (pageTable != null) {
                Long pageTableIndex = AddressTranslator.fromAddressToPageTableLevelEntryId(page.getAddress(), levels - 1);
                PageTableEntry pte = (PageTableEntry) pageTable.getEntry(pageTableIndex);

                // Als page in frame zit, haal page uit frame
                if (pte.getPfn() != null) {
                    page.pageOut();
                    PhysicalMemory.getInstance().removeFrame(pte.getPfn());
                }

                pageTable.removeEntry(pageTableIndex);
            }
        }

        // Next, go from the second-lowest level to the highest level and check on each level
        // if the page table below it is empty and remove it accordingly
        for (int depth = levels - 2; depth > 0; depth--) {
            for (Page page : list) {
                pageTable = getPageTableAtLevel(page.getAddress(), depth);

                if (pageTable != null) {
                    Long pageTableIndex = AddressTranslator.fromAddressToPageTableLevelEntryId(page.getAddress(), depth);
                    PageDirectoryEntry pde = (PageDirectoryEntry) pageTable.getEntry(pageTableIndex);

                    if (pde != null && pde.getPageTable().isEmpty()) {
                        pageTable.removeEntry(pageTableIndex);
                    }
                }
            }
        }
    }

    private PageTable getPageTableAtLevel(Address address, int level) {
        PageTable pageTable = baseTable;
        Long pageTableIndex;

        for (int i = 0; i < level; i++) {
            pageTableIndex = AddressTranslator.fromAddressToPageTableLevelEntryId(address, i);

            if (pageTableIndex != null) {
                PageDirectoryEntry pageDirectoryEntry = ((PageDirectoryEntry) pageTable.getEntry(pageTableIndex));

                if (pageDirectoryEntry == null) return null;

                pageTable = pageDirectoryEntry.getPageTable();

                if (pageTable == null) return null;
            }
        }

        return pageTable;
    }

    public Address getPhysicalAddress(Page page, Address virtualAddress) {
        // Calculate the physical address
        return null;
    }

    public PageTable getBaseTable() {
        return baseTable;
    }
}
