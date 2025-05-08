package be.virtualmem.global.instruction;

import be.virtualmem.global.address.Address;
import be.virtualmem.logic.process.IProcessManager;

public class Write implements IInstruction {
    private int pid;
    private Address address;

    public Write(int pid, Address address){
        this.pid = pid;
        this.address = address;
    }

    @Override
    public void execute(IProcessManager processManager) {
        try {
            processManager.getProcess(pid).getProcessMemory().write(address);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String print() {
        return "[WRITE] Process " + pid + " \t| Address " + address.getAsHex();
    }
}
