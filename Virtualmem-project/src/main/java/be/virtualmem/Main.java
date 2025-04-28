package be.virtualmem;

import be.virtualmem.logic.System;

public class Main {
    public static void main(String[] args) {
        System system = new System();
        int steps = 10;

        for (int i = 0; i < steps; i++) {
            system.run();
        }
    }
}