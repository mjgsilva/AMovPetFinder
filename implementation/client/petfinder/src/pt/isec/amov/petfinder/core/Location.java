package pt.isec.amov.petfinder.core;

import java.io.Serializable;

/**
 *
 */
public class Location implements Serializable {
    private final double latitute;
    private final double longitude;

    public Location(double latitute, double longitude) {
        this.latitute = latitute;
        this.longitude = longitude;
    }

    public double getLatitute() {
        return latitute;
    }

    public double getLongitude() {
        return longitude;
    }

}
