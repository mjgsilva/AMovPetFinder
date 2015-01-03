package pt.isec.amov.petfinder.json;

import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.util.Calendar.*;

/**
 * Created by mgois on 03-01-2015.
 */
public class JsonTimeUtilsTest extends TestCase {

    public void testFromString() throws ParseException {
        final String string = "2014-12-29T18:24:03.941Z";

        final Date date = JsonTimeUtils.fromString(string);

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));

        assertEquals(2014, cal.get(YEAR));
        assertEquals(12, cal.get(MONTH) + 1); // the MONTH value is 0-based
        assertEquals(29, cal.get(DAY_OF_MONTH));
        assertEquals(18, cal.get(HOUR_OF_DAY));
        assertEquals(24, cal.get(MINUTE));
        assertEquals(3, cal.get(SECOND));
        assertEquals(941, cal.get(MILLISECOND));
    }

    public void testToString() throws ParseException {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(YEAR, 2014);
        cal.set(MONTH, 12 - 1); // MONTH is 0-based
        cal.set(DAY_OF_MONTH, 29);
        cal.set(HOUR_OF_DAY, 18);
        cal.set(MINUTE, 24);
        cal.set(SECOND, 3);
        cal.set(MILLISECOND, 941);

        final Date date = cal.getTime();
        final String dateString = JsonTimeUtils.toString(date);

        final Date dateStringDate = JsonTimeUtils.fromString(dateString);

        assertEquals(date, dateStringDate);
    }

}