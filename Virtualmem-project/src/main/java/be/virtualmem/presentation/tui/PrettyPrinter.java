package be.virtualmem.presentation.tui;


import be.virtualmem.presentation.tui.visuals.TableChars;

import java.util.List;
import java.util.Map;

public class PrettyPrinter {
    private static String stringFormatting(String string, int maxLength) {
        if (maxLength < 3)
            throw new IllegalArgumentException("Length must be at least 3");
        StringBuilder finishedString = new StringBuilder();
        if (string.length() > maxLength)
            finishedString.append(string, 0, maxLength - 3).append("...");
        else {
            finishedString.append(string);
            while (finishedString.length() < maxLength) {
                finishedString.append(" ");
            }
        }
        return finishedString.toString();
    }

    private static void makeHeader(int length, String title, int keyLength, String column1Name, int valueLength,
                                    String column2Name, StringBuilder sb) {
        // Create header
        sb.append(TableChars.TOP_LEFT)
                .append(TableChars.HORIZONTAL.repeat(Math.max(0, length - 2)))
                .append(TableChars.TOP_RIGHT).append("\n");

        int padding = (length - title.length() - 2) / 2;
        int extra = (length - title.length() - 2) % 2; // for when total padding isn't even

        // Title
        sb.append(TableChars.VERTICAL)
                .append(" ".repeat(Math.max(0, padding)))
                .append(title)
                .append(" ".repeat(Math.max(0, padding + extra)))
                .append(TableChars.VERTICAL)
                .append("\n");

        sb.append(TableChars.T_RIGHT)
                .append(TableChars.HORIZONTAL.repeat(Math.max(0, keyLength)))
                .append(TableChars.T_DOWN)
                .append(TableChars.HORIZONTAL.repeat(Math.max(0, valueLength)))
                .append(TableChars.T_LEFT)
                .append("\n");

        // Column names
        sb.append(TableChars.VERTICAL)
                .append(PrettyPrinter.stringFormatting(column1Name, keyLength))
                .append(TableChars.VERTICAL)
                .append(PrettyPrinter.stringFormatting(column2Name, valueLength))
                .append(TableChars.VERTICAL)
                .append("\n");

        sb.append(TableChars.T_RIGHT).append(TableChars.HORIZONTAL.repeat(Math.max(0, keyLength)))
                .append(TableChars.CROSS)
                .append(TableChars.HORIZONTAL.repeat(Math.max(0, valueLength)))
                .append(TableChars.T_LEFT).append("\n");
    }

    private static void makeFooter(int keyLength, int valueLength, StringBuilder sb) {
        // Close the bottom of the column
        sb.append(TableChars.BOTTOM_LEFT).append(TableChars.HORIZONTAL.repeat(Math.max(0, keyLength)))
                .append(TableChars.T_UP)
                .append(TableChars.HORIZONTAL.repeat(Math.max(0, valueLength)))
                .append(TableChars.BOTTOM_RIGHT);
    }

    public static String tablePrinterSingle(String title, int keyLength, String column1Name, int valueLength,
                                      String column2Name, Map<Integer, IPrintTUI> structure) {
        StringBuilder sb = new StringBuilder();
        int length = keyLength + valueLength + 3;

        if (title.length() + 2 > length)
            throw new IllegalArgumentException("Title is too long");

        makeHeader(length, title, keyLength, column1Name, valueLength, column2Name, sb);

        // Rows
        for  (Map.Entry<Integer, IPrintTUI> entry : structure.entrySet()) {
            String val1 = entry.getKey().toString();
            String val2 = entry.getValue().print();

            sb.append(TableChars.VERTICAL)
                    .append(PrettyPrinter.stringFormatting(val1, keyLength))
                    .append(TableChars.VERTICAL)
                    .append(PrettyPrinter.stringFormatting(val2, valueLength))
                    .append(TableChars.VERTICAL).append("\n");
        }

        makeFooter(keyLength, valueLength, sb);

        return sb.toString();
    }

    public static String tablePrinterList(String title, int keyLength, String column1Name, int valueLength,
                                      String column2Name, Integer limit, Map<Integer, List<IPrintTUI>> structure) {
        StringBuilder sb = new StringBuilder();
        int length = keyLength + valueLength + 3;

        if (title.length() + 2 > length)
            throw new IllegalArgumentException("Title is too long");

        makeHeader(length, title, keyLength, column1Name, valueLength, column2Name, sb);

        // Rows
        for  (Map.Entry<Integer, List<IPrintTUI>> entry : structure.entrySet()) {
            String val1 = PrettyPrinter.stringFormatting(entry.getKey().toString(), keyLength);
            int count = 0;

            for (IPrintTUI printTUI : entry.getValue()) {
                if (limit != null && count > limit)
                    continue;
                String val2 = PrettyPrinter.stringFormatting(limit != null && count == limit? "..." : printTUI.print(), valueLength);

                if (count != 0)
                    val1 = PrettyPrinter.stringFormatting("", keyLength);

                sb.append(TableChars.VERTICAL)
                        .append(val1)
                        .append(TableChars.VERTICAL)
                        .append(val2)
                        .append(TableChars.VERTICAL).append("\n");

                count++;
            }
        }

        makeFooter(keyLength, valueLength, sb);

        return sb.toString();
    }
}
