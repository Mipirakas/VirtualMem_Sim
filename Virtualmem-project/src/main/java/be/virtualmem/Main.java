package be.virtualmem;

import be.virtualmem.global.Constants;
import be.virtualmem.logic.process.memory.reallocation.WeightedAgeFrequencyAlgorithm;
import be.virtualmem.presentation.gui.controller.MainController;
import be.virtualmem.presentation.tui.Navigator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        be.virtualmem.logic.System system = new be.virtualmem.logic.System(Constants.MEDIUM_INSTRUCTION_DATASET, new WeightedAgeFrequencyAlgorithm());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Run the simulation in the GUI enter 'y', in the TUI enter 'n'");
        String selection = scanner.nextLine();

        // GUI
        if (selection.strip().equalsIgnoreCase("y")) {
            MainController controller = new MainController(system);
            controller.show();
        } else if (selection.strip().equalsIgnoreCase("n")) {
            Navigator navigator = new Navigator(system);
            navigator.run();
        } else {
            System.out.println("Invalid input, exiting...");
        }
    }
}