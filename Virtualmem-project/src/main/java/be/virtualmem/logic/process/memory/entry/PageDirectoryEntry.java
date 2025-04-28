package be.virtualmem.logic.process.memory.entry;

public class PageDirectoryEntry implements IPageEntry {
    private byte present;
    private IPageEntry pointer;

    public PageDirectoryEntry(IPageEntry pointer) {
        this.present = pointer == null ? (byte) 0 : (byte) 1;
        this.pointer = pointer;
    }

    public void setPointer(IPageEntry pointer) {
        this.pointer = pointer;
        present = pointer == null ? (byte) 0 : (byte) 1;
    }

    public void clearPointer() {
        this.pointer = null;
        this.present = (byte) 0;
    }

    public IPageEntry getPointer() {
        return pointer;
    }

    public boolean isPresent() {
        return present == 1;
    }
}
