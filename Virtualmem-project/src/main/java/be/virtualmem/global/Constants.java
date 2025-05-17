package be.virtualmem.global;

public class Constants {
    private Constants() {}
    public static final int[] PAGE_TABLE_ENTRIES = new int[]{9, 9, 9, 9}; // 2^9, 2^9, 2^9, 2^9
    public static final int ADDRESS_OFFSET_BITS = 12;
    public static final int ADDRESS_UNUSED_BITS = 16;
    public static final int BIT_ADDRESSABLE = 64;

    public static final int PAGE_SIZE = 12; // 2^12 bytes
    public static final int FRAME_SIZE = 12;
    public static int FRAMES_IN_RAM = 16;

    public static final String MANY_INSTRUCTION_DATASET = "datasets/many_instructions.txt";
    public static final String MEDIUM_INSTRUCTION_DATASET = "datasets/medium_instructions.txt";
    public static final String FEW_INSTRUCTION_DATASET = "datasets/few_instructions.txt";
    public static final String RESULTS_DIR = "results/";

    public static int WAF_WEIGHT = 35;
    public static final int WAF_MAX_FREQUENCY = 500;

}
