package be.virtualmem.presentation.gui.view.tabs;

import javax.swing.*;
import java.awt.*;

public class InstructionsTab extends JPanel {
    public JLabel prevInstr;
    public JLabel nextInstr;
    public JLabel virtAddrLabel;
    public JLabel physAddrLabel;
    public JLabel virtAddr;
    public JLabel physAddr;
    public JLabel pageNotMapped;

    public InstructionsTab() {
        super(new GridBagLayout());
        prevInstr = new JLabel();
        nextInstr = new JLabel();
        virtAddrLabel = new JLabel("Virtual address: ");
        physAddrLabel = new JLabel("Physical address: ");
        virtAddr = new JLabel();
        physAddr = new JLabel();
        pageNotMapped = new JLabel("Page Not Mapped!!!");
        pageNotMapped.setForeground(Color.RED);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Next instruction:"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Previous instruction:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(nextInstr, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(prevInstr, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(virtAddrLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(physAddrLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(virtAddr, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(physAddr, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        this.add(pageNotMapped, gbc);
    }
}
