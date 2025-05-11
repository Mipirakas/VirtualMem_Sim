package be.virtualmem.presentation.gui.view.tabs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PhysicalFramesTab extends JPanel {
    public JTable table;

    public PhysicalFramesTab() {
        super(new BorderLayout());
        table = new JTable(new DefaultTableModel());
        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
