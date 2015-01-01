package pt.isec.amov.petfinder;

/**
 * Created by mgois on 01-01-2015.
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
