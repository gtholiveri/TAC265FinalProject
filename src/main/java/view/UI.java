package view;
import java.time.LocalDate;
/**
 * UI abstraction for input/output.
 *
 * Implementations (e.g., MyIO for console, PopupUI for Swing)
 *  provide a consistent set of methods for reading and printing values.
 *
 * @author Kendra Walther
 * email: kwalther@usc.edu
 * TAC 265, Fall 2025
 * Date created: 11/14/25
 */

public interface UI {
    // Display methods
    void print(Object output);
    void println(Object output);
    void println(String output);

    // Input primitives
    int readInt(String prompt);
    int readInt(String prompt, int minValue, int maxValue);
    int readInt(String prompt, int minValue, int maxValue, int quit);
    boolean readBoolean(String prompt);
    double readDouble(String prompt);
    long readLong(String prompt);
    double readDouble(String prompt, double min, double max);
    boolean readYesOrNo(String prompt);

    // Input Strings / reference types
    String readln(String prompt);
    String readln(String prompt, String... matches);
    Object chooseFrom(String prompt, Object... options);
    String readLnNotEmpty(String prompt);

    // Date input
    LocalDate readDate(String prompt);
}
