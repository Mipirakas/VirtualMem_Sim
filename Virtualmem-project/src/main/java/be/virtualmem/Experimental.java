package be.virtualmem;

import be.virtualmem.global.Constants;
import be.virtualmem.logic.process.memory.reallocation.SecondChanceAlgorithm;
import be.virtualmem.logic.process.memory.reallocation.WeightedAgeFrequencyAlgorithm;
import be.virtualmem.logic.statistics.Statistics;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Experimental {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String dialog = """
                Run exprimentation:
                1) Belady Anomaly
                2) Page out to WAF weight
                >\s""";
        System.out.print(dialog);

        int selection = scanner.nextInt();
        switch (selection) {
            case 1: beladyAnomaly(); break;
            case 2: pageOutToWeightComparison(); break;
        }
    }

    public static void beladyAnomaly() {
        System.out.println("Running Belady Anomaly experiment...");
        int[] framesToSet = new int[64];
        for (int i = 0; i < framesToSet.length; i++)
            framesToSet[i] = i + 1;

        Map<Integer, Integer> results = new HashMap();

        for (int frame : framesToSet) {
            Constants.FRAMES_IN_RAM = frame;
            be.virtualmem.logic.System system = new be.virtualmem.logic.System(Constants.MEDIUM_INSTRUCTION_DATASET, new WeightedAgeFrequencyAlgorithm());
            system.runAllInstructions();

            results.put(frame, Statistics.getInstance().getPageInCount());
            System.out.println("(" + Constants.FRAMES_IN_RAM + "," + Statistics.getInstance().getPageInCount() + ")");
        }
    }

    public static void pageOutToWeightComparison() {
        System.out.println("Running Page out to WAF weight experiment...");
        int[] weights = new int[20];
        int multiplier = 5;

        for (int i = 0; i < weights.length; i++)
            weights[i] = i * multiplier;

        Map<Integer, Integer> resultsPageOuts = new LinkedHashMap<>();
        Map<Integer, Integer> resultsPageIns = new LinkedHashMap();
        Map<Integer, Integer> resultsPageEvictions = new LinkedHashMap();

        for (int weight : weights) {
            Constants.WAF_WEIGHT = weight;

            be.virtualmem.logic.System system = new be.virtualmem.logic.System(Constants.MEDIUM_INSTRUCTION_DATASET, new WeightedAgeFrequencyAlgorithm());
            system.runAllInstructions();

            resultsPageOuts.put(weight, Statistics.getInstance().getPageOutCount());
            resultsPageIns.put(weight, Statistics.getInstance().getPageInCount());
            resultsPageEvictions.put(weight, Statistics.getInstance().getPageEvictionCount());
        }

        System.out.println("PAGE OUTS:");
        for (Map.Entry<Integer, Integer> entry : resultsPageOuts.entrySet())
            System.out.println("(" + entry.getKey() + "," + entry.getValue() + ")");

        System.out.println("PAGE INS:");
        for (Map.Entry<Integer, Integer> entry : resultsPageIns.entrySet())
            System.out.println("(" + entry.getKey() + "," + entry.getValue() + ")");

        System.out.println("PAGE EVICITIONS:");
        for (Map.Entry<Integer, Integer> entry : resultsPageEvictions.entrySet())
            System.out.println("(" + entry.getKey() + "," + entry.getValue() + ")");
    }
}
