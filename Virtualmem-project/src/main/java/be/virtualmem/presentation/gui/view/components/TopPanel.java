package be.virtualmem.presentation.gui.view.components;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    public JLabel clockLabel;
    public JButton nextInstrButton;
    public JTextField xInstrField;
    public JButton runAllButton;

    public TopPanel() {
        super(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        nextInstrButton = new JButton("Run");
        xInstrField = new JTextField(10);
        JLabel nextInstrLabel = new JLabel("Run x next instructions");
        runAllButton = new JButton("Run all");
        clockLabel = new JLabel("Clock: " + 0);

        gbc.gridx = 0; gbc.gridy = 0;
        this.add(nextInstrLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        this.add(xInstrField, gbc);
        gbc.gridx = 2; gbc.gridy = 0;
        this.add(nextInstrButton, gbc);
        gbc.gridx = 3; gbc.gridy = 0;
        this.add(runAllButton, gbc);
        gbc.gridx = 4; gbc.gridy = 0;
        this.add(clockLabel, gbc);
    }
}
