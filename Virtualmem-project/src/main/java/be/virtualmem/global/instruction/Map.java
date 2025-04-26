package be.virtualmem.global.instruction;

import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.IProcessList;

public class Map implements IInstruction {
    private int pid;
    private IAddress address;
    private int size;

    public Map(int pid, IAddress address, int size){
        this.pid = pid;
        this.address = address;
        this.size = size;
    }

    @Override
    public void execute(IProcessList list) {

    }
}
