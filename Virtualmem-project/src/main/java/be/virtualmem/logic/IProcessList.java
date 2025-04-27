package be.virtualmem.logic;

import be.virtualmem.global.process.IProcess;
import java.util.List;

public interface IProcessList {
    List<IProcess> getProcessList();
    IProcess getProcess(int pid);
    void startProcess(IProcess process);
    void endProcess(IProcess process);
}
