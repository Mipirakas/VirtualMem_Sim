package be.virtualmem.global.instruction;

import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.IProcessList;

public class Write implements IInstruction {
    private int pid;
    private IAddress address;

    public Write(int pid, IAddress address){
        this.pid = pid;
        this.address = address;
    }

    @Override
    public void execute(IProcessList list) {

    }
}
