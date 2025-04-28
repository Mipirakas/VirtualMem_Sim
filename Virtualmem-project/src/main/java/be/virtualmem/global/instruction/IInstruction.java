package be.virtualmem.global.instruction;

import be.virtualmem.logic.process.IProcessManager;

public interface IInstruction {
    void execute(IProcessManager processManager);
}
