package test.app.teksysweather.util;

/**
 * Class holding String, and time/date format utilities
 */
public class Utilities {

    /**
     * Tests wheather a string is null or empty
     * @param string The string
     * @return Wheather null or empty
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
