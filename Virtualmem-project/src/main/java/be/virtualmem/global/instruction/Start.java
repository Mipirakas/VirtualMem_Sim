package be.virtualmem.global.instruction;

import be.virtualmem.global.process.Process;
import be.virtualmem.logic.process.IProcessManager;

public class Start implements IInstruction {
    private int pid;

    public Start(int pid) {
        this.pid = pid;
    }

    @Override
    public void execute(IProcessManager processManager) {
        processManager.startProcess(this.pid);
    }
}
