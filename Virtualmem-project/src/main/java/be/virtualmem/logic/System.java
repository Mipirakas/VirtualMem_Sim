package be.virtualmem.logic;

import be.virtualmem.logic.process.ProcessManager;

public class System implements ISystemContext {
    private ProcessManager processManager;
    private InstructionManager instructionManager;

    // parameter: reallocation algorithm
    public System() {
        boot();
    }

    public void boot(){
        // Add the instructions
        processManager = ProcessManager.getInstance();
        instructionManager = new InstructionManager(processManager);
    }

    // run next instruction
    public void run(){
        SystemClock.getInstance().tick();
        instructionManager.executeNextInstruction();
    }

    public void runXInstructions(int x) {
        while (x > 0 && !getInstructionManager().hasFinished()) {
            run();
            x--;
        }
    }

    public void runAllInstructions() {
        while (!getInstructionManager().hasFinished()) {
            run();
        }
    }

    public InstructionManager getInstructionManager() {
        return instructionManager;
    }

    public boolean hasFinished() {
        return instructionManager.hasFinished();
    }
}
