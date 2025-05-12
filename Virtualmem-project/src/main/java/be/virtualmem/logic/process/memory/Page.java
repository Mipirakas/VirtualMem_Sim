package be.virtualmem.logic.process.memory;

import be.virtualmem.global.address.Address;
import be.virtualmem.presentation.tui.IPrintTUI;

public class Page implements IPrintTUI {
    private Address address; // Start address
    private int size;
    private int accessed = 0;
    private int dirty = 0;

    public Page(Address address, int size) {
        this.address = address;
        this.size = size;
    }

    public Page(Page page){
        this.address = new Address(page.address);
        this.size = page.size;
        this.accessed = page.accessed;
        this.dirty = page.dirty;
    }

    public Address getAddress() {
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

    public void pageOut() {
        this.dirty = 0;
        this.accessed = 0;
    }

    @Override
    public String print() {

        return "Address: " + address.getAsHex() + " ~ Size: " + (int) Math.pow(2, size) + "B";
    }
}
