package be.virtualmem.logic;

import be.virtualmem.data.InstructionReader;
import be.virtualmem.global.Constants;
import be.virtualmem.global.instruction.IInstruction;
import be.virtualmem.logic.process.ProcessManager;

import java.util.Queue;

public class InstructionManager {
    private Queue<IInstruction> instructionQueue;
    private ProcessManager processManager;

    public InstructionManager(ProcessManager processManager) {
        instructionQueue = InstructionReader.readFromFile(Constants.FEW_INSTRUCTION_DATASET);
        this.processManager = processManager;
    }

    public void executeNextInstruction(){
        if (!instructionQueue.isEmpty())
            instructionQueue.poll().execute(processManager);
    }

    public IInstruction getNextInstruction(){
        return instructionQueue.peek();
    }

    public boolean hasFinished() {
        return instructionQueue.isEmpty();
    }
}
