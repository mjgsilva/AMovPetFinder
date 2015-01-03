package pt.isec.amov.petfinder.core;

/**
 *
 */
public enum AnimalSpecie {
    DOG("dog"),
    CAT("cat"),
    OTHER("other");

    private final String value;

    private AnimalSpecie(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AnimalSpecie fromValue(final String value) {
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
