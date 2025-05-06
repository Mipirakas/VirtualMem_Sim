package be.virtualmem.logic.process.memory;

import be.virtualmem.global.address.Address;
import be.virtualmem.global.address.IAddress;
import be.virtualmem.presentation.tui.IPrintTUI;

public class Page implements IPrintTUI {
    private IAddress address; // Start address
    private int size;

    public Page(IAddress address, int size) {
        this.address = address;
        this.size = size;
    }

    public IAddress getAddress() {
        return address;
    }

    @Override
    public String print() {
        return "Address: " + address.toString() + " /\\ Size: " + size;
    }
}
