package view.old;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;

/**
 * Static utility class for printing output with UTF-8 encoding.
 */
public class Printer {
    private static final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private static final String ANSI_CLEAR_SCREEN = "\033[H\033[2J";
    private static final String ANSI_RESET = "\033[0m";

    // Private constructor to prevent instantiation
    private Printer() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Convenience println with no arguments.
     */
    public static void println() {
        out.println();
    }

    /**
     * Convenience println for any object.
     */
    public static void println(Object o) {
        out.println(o);
    }

    /**
     * Convenience println for String.
     */
    public static void println(String o) {
        out.println(o);
    }

    /**
     * Convenience print for any object.
     */
    public static void print(Object o) {
        out.print(o);
    }

    /**
     * Clears the screen and homes the cursor before printing.
     */
    public static void clearPrint(Object o) {
        out.print(ANSI_CLEAR_SCREEN);
        out.flush();
        out.print(o);
    }

    public static void clearPrintln(Object o) {
        clearPrint(o);
        println();
    }

    /**
     * Prints text with the specified formatting options.
     * @param text the text to print
     * @param formats the formatting options to apply (can be any combination)
     */
    public static void printFormatted(String text, EnumSet<TextFormat> formats) {
        StringBuilder sb = new StringBuilder();

        // Apply all format codes
        for (TextFormat format : formats) {
            sb.append(format.getCode());
        }

        sb.append(text);
        sb.append(ANSI_RESET);

        out.print(sb.toString());
    }

    /**
     * Prints text with the specified formatting options, followed by a newline.
     * @param text the text to print
     * @param formats the formatting options to apply (can be any combination)
     */
    public static void printlnFormatted(String text, EnumSet<TextFormat> formats) {
        printFormatted(text, formats);
        println();
    }
}
