package util;

import java.util.ArrayList;

/**
 * Utility methods for arrays and arraylists.
 * */
public class ArrUtils {
    /**
     * Same as the other one but ArrayList.
     */
    public static void assertRectangular(ArrayList<ArrayList<String>> twoDList) {
        if (twoDList.isEmpty() || twoDList.size() == 1) {
            return;
        }
        int cols = twoDList.get(0).size();
        for (int i = 1; i < twoDList.size(); i++) {
            if (cols != twoDList.get(i).size()) {
                throw new IllegalArgumentException("Malformed input: Jagged 2D list");
            }
        }
    }

    /**
     * Ensures that the given 2D array is rectangular, meaning all rows
     * have the same number of columns. If the array is jagged, an
     * IllegalArgumentException is thrown.
     *
     * @param arr The 2D array of strings to be validated as rectangular.
     *            If null or empty, the method does nothing.
     * @throws IllegalArgumentException If the 2D array is jagged and rows
     *                                  have inconsistent lengths.
     */
    public static void assertRectangular(String[][] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        int rows = arr.length;
        int cols = arr[0].length;
        for (int i = 0; i < rows; i++) {
            if (arr[i].length != cols) {
                throw new IllegalArgumentException("Malformed input: Jagged 2D array");
            }
        }
    }

    /**
     * Computes the sum of all integers in the given array.
     *
     * @param arr An array of integers whose sum is to be calculated.
     * @return The sum of all integers in the input array.
     */
    public static int sum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

    /**
     * Wraps a single row of strings into a 2D list format. (Used for utility constructors in Table)
     *
     * @param row The list of strings to be wrapped as a single row in a 2D list.
     * @return A 2D list where the input row is the sole element.
     */
    public static ArrayList<ArrayList<String>> boxRow(ArrayList<String> row) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        result.add(row);
        return result;
    }

    /**
     * Utility method to determine whether a given item is a part of an array of the same specificType.
     *
     * @param in      The item to search for
     * @param options The array to search through
     * @return True if {@code in} was found in {@code options}, false otherwise
     */
    public static boolean oneOf(Object in, Object... options) {
        for (Object option : options) {
            if (in.equals(option)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Subroutine that transforms an array of {@code String}s into a natural-language representation
     *
     * @param arr The array of {@code Object}s
     * @return {@code String} with a natural-language list representation of the array. If {@code arr} was empty, returns an empty string.
     */
    public static <T> String arrToSentence(T[] arr) {
        StringBuilder sb = new StringBuilder();

        if (arr.length > 0) {
            sb.append(arr[0]);
        }

        for (int i = 1; i < arr.length - 1; i++) {
            sb.append(", ").append(arr[i]);
        }

        if (arr.length > 2) {
            sb.append(", or ").append(arr[arr.length - 1]);
        }

        return sb.toString();
    }

    /**
     * Removes duplicate elements from the given array of strings.
     *
     * @param arr The array of {@code String}s from which duplicates should be removed.
     * @return A new array of {@code String}s containing only unique elements from the input array.
     */
    public static String[] deduplicate(String[] arr) {
        ArrayList<String> deduped = new ArrayList<>();

        for (String s : arr) {
            if (!deduped.contains(s)) {
                deduped.add(s);
            }
        }

        return deduped.toArray(new String[0]);
    }

    /**
     * @param arr The array to be summing from
     * @param start The index (inclusive) to start summing from
     * @param end The last index (exclusive) to sum
     * @return The sum of all doubles from from index start up to but not including index end
     */
    public static double sum(double[] arr, int start, int end) {
        if (arr == null || arr.length == 0 || start < 0 || end > arr.length || start >= end) {
            return 0.0;
        }
        double sum = 0;
        for (int i = start; i < end; i++) {
            sum += arr[i];
        }
        return sum;
    }


    public static <T> boolean arrContains(T[] arr, T target) {
        for (T entry : arr) {
            if (entry.equals(target)) {
                return true;
            }
        }
        return false;
    }

}
