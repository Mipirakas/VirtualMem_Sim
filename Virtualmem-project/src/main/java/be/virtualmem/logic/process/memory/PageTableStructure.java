package be.virtualmem.logic.process.memory;

import be.virtualmem.global.address.AddressTranslator;
import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.process.memory.entry.IPageEntry;
import be.virtualmem.logic.process.memory.entry.PageDirectoryEntry;
import be.virtualmem.logic.process.memory.entry.PageTable;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageTableStructure {
    private Map<Integer, ArrayList<IPageTable>> pageTableStructure; // <PageTableLevel, ListWithPageTables>

    public PageTableStructure(int levels) {
        pageTableStructure = new HashMap<>();
        for (int i = 0; i < levels; i++) {
            pageTableStructure.put(i, new ArrayList<>());
        }
        pageTableStructure.get(0).add(new PageTable(0, PageDirectoryEntry::new));
    }

    public PageTableEntry getPageTableEntry(IAddress address) {
        // Recursively look in Page Table Directories until Page Table Entry is found
        IPageTable pageTable = pageTableStructure.get(0).get(0);
        PageTableEntry pageTableEntry = null;

        for (Map.Entry<Integer, ArrayList<IPageTable>> entry : pageTableStructure.entrySet()) {
            Integer pageTableOffset = AddressTranslator.fromAddressToPageTableLevelEntryId(address, entry.getKey());

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

    public void addPageTableEntry(PageTableEntry entry) {
        // Also create necessary Page Table Directories
    }

    public IAddress getPhysicalAddress(Page page, IAddress virtualAddress) {
        // Calculate the physical address
        return null;
    }


}
