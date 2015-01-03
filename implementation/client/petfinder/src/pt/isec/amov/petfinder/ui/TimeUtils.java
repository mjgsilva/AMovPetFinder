package pt.isec.amov.petfinder.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class TimeUtils {

    public static final String FMT = "yyyy-MM-dd HH:mm";

    public static DateFormat newDateFormat() {
        return new SimpleDateFormat(FMT);
    }

    private static DateFormat DATE_FORMAT;
    static {
        DATE_FORMAT = newDateFormat();
    }

    /**
     *
     * @param date
     * @return
     *
     * Date formats are not synchronized, therefore this method is. If trying to avoid the synchronization overhead
     * create a new DateFormat per thread with the newDateFormat() method.
     */
    public synchronized static String toString(final Date date) {
        return DATE_FORMAT.format(date);
    }

}
