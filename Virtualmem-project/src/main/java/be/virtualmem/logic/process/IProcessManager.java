package be.virtualmem.logic.process;

import be.virtualmem.global.process.Process;

public interface IProcessManager {
    Process getProcess(int pid);
    void startProcess(int pid);
    void endProcess(int pid);
}
