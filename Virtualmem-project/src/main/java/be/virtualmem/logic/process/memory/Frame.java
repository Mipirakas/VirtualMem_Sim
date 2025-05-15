package be.virtualmem.logic.process.memory;

import be.virtualmem.global.Constants;
import be.virtualmem.logic.SystemClock;
import be.virtualmem.presentation.tui.IPrintTUI;

public class Frame implements IPrintTUI {
    private Page page = null;
    private int pid;
    private int frequency = 0;
    private int setAtClock = 0;
    private int maxFrequency;

    public Frame() {
        maxFrequency = Constants.WAF_MAX_FREQUENCY;
    }

    public Frame(Page page) {
        this.page = page;
        maxFrequency = Constants.WAF_MAX_FREQUENCY;
    }

    public Page accessPage() {
        incrementFrequency();
        return page;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
        if (page == null)
            pid = 0;

        // reset frequency and age
        frequency = 1;
        setAtClock = SystemClock.getInstance().getTime();
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void incrementFrequency() {
        if (frequency > maxFrequency)
            frequency = 0; // Will result in page being swapped out
        frequency++;
    }

    public int getFrequency() {
        return frequency;
    }

    public int calculateAge() {
        return SystemClock.getInstance().getTime() - setAtClock;
    }

    @Override
    public String print() {
        return page == null ? "NULL" : page.print();
    }
}
