package be.virtualmem.presentation.tui;

import be.virtualmem.global.Constants;
import be.virtualmem.global.address.Address;
import be.virtualmem.global.instruction.IInstruction;
import be.virtualmem.global.instruction.Read;
import be.virtualmem.global.instruction.Write;
import be.virtualmem.global.process.Process;
import be.virtualmem.logic.process.ProcessManager;
import be.virtualmem.logic.process.memory.Frame;
import be.virtualmem.logic.process.memory.PhysicalMemory;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;
import be.virtualmem.logic.statistics.Statistics;
import be.virtualmem.logic.statistics.action.ActionType;
import be.virtualmem.logic.statistics.action.Property;
import be.virtualmem.logic.statistics.action.WriteAction;
import be.virtualmem.logic.storage.BackingStore;

import java.util.*;
import java.util.Map.Entry;
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
                3) Show statistics
                4) Show physical memory
                5) Show backing store
                6) Show complete backing store
                7) Show page table entry content
                8) Current system clock status
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
                    case 3: showStatistics(); break;
                    case 4: showPhysicalMemory(); break;
                    case 5: showBackingStore(false); break;
                    case 6: showBackingStore(true); break;
                    case 7: showPageTableEntryContent(); break;
                    case 8: showClock(); break;
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

    private String promptStringSelection() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    private void runNextInstruction() {
        IInstruction instruction = system.getInstructionManager().getNextInstruction();
        System.out.println("Next instruction: " + instruction.print());
        system.run();

        printInvolvedAddresses(instruction);
    }

    private void showStatistics() {
        Map<String, Integer> map = Statistics.getInstance().map();
        String printable = PrettyPrinter.tablePrinterSingle("Statistics", 20, "Property",10, "Value", map);
        System.out.println(printable);
    }

    private void printInvolvedAddresses(IInstruction instruction) {
        // Print virtual and physical address of this instruction in physical memory
        Map<Property, String> map = null;
        if (instruction instanceof Read)
            map = Statistics.getInstance().getLastActionOfType(ActionType.READ).getPropertyStringMap();
        else if (instruction instanceof Write)
            map = Statistics.getInstance().getLastActionOfType(ActionType.WRITE).getPropertyStringMap();

        if (map != null) {
            String printable = PrettyPrinter.tablePrinterSingle("Instruction", 20, "Property",20, "Value", map);
            System.out.println(printable);        }
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
        Map<Integer, Frame> typedMap = PhysicalMemory.getInstance().getFrames();
        String printable = PrettyPrinter.tablePrinterSingle("Physical Memory", 6, "Index",40, "Frame", typedMap);
        System.out.println(printable);
    }

    private void showBackingStore(boolean complete) {
        int limit = 10;
        Map<Integer, List<IPrintTUI>> flattenedPages = BackingStore.getInstance().getBackingStores().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ArrayList<>(entry.getValue().values())
                ));

        if (complete) {
            System.out.println("! To Exit The Backing Store, type 'exit' and press enter. !\n\n");
            for (Entry<Integer, List<IPrintTUI>> entry : flattenedPages.entrySet()) {
                System.out.println("Backing store for process " + entry.getKey() + ":");
                String printable = PrettyPrinter.tablePrinterList("Backing Store", 6, "PID",40, "Page", null, flattenedPages);
                System.out.println(printable);

                String input = scanner.nextLine();
                if (input.toLowerCase(Locale.ROOT).equals("exit"))
                    break;
            }
        } else {
            String printable = PrettyPrinter.tablePrinterList("Backing Store", 6, "PID",40, "Page", limit, flattenedPages);
            System.out.println(printable);
        }
    }

    private void showPageTableEntryContent() {
        System.out.println("Please provide the process id to show the page table entry content for:");
        int pid = promptIntegerSelection();
        System.out.println("Please provide the virtual address to show the page table entry content for (in hexadecimal format, no prefix):");
        String virtualAddress = promptStringSelection();

        Process process = ProcessManager.getInstance().getProcess(pid);
        if (process != null) {
            Address address = Address.fromHexToAddress(virtualAddress);
            address = address.getSubAddress(Constants.ADDRESS_OFFSET_BITS, Constants.BIT_ADDRESSABLE, false);

            PageTableEntry pte = process.getProcessMemory().getPageTableStructure().getPageTableEntry(address);
            String printable = PrettyPrinter.tablePrinterSingle("PTE at " + address.getAsHex(),
                    20, "Property",5, "Value", pte.getMap());
            System.out.println(printable);
        } else {
            System.out.println("Process not found!");
        }

    }

    private void showClock() {
        System.out.println("Current clock: " + system.getClock());
    }
}
