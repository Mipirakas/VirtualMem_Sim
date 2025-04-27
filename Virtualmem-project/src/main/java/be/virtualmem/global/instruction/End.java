package be.virtualmem.global.instruction;

import be.virtualmem.logic.IProcessList;

public class End implements IInstruction {
    private int pid;

    public End(int pid){
        this.pid = pid;
    }

    @Override
    public void execute(IProcessList list) {

    }
}
