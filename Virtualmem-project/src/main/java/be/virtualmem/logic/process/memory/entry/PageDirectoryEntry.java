package be.virtualmem.logic.process.memory.entry;

import be.virtualmem.logic.process.memory.IPageTable;

public class PageDirectoryEntry implements IPageEntry {
    private byte present;
    private IPageTable pointer;

    public PageDirectoryEntry(IPageTable pointer) {
        this.present = pointer == null ? (byte) 0 : (byte) 1;
        this.pointer = pointer;
    }

    public PageDirectoryEntry() {
        this.pointer = null;
        this.present = 0;
    }

    public void setPointer(IPageTable pointer) {
        this.pointer = pointer;
        present = pointer == null ? (byte) 0 : (byte) 1;
    }

    public void clearPointer() {
        this.pointer = null;
        this.present = (byte) 0;
    }

    public IPageTable getPointer() {
        return pointer;
    }

    public boolean isPresent() {
        return present == 1;
    }
}
