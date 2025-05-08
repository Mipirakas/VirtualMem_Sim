package be.virtualmem.logic.process.memory;

import be.virtualmem.presentation.tui.IPrintTUI;

public class Frame implements IPrintTUI {
    private Page page = null;

    public Frame(){}

    public Frame(Page page){
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String print() {
        return page == null ? "NULL" : page.print();
    }
}
