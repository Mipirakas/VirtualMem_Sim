package be.virtualmem.logic;

import be.virtualmem.logic.process.ProcessManager;
import be.virtualmem.logic.process.memory.PhysicalMemory;
import be.virtualmem.logic.process.memory.reallocation.IAlgorithm;
import be.virtualmem.logic.statistics.Statistics;
import be.virtualmem.logic.storage.BackingStore;

public class System implements ISystemContext {
    private ProcessManager processManager;
    private InstructionManager instructionManager;
    private String dataset;

    public System(String dataset, IAlgorithm algorithm) {
        this.dataset = dataset;
        PhysicalMemory.getInstance().setAlgorithm(algorithm);
        resetSingleton();
        boot();
    }

    public void boot(){
        // Add the instructions
        processManager = ProcessManager.getInstance();
        instructionManager = new InstructionManager(processManager, dataset);
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

    private void resetSingleton() {
        Statistics.getInstance().resetStatistics();
        SystemClock.getInstance().resetSystemClock();
        PhysicalMemory.getInstance().resetPhysicalMemory();
        BackingStore.getInstance().resetBackingStore();
    }
}
