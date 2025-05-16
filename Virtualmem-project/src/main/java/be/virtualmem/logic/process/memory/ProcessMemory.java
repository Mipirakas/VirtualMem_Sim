package be.virtualmem.logic.process.memory;

import be.virtualmem.global.Constants;
import be.virtualmem.global.address.Address;
import be.virtualmem.logic.exception.PageNotMappedException;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;
import be.virtualmem.logic.process.memory.table.PageTableStructure;
import be.virtualmem.logic.statistics.Statistics;
import be.virtualmem.logic.storage.BackingStore;

import java.util.ArrayList;
import java.util.List;

public class ProcessMemory {
    private PageTableStructure pageTableStructure;
    private int pid;

    public ProcessMemory(int pid) {
        pageTableStructure = new PageTableStructure(Constants.PAGE_TABLE_ENTRIES.length);
        this.pid = pid;
    }

    private Page getPage(Address address) throws PageNotMappedException {
        PageTableEntry pageTableEntry = pageTableStructure.getPageTableEntry(address);
        if (pageTableEntry == null)
            throw new PageNotMappedException();

        Address pageAddress = address.getSubAddress(Constants.ADDRESS_OFFSET_BITS, Constants.BIT_ADDRESSABLE, false);
        Page page;

        try {
            // Try to get page from physical memory
            page = PhysicalMemory.getInstance().accessPage(pid, pageAddress);
        } catch (Exception e) {
            // Page Fault
            Statistics.getInstance().incrementPageFaultCount();

            // If page not in memory get from backing store
            page = BackingStore.getInstance().getPage(pid, pageAddress);

            if (page != null) {
                Integer frameNumber = PhysicalMemory.getInstance().swapPage(page, pid);
                Statistics.getInstance().incrementPageInCount();
                pageTableEntry.setPfn(frameNumber);
            }
        }

        return page;
    }

    public void read(Address address) throws Exception {
        PageTableEntry pageTableEntry = pageTableStructure.getPageTableEntry(address);
        if (pageTableEntry == null)
            throw new PageNotMappedException();

        Page page = getPage(address);

        if (page != null)
            page.setAccessed(1);

        pageTableEntry.read();
    }

    public void write(Address address) throws Exception {
        PageTableEntry pageTableEntry = pageTableStructure.getPageTableEntry(address);
        if (pageTableEntry == null)
            throw new PageNotMappedException();

        Page page = getPage(address);

        if (page != null) {
            page.setAccessed(1);
            page.setDirty(1);
        }

        pageTableEntry.write();
    }

    public void map(Address address, int size) {
        int requiredPages = size / (int) Math.pow(2, Constants.PAGE_SIZE);
        List<Page> pagesToAdd = new ArrayList<>(requiredPages);

        for (int i = 0; i < requiredPages; i++) {
            Address addressToAdd = Address.offsetAddress(address, (int) (i * Math.pow(2, Constants.PAGE_SIZE)));
            Page pageToAdd = new Page(addressToAdd, Constants.PAGE_SIZE);
            pagesToAdd.add(pageToAdd);
            BackingStore.getInstance().addPage(pid, addressToAdd, pageToAdd);
        }

        pageTableStructure.mapPageTables(pagesToAdd);
    }

    public void unmap(Address address, int size) {
        int requiredPages = size / (int) Math.pow(2, Constants.PAGE_SIZE);
        List<Page> pagesToRemove = new ArrayList<>(requiredPages);

        for (int i = 0; i < requiredPages; i++) {
            Address addressToRemove = Address.offsetAddress(address, (int) (i * Math.pow(2, Constants.PAGE_SIZE)));
            Page pageToRemove = BackingStore.getInstance().getPage(pid, addressToRemove);
            if (pageToRemove != null)
                pagesToRemove.add(pageToRemove);
            // Remove mapped pages to the map
            BackingStore.getInstance().removePage(pid, addressToRemove);
        }

        pageTableStructure.unmapPageTables(pagesToRemove);
    }

    public Address getPhysicalAddress(Address address, Integer frameNumber) {
        if (frameNumber == null)
            throw new NullPointerException("Frame number is null!");
        Long decimalValue = (long) (frameNumber * Math.pow(2, Constants.PAGE_SIZE));
        decimalValue += address.getSubAddress(0, Constants.ADDRESS_OFFSET_BITS, true).getAsInteger();
        return Address.fromDecimalToAddress(decimalValue);
    }

    public void free() {
        PhysicalMemory.getInstance().removePID(pid);
        BackingStore.getInstance().removePID(pid);
    }
    public PageTableStructure getPageTableStructure() {
        return pageTableStructure;
    }
}
