package utils;

/**
 * Helper methods
 */
public class Utils {

    private Utils() {
    }

    /**
     * Trims string, but first, it checks if string is not null;
     *
     * @param str the str
     * @return the string
     */
    public static String trimString(String str) {
        if (str != null) {
            return str.trim();
        }
        System.err.println("Given string is null, returning null.");
        return str;
    }

}
