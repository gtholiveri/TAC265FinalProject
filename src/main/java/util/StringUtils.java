package util;

/**
 * Utility class for string operations.
 */
public class StringUtils {
    /**
     * Centers given string within a specified width by adding spaces
     * equally on both sides. If the string's width exceeds the specified width,
     * truncates the string
     */
    public static String centerPad(String s, int width) {
        int padding = width - s.length();
        if (padding <= 0) return s.substring(0, width);
        int left = padding / 2;          // integer division skews left when odd
        int right = padding - left;
        return " ".repeat(left) + s + " ".repeat(right);
    }

    /**
     * @param s The input string to be capitalized.
     * @return A new string with the first character converted to uppercase and the remainder unchanged.
     */
    public static String capitalize(String s) {
        if (s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * ANSI escape for clearing screen and resetting cursor.
     */
    public static void clearScrn() {
        System.out.print("\u001b[2J\u001b[H");
        System.out.flush();
    }

    /**
     *
     * @param str String to search through (haystack)
     * @param substr String to search for (needle)
     * @return True if needle is in the haystack, ignoring case, and false otherwise.
     */
    public static boolean containsIgnoreCase(String str, String substr) {
        return str.toLowerCase().contains(substr.toLowerCase());
    }

    /**
     * Checks if a given target string is present in an array of strings,
     * ignoring case differences.
     *
     * @param arr Strin[] to search through
     * @param target String[] to search for
     * @return True if the target string is found in the array, ignoring case,
     *         false otherwise.
     */
    public static boolean containsIgnoreCase(String[] arr, String target) {
        for (String s : arr) {
            if (s.equalsIgnoreCase(target)) return true;
        }
        return false;
    }

    /**
     * Formats a double value as a blood amount, aka truncates it to 2 decimal places
     *
     * @param d The double value to be formatted.
     * @return String rep of the double truncated to 2 decimal places
     */
    public static String fDouble(double d) {
        return String.format("%.1f", d);
    }


    /**
     * Formats two doubles as a fraction
     * @param val1 The numerator
     * @param val2 The denominator
     * @return The two doubles truncated to 1 decimal place with " / " between them
     */
    public static String fDouble(double val1, double val2) {
        return String.format("%-4.1f / %-4.1f", val1, val2);
    }

    /**
     * Returns the ordinal suffix for a given number (1st, 2nd, 3rd, 4th, etc.)
     * @param num The number to get the ordinal suffix for
     * @return The ordinal suffix ("st", "nd", "rd", or "th")
     */
    public static String getOrdinalSuffix(int num) {
        // Handle special cases: 11th, 12th, 13th
        if (num % 100 >= 11 && num % 100 <= 13) {
            return "th";
        }

        // Handle standard cases based on last digit
        return switch (num % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}
