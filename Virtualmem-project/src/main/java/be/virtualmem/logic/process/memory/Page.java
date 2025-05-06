package be.virtualmem.logic.process.memory;

import be.virtualmem.global.address.IAddress;

public class Page {
    private IAddress address; // Start address
    private int size;

    public Page(IAddress address, int size) {
        this.address = address;
        this.size = size;
    }

    public IAddress getAddress() {
        return address;
    }
}
