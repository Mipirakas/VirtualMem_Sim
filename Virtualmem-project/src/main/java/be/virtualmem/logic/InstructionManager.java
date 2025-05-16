package be.virtualmem.logic;

import be.virtualmem.data.InstructionReader;
import be.virtualmem.global.Constants;
import be.virtualmem.global.instruction.IInstruction;
import be.virtualmem.logic.process.ProcessManager;

import java.util.Queue;

public class InstructionManager {
    private Queue<IInstruction> instructionQueue;
    private ProcessManager processManager;
    private IInstruction lastInstruction;

    public InstructionManager(ProcessManager processManager, String dataset) {
        instructionQueue = InstructionReader.readFromFile(dataset);
        this.processManager = processManager;
    }

    public void executeNextInstruction(){
        if (!instructionQueue.isEmpty()) {
            IInstruction instruction = instructionQueue.poll();
            lastInstruction = instruction;
            instruction.execute(processManager);
        }
    }

    public IInstruction getNextInstruction(){
        return instructionQueue.peek();
    }

    public boolean hasFinished() {
        return instructionQueue.isEmpty();
    }

    public IInstruction getLastFinishedInstruction() {
        return lastInstruction;
    }
}
