package be.virtualmem.global.instruction;

import be.virtualmem.global.address.Address;
import be.virtualmem.logic.process.IProcessManager;
import be.virtualmem.logic.statistics.Statistics;
import be.virtualmem.logic.statistics.action.IAction;
import be.virtualmem.logic.statistics.action.Property;
import be.virtualmem.logic.statistics.action.ReadAction;

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
            if (e.getMessage().equals("Page not mapped yet!")) {
                processManager.endProcess(pid);
                IAction action = new ReadAction();
                action.addProperty(Property.VIRTUAL_ADDRESS, address.getAsHex());
                action.addProperty(Property.PAGE_NOT_MAPPED_ERROR, e.getMessage());
                Statistics.getInstance().addAction(action);
            }
        }
    }

    @Override
    public String print() {
        return "[READ] Process " + pid + " \t| Address " + address.getAsHex();
    }
}
