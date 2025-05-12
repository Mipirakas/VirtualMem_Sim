package be.virtualmem.presentation.gui.controller;

import be.virtualmem.logic.System;
import be.virtualmem.logic.process.memory.Page;
import be.virtualmem.logic.process.memory.PhysicalMemory;
import be.virtualmem.presentation.gui.view.tabs.PhysicalFramesTab;

import javax.swing.table.DefaultTableModel;

public class PhysicalFramesTabController {
    private System system;
    private PhysicalFramesTab tab;
    private DefaultTableModel model;
    private final String[] columns = {"Frame", "PID", "Virtual address", "Accessed?", "Dirty?"};

    public PhysicalFramesTabController(PhysicalFramesTab physicalFramesTab, System system) {
        this.system = system;
        this.tab = physicalFramesTab;
        model = (DefaultTableModel) tab.table.getModel();
        model.setDataVector(PhysicalMemory.getInstance().getFrames().keySet().stream()
                .map(i -> new Object[]{i, null, null}).toArray(Object[][]::new), columns);
    }

    public void update() {
        Object[][] data = PhysicalMemory.getInstance().getFrames().entrySet().stream()
                .map(e -> {
                    Page page = e.getValue().getPage();

                    return new Object[]{
                        e.getKey(),
                        e.getValue().getPid() == 0 ? null : e.getValue().getPid(),
                        page == null ? null : page.getAddress().getAsHex(),
                        page == null ? null : page.getAccessed(),
                        page == null ? null : page.getDirty()
                    };
                }).toArray(Object[][]::new);
        model.setDataVector(data, columns);
    }
}
