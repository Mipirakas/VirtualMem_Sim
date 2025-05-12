package be.virtualmem.logic.process.memory;

import be.virtualmem.global.Constants;
import be.virtualmem.global.address.Address;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;
import be.virtualmem.logic.process.memory.table.PageTableStructure;
import be.virtualmem.logic.statistics.Statistics;
import be.virtualmem.logic.statistics.action.IAction;
import be.virtualmem.logic.statistics.action.Property;
import be.virtualmem.logic.statistics.action.ReadAction;
import be.virtualmem.logic.statistics.action.WriteAction;
import be.virtualmem.logic.storage.BackingStore;

import java.util.ArrayList;
import java.util.List;

public class ProcessMemory {
    private PageTableStructure pageTableStructure;
    private int pid;

    public ProcessMemory(int pid) {
        pageTableStructure = new PageTableStructure(Constants.PAGE_TABLE_ENTRIES.length);
        this.pid = pid;
        // Create memory
    }

    public void read(Address address) throws Exception {
        // If memory not mapped yet, end process (Segmentation fault)
        // If memory is mapped, but not in physical memory, reallocate
        PageTableEntry pageTableEntry = pageTableStructure.getPageTableEntry(address);
        Address pageAddress = address.getSubAddress(Constants.ADDRESS_OFFSET_BITS, Constants.BIT_ADDRESSABLE, false);
        Page page = BackingStore.getInstance().getPage(pid, pageAddress);

        // If page is null, read from physical memory
        if (pageTableEntry == null)
            throw new NullPointerException("Page not mapped yet!");

        // Map page, not in memory yet
        if (page != null && pageTableEntry.getPfn() == null) {
            Integer frameNumber = PhysicalMemory.getInstance().swapPage(page, pid);
            pageTableEntry.setPfn(frameNumber);
            page.setAccessed(1);
        } else if (page != null && pageTableEntry.getPfn() != null) {
            page = PhysicalMemory.getInstance().getFrames().get(pageTableEntry.getPfn()).getPage();
            page.setAccessed(1);
        }

        IAction action = new ReadAction();
        action.addProperty(Property.VIRTUAL_ADDRESS, address.getAsHex());
        action.addProperty(Property.PHYSICAL_ADDRESS, getPhysicalAddress(address, pageTableEntry.getPfn()).getAsHex());
        Statistics.getInstance().addAction(action);

        pageTableEntry.read();
    }

    public void write(Address address) throws Exception {
        // If memory not mapped yet, end process (Segmentation fault)
        // If memory is mapped, but not in physical memory, reallocate
        PageTableEntry pageTableEntry = pageTableStructure.getPageTableEntry(address);
        Address pageAddress = address.getSubAddress(Constants.ADDRESS_OFFSET_BITS, Constants.BIT_ADDRESSABLE, false);
        Page page = BackingStore.getInstance().getPage(pid, pageAddress);

        // If page is null, read from physical memory
        if (pageTableEntry == null)
            throw new NullPointerException("Page not mapped yet!");

        // Map page, not in memory yet
        if (page != null && pageTableEntry.getPfn() == null) {
            Integer frameNumber = PhysicalMemory.getInstance().swapPage(page, pid);
            pageTableEntry.setPfn(frameNumber);
            page.setAccessed(1);
            page.setDirty(1);
        } else if (page != null && pageTableEntry.getPfn() != null) {
            page = PhysicalMemory.getInstance().getFrames().get(pageTableEntry.getPfn()).getPage();
            page.setAccessed(1);
            page.setDirty(1);
        }

        // Statistics
        IAction action = new WriteAction();
        action.addProperty(Property.VIRTUAL_ADDRESS, address.getAsHex());
        action.addProperty(Property.PHYSICAL_ADDRESS, getPhysicalAddress(address, pageTableEntry.getPfn()).getAsHex());
        Statistics.getInstance().addAction(action);

        pageTableEntry.write();
    }

    public void map(Address address, int size) {
        List<Page> pagesToAdd = new ArrayList<>();
        int requiredPages = size / (int) Math.pow(2, Constants.PAGE_SIZE);

        for (int i = 0; i < requiredPages; i++) {
            Address addressToAdd = Address.offsetAddress(address, (int) (i * Math.pow(2, Constants.PAGE_SIZE)));
            Page pageToAdd = new Page(addressToAdd, Constants.PAGE_SIZE);
            pagesToAdd.add(pageToAdd);
            BackingStore.getInstance().addPage(pid, addressToAdd, pageToAdd);
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
            Page pageToRemove = BackingStore.getInstance().getPage(pid, addressToRemove);
            if (pageToRemove != null)
                pagesToRemove.add(pageToRemove);
            // Remove mapped pages to the map
            BackingStore.getInstance().removePage(pid, addressToRemove);
        }

        pageTableStructure.unmapPageTables(pagesToRemove);
    }

    private Address getPhysicalAddress(Address address, Integer frameNumber) {
        Long decimalValue = (long) (frameNumber * Math.pow(2, Constants.PAGE_SIZE));
        decimalValue += address.getSubAddress(0, Constants.ADDRESS_OFFSET_BITS, true).getAsInteger();
        return Address.fromDecimalToAddress(decimalValue);
    }

    public void free() {
        // Free memory
    }
    public PageTableStructure getPageTableStructure() {
        return pageTableStructure;
    }
}
