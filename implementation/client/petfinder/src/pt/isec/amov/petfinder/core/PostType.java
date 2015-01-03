package pt.isec.amov.petfinder.core;

/**
 * Created by mgois on 02-01-2015.
 */
public enum PostType {
    LOST("lost"),
    FOUND("found");

    private final String value;

    private PostType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PostType fromValue(final String value) {
        final String clean = value.toLowerCase().trim();

        if (clean.equals(LOST.getValue())) {
            return LOST;
        } else if (clean.equals(FOUND.getValue())) {
            return FOUND;
        } else {
            throw new IllegalArgumentException("The value " + value + " is invalid");
        }
    }
}
