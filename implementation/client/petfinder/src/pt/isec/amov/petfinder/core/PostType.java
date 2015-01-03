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
}
