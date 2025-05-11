package be.virtualmem.logic.process;

import be.virtualmem.global.process.Process;
import be.virtualmem.logic.process.memory.ProcessMemory;

import java.util.HashMap;
import java.util.Map;

public class ProcessManager implements IProcessManager {
    private static ProcessManager instance = new ProcessManager();
    private Map<Integer, Process> processes;

    private ProcessManager(){
        processes = new HashMap<>();
    }

    public static ProcessManager getInstance() {
        return instance;
    }

    @Override
    public Process getProcess(int pid) {
        return processes.get(pid);
    }

    @Override
    public void startProcess(int pid) {
        processes.put(pid, new Process(pid, allocateMemory(pid)));
    }

    @Override
    public void endProcess(int pid) {
        processes.get(pid).end(); // Stop the process
        processes.remove(pid); // Remove from processes
    }

    private ProcessMemory allocateMemory(int pid){
        return new ProcessMemory(pid);
    }
}
