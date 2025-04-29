package be.virtualmem.logic.process.memory;

import be.virtualmem.global.Constants;
import be.virtualmem.global.address.Address;
import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;
import be.virtualmem.logic.process.memory.table.PageTableStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProcessMemory {
    private PageTableStructure pageTableStructure;
    private Map<IAddress, Page> pages;

    public ProcessMemory() {
        pageTableStructure = new PageTableStructure(Constants.PAGE_TABLE_ENTRIES.length);
        pages = new HashMap<>();
        // Create memory
    }

    public void read(IAddress address) {
        // If memory not mapped yet, end process (Segmentation fault)
        // If memory is mapped, but not in physical memory, reallocate
        PageTableEntry pageTableEntry = pageTableStructure.getPageTableEntry(address);
        if (pageTableEntry != null) {
            try {
                // First check if in present, else swap and then read
                pageTableEntry.read();
            } catch (Exception e) { // Make custom exception
                e.printStackTrace();
            }
        }
    }

    public void write(IAddress address) {
        // If memory not mapped yet, end process (Segmentation fault)
        // If memory is mapped, but not in physical memory, reallocate
        PageTableEntry pageTableEntry = pageTableStructure.getPageTableEntry(address);
        if (pageTableEntry != null) {
            try {
                pageTableEntry.write();
            } catch (Exception e) { // Make custom exception
                e.printStackTrace();
            }
        }
    }

    public void map(IAddress address, int size) {
        List<Page> pagesToAdd = new ArrayList<>();
        int requiredPages = size / Constants.PAGE_SIZE;

        for (int i = 0; i < requiredPages; i++) {
            IAddress addressToAdd = Address.offsetAddress(address, i * Constants.PAGE_SIZE);
            pagesToAdd.add(new Page(addressToAdd, Constants.PAGE_SIZE));
        }

        pageTableStructure.mapPageTables(pagesToAdd);

        // Add newly mapped pages to the map
        pages.putAll(pagesToAdd.stream().collect(Collectors.toMap(Page::getAddress, Function.identity())));
    }

    public void unmap(IAddress address, int size) {
        List<Page> pagesToRemove = new ArrayList<>();
        int requiredPages = size / Constants.PAGE_SIZE;

        for (int i = 0; i < requiredPages; i++) {
            IAddress addressToRemove = Address.offsetAddress(address, i * Constants.PAGE_SIZE);
            if (pages.get(addressToRemove) != null)
                pagesToRemove.add(pages.get(addressToRemove));
            // Remove mapped pages to the map
            pages.remove(addressToRemove);
        }

        pageTableStructure.unmapPageTables(pagesToRemove);
    }

    public void free() {
        // Free memory
    }
}
