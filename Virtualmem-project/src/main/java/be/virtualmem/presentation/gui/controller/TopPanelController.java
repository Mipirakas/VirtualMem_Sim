package be.virtualmem.presentation.gui.controller;

import be.virtualmem.presentation.gui.view.components.TopPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TopPanelController {
    private TopPanel topPanel;
    private be.virtualmem.logic.System system;
    private MainController mainController;

    public TopPanelController(TopPanel topPanel, be.virtualmem.logic.System system, MainController mainController) {
        this.topPanel = topPanel;
        initListeners();
        this.system = system;
        this.mainController = mainController;
    }

    private void initListeners() {
        topPanel.nextInstrButton.addActionListener((ActionEvent e) -> runNextXInstructions());
        topPanel.runAllButton.addActionListener((ActionEvent e) -> runAllInstructions());
    }

    private void runNextXInstructions() {
        String input = topPanel.xInstrField.getText();
        try {
            int x = 1;
            if (!input.trim().isEmpty()) {
                x = Integer.parseInt(input);
            }
            system.runXInstructions(x);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        topPanel.clockLabel.setText("Clock: " + system.getClock());
        mainController.runButtonAction();
    }

    private void runAllInstructions() {
        system.runAllInstructions();
        topPanel.clockLabel.setText("Clock: " + system.getClock());
        mainController.runButtonAction();
    }
}
