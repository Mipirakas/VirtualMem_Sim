package be.virtualmem;

import be.virtualmem.global.Constants;
import be.virtualmem.logic.statistics.Statistics;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Experimental {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String dialog = """
                Run exprimentation:
                1) Belady Anomaly
                >\s""";
        System.out.print(dialog);

        int selection = scanner.nextInt();
        switch (selection) {
            case 1: beladyAnomaly(); break;
        }
    }

    public static void beladyAnomaly() {
        System.out.println("Running Belady Anomaly experiment...");
        int[] framesToSet = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        Map<Integer, Integer> results = new HashMap();

        for (int frame : framesToSet) {
            be.virtualmem.logic.System system = new be.virtualmem.logic.System(Constants.MANY_INSTRUCTION_DATASET);
            system.runAllInstructions();

            results.put(frame, Statistics.getInstance().getPageInCount());
            System.out.println("(" + frame + "," + Statistics.getInstance().getPageInCount() + ")");
        }
    }
}
