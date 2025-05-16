package be.virtualmem.global.instruction;

import be.virtualmem.global.address.Address;
import be.virtualmem.logic.exception.PageNotMappedException;
import be.virtualmem.logic.process.IProcessManager;
import be.virtualmem.logic.process.memory.ProcessMemory;
import be.virtualmem.logic.statistics.Statistics;
import be.virtualmem.logic.statistics.action.IAction;
import be.virtualmem.logic.statistics.action.Property;
import be.virtualmem.logic.statistics.action.WriteAction;

public class Write implements IInstruction {
    private int pid;
    private Address address;

    public Write(int pid, Address address){
        this.pid = pid;
        this.address = address;
    }

    @Override
    public void execute(IProcessManager processManager) {
        // Statistics
        IAction action = new WriteAction();
        action.addProperty(Property.VIRTUAL_ADDRESS, address.getAsHex());

        try {
            ProcessMemory processMemory = processManager.getProcess(pid).getProcessMemory();
            processMemory.write(address);
            action.addProperty(Property.PHYSICAL_ADDRESS,
                    processMemory.getPhysicalAddress(address,
                            processMemory.getPageTableStructure().getPageTableEntry(address).getPfn()).getAsHex());
        } catch (PageNotMappedException e) {
            processManager.endProcess(pid);
            action.addProperty(Property.PAGE_NOT_MAPPED_ERROR, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Statistics.getInstance().addAction(action);
    }

    @Override
    public String print() {
        return "[WRITE] Process " + pid + " \t| Address " + address.getAsHex();
    }
}
