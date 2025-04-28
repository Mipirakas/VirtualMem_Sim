package be.virtualmem.logic.process;

import be.virtualmem.global.process.IProcess;
import be.virtualmem.global.process.Process;
import be.virtualmem.logic.process.memory.ProcessMemory;

import java.util.HashMap;
import java.util.Map;

public class ProcessManager implements IProcessManager {
    private Map<Integer, IProcess> processes;

    public ProcessManager(){
        processes = new HashMap<>();
    }

    @Override
    public IProcess getProcess(int pid) {
        return null;
    }

    @Override
    public void startProcess(int pid) {
        processes.put(pid, new Process(pid, allocateMemory()));
    }

    @Override
    public void endProcess(int pid) {
        processes.get(pid).end(); // Stop the process
        processes.remove(pid); // Remove from processes
    }

    private ProcessMemory allocateMemory(){
        return new ProcessMemory();
    }
}
