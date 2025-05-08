package be.virtualmem.global.instruction;

import be.virtualmem.global.address.Address;
import be.virtualmem.logic.process.IProcessManager;

public class Read implements IInstruction {
    private int pid;
    private Address address;

    public Read(int pid, Address address){
        this.pid = pid;
        this.address = address;
    }

    @Override
    public void execute(IProcessManager processManager) {
        try {
            processManager.getProcess(pid).getProcessMemory().read(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String print() {
        return "[READ] Process " + pid + " \t| Address " + address.getAsHex();
    }
}
