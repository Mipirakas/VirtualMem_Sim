package be.virtualmem.logic;

import be.virtualmem.global.instruction.IInstruction;
import be.virtualmem.logic.process.ProcessManager;

import java.util.Queue;

public class InstructionManager {
    private Queue<IInstruction> instructionQueue;
    private ProcessManager processManager;

    public InstructionManager(ProcessManager processManager) {
        // Read instructions instructionQueue = ...
        this.processManager = processManager;
    }

    public void executeNextInstruction(){
        if (!instructionQueue.isEmpty())
            instructionQueue.poll().execute(processManager);
    }
}
