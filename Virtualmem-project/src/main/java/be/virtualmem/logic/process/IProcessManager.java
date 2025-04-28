package be.virtualmem.logic.process;

import be.virtualmem.global.process.IProcess;
import java.util.List;

public interface IProcessManager {
    IProcess getProcess(int pid);
    void startProcess(int pid);
    void endProcess(int pid);
}
