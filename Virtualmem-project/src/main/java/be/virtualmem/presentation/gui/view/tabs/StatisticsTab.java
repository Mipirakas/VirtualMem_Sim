package be.virtualmem.presentation.gui.view.tabs;

import javax.swing.*;
import java.awt.*;

public class StatisticsTab extends JPanel {
    public JLabel pageInLabel;
    public JLabel pageEvictionLabel;
    public JLabel pageOutLabel;

    public StatisticsTab() {
        super(new GridBagLayout());
        pageInLabel = new JLabel("# page ins: " + 0);
        pageEvictionLabel = new JLabel("# page evictions: " + 0);
        pageOutLabel = new JLabel("# page outs: " + 0);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        this.add(pageInLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        this.add(pageEvictionLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        this.add(pageOutLabel, gbc);
    }
}
