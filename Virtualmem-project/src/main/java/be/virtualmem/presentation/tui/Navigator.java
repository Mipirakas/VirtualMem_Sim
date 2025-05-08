package be.virtualmem.presentation.tui;

import be.virtualmem.global.instruction.IInstruction;
import be.virtualmem.global.instruction.Read;
import be.virtualmem.global.instruction.Write;
import be.virtualmem.logic.process.memory.PhysicalMemory;

import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                6) Show statistics
                7) Current system clock status
                0) Exit
                """;
        // Statistics contains: The number of page ins, page evictions, and page outs
        // Current system clock
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
                    case 3: showPhysicalMemory(); break;

                    case 0: return;
                    default: System.out.println("Invalid selection. Try again."); break;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private int promptIntegerSelection() throws NumberFormatException {
        System.out.print("> ");
        return Integer.parseInt(scanner.nextLine());
    }

    private void runNextInstruction() {
        IInstruction instruction = system.getInstructionManager().getNextInstruction();

        if (instruction instanceof Read || instruction instanceof Write)
            printInvolvedAddresses(instruction);
        System.out.println("Next instruction: " + instruction.print());
        system.run();
    }

    private void printInvolvedAddresses(IInstruction instruction) {
        // Print virtual and physical address of this instruction in physical memory
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
        Map<Integer, IPrintTUI> typedMap = PhysicalMemory.getInstance().getFrames().entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        String printable = PrettyPrinter.tablePrinter("Physical Memory", 6, "Index",40, "Frame", typedMap);
        System.out.println(printable);
    }
}
