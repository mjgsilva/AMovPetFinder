package pt.isec.amov.petfinder;

/**
 * Validation utility classes
 */
public class Validation {

    public static void assertNotNull(final String value, final String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
    }

    public static void assertNotNullOrEmpty(final String value, final String message) {
        if (value == null || value.isEmpty()) {
            throw new NullPointerException(message);
        }
    }

}
