package be.virtualmem.logic.process.memory;

import be.virtualmem.presentation.tui.IPrintTUI;

public class Frame implements IPrintTUI {
    private Page page;

    @Override
    public String print() {
        return page == null ? "NULL" : page.print();
    }
}
