package be.virtualmem.global.instruction;

import be.virtualmem.global.address.Address;
import be.virtualmem.logic.process.IProcessManager;

public class Map implements IInstruction {
    private int pid;
    private Address address;
    private int size;

    public Map(int pid, Address address, int size){
        this.pid = pid;
        this.address = address;
        this.size = size;
    }

    @Override
    public void execute(IProcessManager processManager) {
        processManager.getProcess(pid).getProcessMemory().map(address, size);
    }

    @Override
    public String print() {
        return "[MAP] Process " + pid + " \t| Base address " + address.getAsHex() + " \t| Size " + size;
    }
}
