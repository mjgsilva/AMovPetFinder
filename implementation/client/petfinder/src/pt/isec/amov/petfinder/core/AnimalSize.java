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
}
