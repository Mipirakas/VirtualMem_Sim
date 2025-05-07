package be.virtualmem.global.instruction;

import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.process.IProcessManager;

public class Unmap implements IInstruction {
    private int pid;
    private IAddress address;
    private int size;

    public Unmap(int pid, IAddress address, int size){
        this.pid = pid;
        this.address = address;
        this.size = size;
    }

    @Override
    public void execute(IProcessManager processManager) {
        processManager.getProcess(pid).getProcessMemory().unmap(address, size);
    }

    @Override
    public String print() {
        return "[UNMAP] Process " + pid + " \t| Base address " + address.getAsHex() + " \t| Size " + size;
    }
}
