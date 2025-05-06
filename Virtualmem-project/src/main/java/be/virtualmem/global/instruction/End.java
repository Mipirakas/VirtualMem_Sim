package be.virtualmem.global.instruction;

import be.virtualmem.logic.process.IProcessManager;

public class End implements IInstruction {
    private int pid;

    public End(int pid){
        this.pid = pid;
    }

    @Override
    public void execute(IProcessManager processManager) {
        processManager.endProcess(pid);
    }

    @Override
    public String print() {
        return "[END] Process " + pid;
    }
}
