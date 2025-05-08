package be.virtualmem.logic.process.memory;

import be.virtualmem.presentation.tui.IPrintTUI;

public class Frame implements IPrintTUI {
    private Page page = null;
    private int pid;

    public Frame() {}

    public Frame(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String print() {
        return page == null ? "NULL" : page.print();
    }
}
