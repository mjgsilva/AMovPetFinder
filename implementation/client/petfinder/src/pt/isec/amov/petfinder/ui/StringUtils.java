package pt.isec.amov.petfinder.ui;

import pt.isec.amov.petfinder.R;
import pt.isec.amov.petfinder.core.AnimalColor;
import pt.isec.amov.petfinder.core.AnimalSize;
import pt.isec.amov.petfinder.core.AnimalSpecie;

/**
 * Created by mgois on 03-01-2015.
 */
public class StringUtils {

    public static int getStringId(final AnimalSpecie specie) {
        switch (specie) {
            case CAT: return R.string.specie_cat;
            case DOG: return R.string.specie_dog;
            case OTHER: return R.string.specie_other;
            default: throw new IllegalArgumentException("Unsupported specie argument: " + specie.getValue());
        }
    }

    public static int getStringId(final AnimalSize size) {
        switch (size) {
            case SMALL: return R.string.size_small;
            case MEDIUM: return R.string.size_medium;
            case LARGE: return R.string.size_large;
            default: throw new IllegalArgumentException("Unsupported size argument: " + size.getValue());
        }
    }

    public static int getStringId(final AnimalColor color) {
        switch (color) {
            case BLACK: return R.string.color_black;
            case WHITE: return R.string.color_white;
            case BROWN: return R.string.color_brown;
            case GREY: return R.string.color_grey;
            case YELLOW: return R.string.color_yellow;
            default: throw new IllegalArgumentException("Unsupported color argument: " + color.getValue());
        }
    }

}
