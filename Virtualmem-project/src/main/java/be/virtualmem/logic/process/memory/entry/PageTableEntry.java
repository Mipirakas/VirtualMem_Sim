package be.virtualmem.logic.process.memory.entry;

public class PageTableEntry implements IPageEntry {
    private byte accessible; // Is it accessible by the process
    private byte present; // Is it in memory
    private byte accessed; // Did the process recently try to access this entry
    private byte dirty; // Did the process recently try writing to this entry
    private Integer pfn; // Page Frame Number

    public PageTableEntry(Integer pfn) {
        this.pfn = pfn;
        present = pfn == null ? (byte) 0 : (byte) 1;
        this.accessible = 1;
        this.accessed = 1; // When creating an entry the process tries to read it
        this.dirty = 0; // Newly created memory can't be dirty yet
    }

    public PageTableEntry() {
        pfn = null;
        present = 0;
        this.accessible = 1;
        this.accessed = 1; // When creating an entry the process tries to read it
        this.dirty = 0; // Newly created memory can't be dirty yet
    }

    public void setPfn(Integer pfn) {
        this.pfn = pfn;
        present = pfn == null ? (byte) 0 : (byte) 1;
    }

    public void clearPfn() {
        this.pfn = null;
        this.present = 0;
    }

    // Make custom exceptions: segmentation fault exceptions
    public void read() throws Exception {
        if (accessible == 0 || present == 0)
            throw new Exception("Segmentation fault: this page is not accessible");
        accessed = 1;
    }

    public void write() throws Exception {
        if (accessible == 0 || present == 0)
            throw new Exception("Segmentation fault: this page is not accessible");
        accessed = 1;
        dirty = 1;
    }

    public void clearAccessed(){
        accessed = 0;
    }

    public void clearDirty() {
        dirty = 0;
    }

    public Integer getPfn() {
        return pfn;
    }
}
