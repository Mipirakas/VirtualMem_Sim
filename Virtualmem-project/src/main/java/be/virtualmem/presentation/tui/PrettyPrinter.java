package be.virtualmem.presentation.tui;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PrettyPrinter {
    public static String stringFormatting(String string, int maxLength) {
        StringBuilder finishedString = new StringBuilder();
        if (string.length() > maxLength - 3)
            finishedString.append(string, 0, maxLength - 3).append("...");
        else {
            finishedString.append(string);
            while (finishedString.length() < maxLength) {
                finishedString.append(" ");
            }
        }
        return finishedString.toString();
    }

    public static String tablePrinter(String title, int[] colLength, Map<Integer, Object> structure) {
        StringBuilder sb = new StringBuilder();
        // |....|.....| -> length = 3 strepen en lengte text
        int length = Arrays.stream(colLength).sum() + colLength.length + 1;

        if (title.length() + 2 > length)
            throw new IllegalArgumentException("Title is too long");

        // Create header
        sb.append("+");
        for (int i = 0; i < length - 2; i++)
            sb.append("-");
        sb.append("+\n|");
        sb.append(title);

        for (int i = 0; i < (length - title.length()) - 2; i++)
            sb.append(" ");
        sb.append("+\n|");

        for (int i = 0; i < length - 2; i++)
            sb.append("-");
        sb.append("+\n");

        // Rows
        for  (Map.Entry<Integer, Object> entry : structure.entrySet()) {
            String val1 = entry.getKey().toString();
            String val2 = entry.getValue().toString();



        }
    }
}
