package be.virtualmem.global.instruction;

import be.virtualmem.logic.process.IProcessManager;
import be.virtualmem.presentation.tui.IPrintTUI;

public interface IInstruction extends IPrintTUI {
    void execute(IProcessManager processManager);
}
