package be.virtualmem.global.process;

import be.virtualmem.logic.process.memory.ProcessMemory;

public class Process {
    private int pid;
    private ProcessMemory processMemory;

    // Construction = start process
    public Process(int pid, ProcessMemory processMemory) {
        this.pid = pid;
        this.processMemory = processMemory;
    }

    public ProcessMemory getProcessMemory() {
        return processMemory;
    }

    public int getPid() {
        return pid;
    }

    public void end(){
        processMemory.free();
    }

}
