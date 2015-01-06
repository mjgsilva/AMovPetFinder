package pt.isec.amov.petfinder.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 */
public class StreamUtils {

    public static String inputStreamToString(final InputStream is) throws IOException {
        final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        final StringBuilder total = new StringBuilder();

        try {
            String line;
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (final IOException e) {
            throw e;
        }

        return total.toString();
    }

}
