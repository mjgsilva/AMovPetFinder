package pt.isec.amov.petfinder.json;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mgois on 03-01-2015.
 */
public class JsonTimeUtils {

    public static final String FMT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static DateFormat newDateFormat() {
        return new SimpleDateFormat(FMT);
    }

    private static DateFormat DATE_FORMAT;
    static {
        DATE_FORMAT = newDateFormat();
    }

    /**
     *
     * @param value
     * @return
     * @throws ParseException
     *
     * Date formats are not synchronized, therefore this method is. If trying to avoid the synchronization overhead
     * create a new DateFormat per thread with the newDateFormat() method.
     */
    public synchronized static Date fromString(final String value) throws ParseException {
        // Java doesn't support general timezones (see SimpleDateFormat docs), so we need to
        // replace the trailing Z with the equivalent offset timezone
        final String fixed = value.trim().replaceAll("Z$", "+0000");

        return DATE_FORMAT.parse(fixed);
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
