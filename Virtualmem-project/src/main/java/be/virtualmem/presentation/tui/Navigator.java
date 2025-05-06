package be.virtualmem.presentation.tui;

import java.util.Scanner;

public class Navigator {
    private be.virtualmem.logic.System system;
    private Scanner scanner;
    private String dialog;

    public Navigator(be.virtualmem.logic.System system) {
        this.system = system;
        this.scanner = new Scanner(System.in);
        dialog = """
                Please enter the digit as selection, from the list below:
                1) Run next instruction
                2) Fast forward next x instructions
                3) Show physical memory
                4) Show backing store
                5) Show page table entry content
                0) Exit
                """;
    }

    public void run() {
        while (!system.hasFinished()) {
            System.out.print(dialog);

            try {
                int selection = promptIntegerSelection();

                // Map met alle runnables?
                switch (selection) {
                    case 1: runNextInstruction(); break;
                    case 2: runNextXInstruction(); break;
                    default: System.out.println("Invalid selection. Try again."); break;
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private int promptIntegerSelection() throws NumberFormatException {
        System.out.print("> ");
        return Integer.parseInt(scanner.nextLine());
    }

    private void runNextInstruction() {
        IPrintTUI printable = system.getInstructionManager().getNextInstruction();
        System.out.println("Next instruction: " + printable.print());
        system.run();
    }

    private void runNextXInstruction() {
        try {
            System.out.println("Please provide how many instructions to execute next:");
            int size = promptIntegerSelection();
            final int startSize = size;

            while (size > 0 && !system.getInstructionManager().hasFinished()) {
                system.run();
                size--;
            }

            System.out.println("Ran " + (startSize - size) + " instructions.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void showPhysicalMemory() {
        //String printable = PrettyPrinter.tablePrinter();
    }
}
