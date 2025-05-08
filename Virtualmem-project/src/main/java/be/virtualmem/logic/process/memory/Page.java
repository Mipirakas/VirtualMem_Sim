package be.virtualmem.logic.process.memory;

import be.virtualmem.global.address.Address;
import be.virtualmem.global.address.IAddress;
import be.virtualmem.presentation.tui.IPrintTUI;

public class Page implements IPrintTUI {
    private IAddress address; // Start address
    private int size;
    private int accessed = 0;
    private int dirty = 0;

    public Page(IAddress address, int size) {
        this.address = address;
        this.size = size;
    }

    public Page(Page page){
        this.address = new Address(page.address);
        this.size = page.size;
    }

    public IAddress getAddress() {
        return address;
    }

    public int getAccessed() {
        return accessed;
    }

    public void setAccessed(int accessed) {
        this.accessed = accessed;
    }

    public int getDirty() {
        return dirty;
    }

    public void setDirty(int dirty) {
        this.dirty = dirty;
    }

    @Override
    public String print() {
        return "Address: " + address.toString() + " \t| Size: " + size;
    }
}
