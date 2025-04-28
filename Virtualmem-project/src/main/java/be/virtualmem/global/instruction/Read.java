package be.virtualmem.global.instruction;

import be.virtualmem.global.address.IAddress;
import be.virtualmem.logic.process.IProcessManager;

public class Read implements IInstruction {
    private int pid;
    private IAddress address;

    public Read(int pid, IAddress address){
        this.pid = pid;
        this.address = address;
    }

    @Override
    public void execute(IProcessManager processManager) {
        processManager.getProcess(pid).getProcessMemory().read(address);
    }
}
