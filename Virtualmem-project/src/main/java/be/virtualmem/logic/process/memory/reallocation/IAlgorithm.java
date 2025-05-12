package be.virtualmem.logic.process.memory.reallocation;

public interface IAlgorithm {
    Integer frameIdToReallocate();
    int getStartIndex();
}
