package pt.isec.amov.petfinder.core;

/**
 *
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

    public static AnimalColor fromValue(final String value) {
        final String clean = value.toLowerCase().trim();

        if (clean.equals(WHITE.getValue())) {
            return WHITE;
        } else if (clean.equals(BLACK.getValue())) {
            return BLACK;
        } else if (clean.equals(BROWN.getValue())) {
            return BROWN;
        } else if (clean.equals(GREY.getValue())) {
            return GREY;
        } else if (clean.equals(YELLOW.getValue())) {
            return YELLOW;
        } else {
            throw new IllegalArgumentException("The value " + value + " is invalid");
        }
    }
}
