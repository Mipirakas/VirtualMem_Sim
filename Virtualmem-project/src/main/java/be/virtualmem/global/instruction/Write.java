package be.virtualmem.global.instruction;

import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.process.IProcessManager;

public class Write implements IInstruction {
    private int pid;
    private IAddress address;

    public Write(int pid, IAddress address){
        this.pid = pid;
        this.address = address;
    }

    @Override
    public void execute(IProcessManager processManager) {
        processManager.getProcess(pid).getProcessMemory().write(address);
    }

    @Override
    public String print() {
        return "[WRITE] Process " + pid + " \t| Address " + address.getAsHex();
    }
}
