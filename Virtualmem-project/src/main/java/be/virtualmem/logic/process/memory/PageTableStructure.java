package be.virtualmem.logic.process.memory;

import be.virtualmem.global.address.AddressTranslator;
import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageTableStructure {
    private Map<Integer, Map<BigInteger, IPageTable>> pageTableStructure; // <PageTableLevel, ListWithPageTables>

    public PageTableStructure(int levels) {
        pageTableStructure = new HashMap<>();
        for (int i = 0; i < levels; i++) {
            pageTableStructure.put(i, new HashMap<>());
        }
    }

    public PageTableEntry getPageTableEntry(IAddress address) {
        // Recursively look in Page Table Directories until Page Table Entry is found
        for (Map.Entry<Integer, Map<BigInteger, IPageTable>> entry : pageTableStructure.entrySet()) {
           // BigInteger pageTableEntryId = AddressTranslator.fromAddressToPageTableLevelEntryId(address, entry.getKey());
        }

        return null;
    }

    public void addPageTableEntry(PageTableEntry entry) {
        // Also create necessary Page Table Directories
    }

    public IAddress getPhysicalAddress(Page page, IAddress virtualAddress) {
        // Calculate the physical address
        return null;
    }


}
