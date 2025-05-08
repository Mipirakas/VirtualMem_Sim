package be.virtualmem.logic.process.memory;

import be.virtualmem.global.Constants;
import be.virtualmem.global.address.Address;
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
    private Map<Address, Page> pages;
    private int pid;

    public ProcessMemory(int pid) {
        pageTableStructure = new PageTableStructure(Constants.PAGE_TABLE_ENTRIES.length);
        pages = new HashMap<>();
        this.pid = pid;
        // Create memory
    }

    public void read(Address address) throws Exception {
        // If memory not mapped yet, end process (Segmentation fault)
        // If memory is mapped, but not in physical memory, reallocate
        PageTableEntry pageTableEntry = pageTableStructure.getPageTableEntry(address);
        Address pageAddress = address.getSubAddress(Constants.ADDRESS_OFFSET_BITS, Constants.BIT_ADDRESSABLE, false);
        Page page = pages.get(pageAddress);

        // If page is null, read from physical memory
        if (pageTableEntry == null)
            throw new NullPointerException("Page not mapped yet!");

        // Map page, not in memory yet
        if (page != null && pageTableEntry.getPfn() == null) {
            Integer frameNumber = PhysicalMemory.getInstance().swapPage(page, pid);
            pageTableEntry.setPfn(frameNumber);
            page.setAccessed(1);
        }

        if (page == null && pageTableEntry.getPfn() != null) {
            page = PhysicalMemory.getInstance().getFrames().get(pageTableEntry.getPfn()).getPage();
            page.setAccessed(1);
        }

        pageTableEntry.read();
    }

    public void write(Address address) throws Exception {
        // If memory not mapped yet, end process (Segmentation fault)
        // If memory is mapped, but not in physical memory, reallocate
        PageTableEntry pageTableEntry = pageTableStructure.getPageTableEntry(address);
        Address pageAddress = address.getSubAddress(Constants.ADDRESS_OFFSET_BITS, Constants.BIT_ADDRESSABLE, false);
        Page page = pages.get(pageAddress);

        // If page is null, read from physical memory
        if (pageTableEntry == null)
            throw new NullPointerException("Page not mapped yet!");

        // Map page, not in memory yet
        if (page != null && pageTableEntry.getPfn() == null) {
            Integer frameNumber = PhysicalMemory.getInstance().swapPage(page, pid);
            pageTableEntry.setPfn(frameNumber);
            page.setAccessed(1);
            page.setDirty(1);
        }

        if (page == null && pageTableEntry.getPfn() != null) {
            page = PhysicalMemory.getInstance().getFrames().get(pageTableEntry.getPfn()).getPage();
            page.setAccessed(1);
            page.setDirty(1);
        }

        pageTableEntry.write();
    }

    public void map(Address address, int size) {
        List<Page> pagesToAdd = new ArrayList<>();
        int requiredPages = size / (int) Math.pow(2, Constants.PAGE_SIZE);

        for (int i = 0; i < requiredPages; i++) {
            Address addressToAdd = Address.offsetAddress(address, (int) (i * Math.pow(2, Constants.PAGE_SIZE)));
            Page pageToAdd = new Page(addressToAdd, Constants.PAGE_SIZE);
            pagesToAdd.add(pageToAdd);
            pages.put(addressToAdd, pageToAdd);
        }

        pageTableStructure.mapPageTables(pagesToAdd);

        // Add newly mapped pages to the map
        // Test: ((PageDirectoryEntry) pageTableStructure.baseTable.getEntry(337)).pointer.getEntry(298)
    }

    public void unmap(Address address, int size) {
        List<Page> pagesToRemove = new ArrayList<>();
        int requiredPages = size / (int) Math.pow(2, Constants.PAGE_SIZE);

        for (int i = 0; i < requiredPages; i++) {
            Address addressToRemove = Address.offsetAddress(address, (int) (i * Math.pow(2, Constants.PAGE_SIZE)));
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

    public void setPage(Page page) {
        pages.put(page.getAddress(), page);
    }
}
