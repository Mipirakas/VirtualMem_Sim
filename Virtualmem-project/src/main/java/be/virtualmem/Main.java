package be.virtualmem;

import be.virtualmem.logic.System;
import be.virtualmem.presentation.tui.Navigator;

public class Main {
    public static void main(String[] args) {
        System system = new System();
        Navigator navigator = new Navigator(system);
        navigator.run();
    }
}