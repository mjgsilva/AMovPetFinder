package pt.isec.amov.petfinder.core;

/**
 * Created by mgois on 02-01-2015.
 */
public enum AnimalSize {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large");

    private final String value;

    private AnimalSize(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AnimalSize fromValue(final String value) {
        final String clean = value.toLowerCase().trim();

        if (clean.equals(SMALL.getValue())) {
            return SMALL;
        } else if (clean.equals(MEDIUM.getValue())) {
            return MEDIUM;
        } else if (clean.equals(LARGE.getValue())) {
            return LARGE;
        } else {
            throw new IllegalArgumentException("The value " + value + " is invalid");
        }
    }
}
