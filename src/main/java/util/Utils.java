package util;

import java.time.Duration;

public class Utils {


    /**
     *
     * @param min The minimum value (inclusive)
     * @param max The maximium value (exclusive)
     * @return A random int on [min, max)
     */
    public static int randInt(int min, int max) {
        return (int) (Math.random() * ((max - min))) + min;
    }

    /**
     * @param min The minimum value (inclusive)
     * @param max The maximum value (exclusive)
     * @return A random double on [min, max)
     */
    public static double randDouble(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    public static double getDoubleMinutes(Duration duration) {
        double seconds = (double) duration.toSeconds();
        return seconds / 60.0;
    }

    /**
     * @return true if {@code index} is within the range [0, {@code size})
     */
    public static boolean validIndex(int index, int size) {
        return index >= 0 && index < size;
    }

}
