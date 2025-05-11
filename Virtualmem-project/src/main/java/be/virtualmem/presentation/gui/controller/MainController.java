package be.virtualmem.presentation.gui.controller;

import be.virtualmem.presentation.gui.view.MainView;
import be.virtualmem.presentation.gui.view.components.TopPanel;
import be.virtualmem.presentation.gui.view.tabs.InstructionsTab;
import be.virtualmem.presentation.gui.view.tabs.PhysicalFramesTab;
import be.virtualmem.presentation.gui.view.tabs.StatisticsTab;

import javax.swing.*;
import java.awt.*;

public class MainController {
    private be.virtualmem.logic.System system;
    private MainView view;
    private TopPanelController topPanelController;
    private PhysicalFramesTabController pFTController;
    private StatisticsTabController statsTabController;
    private InstructionsTabController instructionsTabController;

    public MainController(be.virtualmem.logic.System system) {
        this.system = system;
        view = new MainView();

        TopPanel topPanel = new TopPanel();
        topPanelController = new TopPanelController(topPanel, system, this);
        view.add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        view.add(tabbedPane, BorderLayout.CENTER);

        PhysicalFramesTab physicalFramesTab = new PhysicalFramesTab();
        pFTController = new PhysicalFramesTabController(physicalFramesTab, system);
        tabbedPane.add("Physical frames", physicalFramesTab);

        StatisticsTab statisticsTab = new StatisticsTab();
        statsTabController = new StatisticsTabController(statisticsTab, system);
        tabbedPane.add("Statistics", statisticsTab);

        InstructionsTab instructionsTab = new InstructionsTab();
        instructionsTabController = new InstructionsTabController(instructionsTab, system);
        tabbedPane.add("Instructions", instructionsTab);
    }

    public void show() {
        view.show();
    }

    public void runButtonAction() {
        pFTController.update();
        statsTabController.update();
        instructionsTabController.update();
    }
}
