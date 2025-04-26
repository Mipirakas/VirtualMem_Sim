package be.virtualmem.global.process;

public class Process implements IProcess {
    private int pid;
    // Page table

    public Process(int pid){
        this.pid = pid;
    }

}
