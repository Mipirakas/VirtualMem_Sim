package be.virtualmem.logic.process.memory.reallocation;

import be.virtualmem.logic.process.memory.Page;

public class PageState {
    private Page originalPage;
    private Page copyPage;

    public PageState(Page page) {
        originalPage = page;
        copyPage = new Page(page);
    }

    public Page requestCopy() {
        return copyPage;
    }

    public Page requestOriginal() {
        return originalPage;
    }
}
