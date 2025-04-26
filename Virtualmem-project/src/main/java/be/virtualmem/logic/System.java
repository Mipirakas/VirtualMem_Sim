package be.virtualmem.logic;

import be.virtualmem.global.process.IProcess;

import java.util.List;

public class System implements ISystemContext, IProcessList {

    public void boot(){

    }

    public void run(){

    }

    @Override
    public List<IProcess> getProcessList() {
        return List.of();
    }

    @Override
    public IProcess getProcess(int pid) {
        return null;
    }

    @Override
    public void startProcess(IProcess process) {

    }

    @Override
    public void endProcess(IProcess process) {

    }
}
