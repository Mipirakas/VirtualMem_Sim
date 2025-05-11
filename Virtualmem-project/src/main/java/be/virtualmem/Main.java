package be.virtualmem;

import be.virtualmem.logic.System;
import be.virtualmem.presentation.gui.controller.MainController;
import be.virtualmem.presentation.tui.Navigator;

public class Main {
    public static void main(String[] args) {
        System system = new System();

        MainController controller = new MainController(system);
        controller.show();

        Navigator navigator = new Navigator(system);
        navigator.run();
    }
}