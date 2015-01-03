package pt.isec.amov.petfinder.core;

/**
 * Created by mgois on 03-01-2015.
 */
public enum AnimalColor {
    WHITE("white"),
    BLACK("black"),
    BROWN("brown"),
    GREY("grey"),
    YELLOW("yellow");

    private final String value;

    private AnimalColor(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
