package be.virtualmem.global.process;

import be.virtualmem.logic.process.memory.ProcessMemory;

public class Process implements IProcess {
    private int pid;
    private ProcessMemory processMemory;

    // Construction = start process
    public Process(int pid, ProcessMemory processMemory) {
        this.pid = pid;
        this.processMemory = processMemory;
    }

    @Override
    public ProcessMemory getProcessMemory() {
        return processMemory;
    }

    public void end(){
        processMemory.free(); // Free all memory this process allocated
    }

}
