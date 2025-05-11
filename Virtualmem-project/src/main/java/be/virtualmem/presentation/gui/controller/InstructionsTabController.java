package be.virtualmem.presentation.gui.controller;

import be.virtualmem.global.instruction.IInstruction;
import be.virtualmem.global.instruction.Read;
import be.virtualmem.global.instruction.Write;
import be.virtualmem.logic.System;
import be.virtualmem.logic.statistics.Statistics;
import be.virtualmem.logic.statistics.action.*;
import be.virtualmem.presentation.gui.view.tabs.InstructionsTab;

public class InstructionsTabController {
    private be.virtualmem.logic.System system;
    private InstructionsTab tab;
    private IInstruction prevInstruction, nextInstruction;

    public InstructionsTabController(InstructionsTab instructionsTab, System system) {
        this.system = system;
        this.tab = instructionsTab;
        nextInstruction = system.getInstructionManager().getNextInstruction();
        tab.nextInstr.setText(nextInstruction.print());
        setRWExtraVisible(false);
    }

    public void update() {
        prevInstruction = system.getInstructionManager().getLastFinishedInstruction();
        nextInstruction = system.getInstructionManager().getNextInstruction();
        tab.prevInstr.setText(prevInstruction.print());
        tab.nextInstr.setText(nextInstruction != null ? nextInstruction.print() : null);

        if (prevInstruction instanceof Write || prevInstruction instanceof Read) {
            IAction action = Statistics.getInstance().getActions()[0];

            tab.virtAddr.setText(action.getPropertyStringMap().get(Property.VIRTUAL_ADDRESS));
            tab.physAddr.setText(action.getPropertyStringMap().get(Property.PHYSICAL_ADDRESS));
            setRWExtraVisible(true);
        } else {
            setRWExtraVisible(false);
        }
    }

    public void setRWExtraVisible(boolean visible) {
        tab.virtAddrLabel.setVisible(visible);
        tab.virtAddr.setVisible(visible);
        tab.physAddrLabel.setVisible(visible);
        tab.physAddr.setVisible(visible);
    }
}
