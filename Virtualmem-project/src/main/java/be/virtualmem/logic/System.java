package be.virtualmem.logic;

import be.virtualmem.logic.process.ProcessManager;

public class System implements ISystemContext {
    private ProcessManager processManager;
    private InstructionManager instructionManager;
    private int clock;

    // parameter: reallocation algorithm
    public System() {
        boot();
    }

    public void boot(){
        // Add the instructions
        clock = 0;
        processManager = ProcessManager.getInstance();
        instructionManager = new InstructionManager(processManager);
    }

    // run next instruction
    public void run(){
        clock++;
        instructionManager.executeNextInstruction();
    }

    public InstructionManager getInstructionManager() {
        return instructionManager;
    }

    public boolean hasFinished() {
        return instructionManager.hasFinished();
    }

    public int getClock() {
        return clock;
    }
}
