package be.virtualmem.global.process;

import be.virtualmem.logic.process.memory.ProcessMemory;

public interface IProcess {
    ProcessMemory getProcessMemory();
    void end();
}
