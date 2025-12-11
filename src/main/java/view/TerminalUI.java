package view;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Scanner;

/**
 * MyIO -- a small input/output helper that wraps Scanner
 * and does robust error checking for user input.<br><br>
 * <p>
 * This avoids depending on any preview language features,
 * so it works fine on standard Java versions (17, 21, 25, etc.).
 *
 * @author Kendra Walther
 * email: kwalther@usc.edu
 * TAC 265, Fall 2025
 * Date created: 11/14/25
 */
public class TerminalUI implements UI {

    private Scanner sc;

    public TerminalUI() {
        sc = new Scanner(System.in); // single shared Scanner for System.in
    }

    /**
     * Drains any buffered input from System.in to prevent ghost keypresses
     * from the native key hook from interfering with Scanner input.
     */
    private void drainBuffer() {
        try {
            System.out.println("wait...");
            Thread.sleep(2000);
            System.out.println("go");
            for (int i = 0; i < 5; i++) {
                sc.nextLine();
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while draining buffer.");
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println("Nothing in buffer");
            // If draining fails, just continue - not critical
        }
    }

    /**
     * Read a full line of text from the user.
     *
     * @param prompt The question to ask the user
     * @return whatever the user types before pressing Enter
     */
    public String readStr(String prompt) {
        sc = new Scanner(System.in);
        print(prompt);
        drainBuffer();
        return sc.nextLine();
    }

    public String readLnNotEmpty(String prompt) {
        while (true) {
            String response = readStr(prompt).trim();

            if (!response.isEmpty()) {
                return response;
            } else {
                System.err.println("Response cannot be empty. Try again.");
            }
        }
    }

    @Override
    public void print(Object output) {
        Printer.print(output);
    }

    @Override
    public void println(Object output) {
        Printer.println(output);
    }

    @Override
    public void println(String output) {
        Printer.println(output);
    }

    /**
     * Read an int value from the user, with error checking.
     *
     * @param prompt The question to ask the user
     * @return the int value the user types in
     */
    public int readInt(String prompt) {
        drainBuffer();
        print(prompt);
        while (!sc.hasNextInt()) {
            String garbage = sc.nextLine(); // grab the "bad data"
            System.err.println(garbage + " was not an int.");
            print(prompt);
        }
        int num = sc.nextInt();      // grab the number
        sc.nextLine();               // consume the rest of the line
        return num;
    }

    public long readLong(String prompt) {
        drainBuffer();
        print(prompt);
        while (!sc.hasNextLong()) {
            String garbage = sc.nextLine(); // grab the "bad data"
            System.err.println(garbage + " was not a long.");
            print(prompt);
        }
        long num = sc.nextLong();      // grab the number
        sc.nextLine();               // consume the rest of the line
        return num;
    }

    /**
     * Read an int between [min, max] inclusive.
     *
     * @param prompt The question to ask the user
     * @param min    Minimum allowed value (inclusive)
     * @param max    Maximum allowed value (inclusive)
     * @return an int between [min, max]
     */
    public int readInt(String prompt, int min, int max) {
        int num = readInt(prompt);
        while (num < min || num > max) {
            System.err.println(num + " is invalid. Choose a number from " + min + " to " + max + ".");
            num = readInt(prompt);
        }
        return num;
    }

    /**
     * Method readInt: gets an int value between min and max (inclusive) based on user input.
     *
     * @param prompt    The question to ask the user
     * @param minValue  The min allowed int value for the user input (inclusive)
     * @param maxValue  The max allowed int value for the user input (inclusive)
     * @param quitValue The max allowed int value for the user input (inclusive)
     * @return an int between [min, max]  or equal to the quitValue
     */
    public int readInt(String prompt, int minValue, int maxValue, int quitValue) {
        int num = readInt(prompt); //get a number
        while (!(num >= minValue && num <= maxValue) || num == quitValue) {
            System.err.println(num + " is invalid, Choose a num " + minValue + " to " + maxValue + " or " + quitValue + " for quit");
            num = readInt(prompt); //get a new number
        }
        return num;
    }

    /**
     * Read a double value from the user, with error checking.
     *
     * @param prompt The question to ask the user
     * @return the double value the user types in
     */
    public double readDouble(String prompt) {
        drainBuffer();
        print(prompt);
        while (!sc.hasNextDouble()) {
            String garbage = sc.nextLine(); // grab the "bad data"
            System.err.println(garbage + " was not a double.");
            print(prompt);
        }
        double num = sc.nextDouble();
        sc.nextLine(); // clear the input buffer
        return num;
    }

    /**
     * Read a double between [min, max] inclusive.
     */
    public double readDouble(String prompt, double min, double max) {
        double num = readDouble(prompt);
        while (num < min || num > max) {
            System.err.println(num + " is invalid. Choose a number from " + min + " to " + max + ".");
            num = readDouble(prompt);
        }
        return num;
    }

    /**
     * Read a yes/no answer, looping until a valid response is given.
     *
     * @param prompt The question to ask the user
     * @return true for yes/y, false for no/n
     */
    public boolean readYesOrNo(String prompt) {
        String answer = readStr(prompt + " (yes/no) > ").toLowerCase();

        while (!(answer.equals("yes") || answer.equals("y")
                || answer.equals("no") || answer.equals("n"))) {
            System.err.println(answer + " is invalid.");
            answer = readStr(prompt + " (yes/no) > ").toLowerCase();
        }

        return answer.equals("yes") || answer.equals("y");
    }

    /**
     * Read a boolean ("true" or "false"), looping until valid.
     */
    public boolean readBoolean(String prompt) {
        String userInput = readStr(prompt).toLowerCase();
        while (!(userInput.equals("true") || userInput.equals("false"))) {
            System.err.println(userInput + " is invalid. Must specificType \"true\" or \"false\".");
            userInput = readStr(prompt).toLowerCase();
        }
        return userInput.equals("true");
    }

    /**
     * Prompt the user for a String that must be one of the provided options
     * (case-insensitive).
     *
     * @param prompt  The question to ask the user
     * @param options Zero or more valid options
     * @return a String that matches one of the options (case-insensitive)
     */
    public String readString(String prompt, String... options) {
        String response = readStr(prompt);

        while (contains(response, options) == -1) {
            System.err.println(response + " is not a valid option. Choose from " + Arrays.toString(options));
            response = readStr(prompt);
        }
        return response;
    }

    /**
     * Alias for readString with String options.
     */
    public String readStr(String prompt, String... options) {
        return readString(prompt, options);
    }

    /**
     * Version that takes Object options but still returns the chosen text.
     */
    public String readStr(String prompt, Object... options) {
        String response = readStr(prompt);
        while (contains(response, options) == -1) {
            System.err.println(response + " is not a valid option. Choose from " + Arrays.toString(options));
            response = readStr(prompt);
        }
        return response;
    }

    /**
     * Return index of word in options (String...), or -1 if not present (case-insensitive).
     */
    public int contains(String word, String... options) {
        int index = -1;
        int i = 0;
        while (index == -1 && i < options.length) {
            if (options[i].equalsIgnoreCase(word)) {
                index = i;
            }
            i++;
        }
        return index;
    }

    /**
     * Overload for Object options (uses toString()).
     */
    public int contains(String word, Object... options) {
        int index = -1;
        int i = 0;
        while (index == -1 && i < options.length) {
            if (options[i].toString().equalsIgnoreCase(word)) {
                index = i;
            }
            i++;
        }
        return index;
    }


    /**
     * Allows the user to select from a variety of options.
     *
     * @param prompt
     * @param options
     * @return
     */
    public Object chooseFrom(String prompt, Object... options) {
        String choices = "";
        for (int i = 1; i <= options.length; i++) {
            choices += i + ": " + options[i - 1].toString() + "\n";
        }
        int num = readInt(choices + "\n" + prompt, 1, options.length + 1);
        return options[num - 1];
    }

    /**
     * Read a valid date (year, month, day) from the user.
     * <p>
     * Uses YearMonth to ensure the day is valid for the given month/year.
     */
    public LocalDate readDate(String prompt) {
        println(prompt);
        int year = this.readInt("Enter 4-digit year:", 1900, 2100);
        int month = this.readInt("Enter month:", 1, 12);
        YearMonth yearMonth = YearMonth.of(year, month);
        int day = this.readInt("Enter day of month:", 1, yearMonth.lengthOfMonth());
        return LocalDate.of(year, month, day);
    }
}

