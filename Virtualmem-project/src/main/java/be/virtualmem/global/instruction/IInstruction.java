package be.virtualmem.global.instruction;

import be.virtualmem.logic.IProcessList;

public interface IInstruction {
    void execute(IProcessList list);
}
