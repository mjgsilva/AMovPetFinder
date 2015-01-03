package pt.isec.amov.petfinder.core;

/**
 * Created by mgois on 02-01-2015.
 */
public enum Specie {
    DOG("dog"),
    CAT("cat"),
    OTHER("other");

    private final String value;

    private Specie(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Specie fromValue(final String value) {
        final String clean = value.toLowerCase().trim();

        if (clean.equals(DOG.getValue())) {
            return DOG;
        } else if (clean.equals(CAT.getValue())) {
            return CAT;
        } else {
            throw new IllegalArgumentException("The value " + value + " is invalid");
        }
    }
}
