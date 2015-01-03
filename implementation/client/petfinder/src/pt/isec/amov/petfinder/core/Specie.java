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
}
