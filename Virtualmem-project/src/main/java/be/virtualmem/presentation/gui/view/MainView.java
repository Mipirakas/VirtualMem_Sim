package be.virtualmem.presentation.gui.view;


import be.virtualmem.presentation.gui.view.components.TopPanel;

import javax.swing.*;
import java.awt.*;

public class MainView {
    public JFrame frame;
    public TopPanel topPanel;


    public MainView() {
        frame = new JFrame("Virtual Memory Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setLayout(new BorderLayout());
    }

    public void show() {
        frame.setVisible(true);
    }

    public void add(Component component, String constraints) {
        frame.add(component, constraints);
    }
}
