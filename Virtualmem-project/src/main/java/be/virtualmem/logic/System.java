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
        processManager = new ProcessManager();
        instructionManager = new InstructionManager(processManager);
    }

    // run next instruction
    public void run(){
        instructionManager.executeNextInstruction();
    }
}
