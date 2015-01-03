package pt.isec.amov.petfinder.core;

/**
 * Created by mgois on 03-01-2015.
 */
public class Location {
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
