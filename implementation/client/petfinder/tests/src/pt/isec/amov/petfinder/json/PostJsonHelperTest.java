package pt.isec.amov.petfinder.json;

import android.util.Base64;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONException;
import pt.isec.amov.petfinder.core.AnimalColor;
import pt.isec.amov.petfinder.core.Location;

import java.util.*;

import static pt.isec.amov.petfinder.core.AnimalColor.BLACK;
import static pt.isec.amov.petfinder.core.AnimalColor.WHITE;

/**
 * Created by mgois on 03-01-2015.
 */
public class PostJsonHelperTest extends TestCase {

    public void testColorsFromJsonArray() throws JSONException {
        final JSONArray array = new JSONArray().put("black").put("white");

        final Set<AnimalColor> colors = PostJsonHelper.colorsFromJsonArray(array);

        assertEquals(2, colors.size());
        assertTrue(colors.contains(BLACK));
        assertTrue(colors.contains(WHITE));
    }

    public void testColorsToJsonArray() throws JSONException {
        final Set<AnimalColor> colors = EnumSet.of(BLACK, WHITE);

        final JSONArray array = PostJsonHelper.colorsToJsonArray(colors);

        assertEquals(2, array.length());
        // The original set doesn't guarantee ordering so we don't know the position of each
        // color. Therefore we put the array contents in side another set and compare that with the original
        final Set<AnimalColor> arrayColors = new HashSet<AnimalColor>(array.length());
        for (int i = 0; i < array.length(); i++) {
            arrayColors.add(AnimalColor.fromValue(array.getString(i)));
        }

        assertTrue(colors.equals(arrayColors));
    }

    public void testImagesFromJsonArray() throws JSONException {
        final byte[] image = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
        final String encoded = Base64.encodeToString(image, Base64.NO_WRAP);
        final JSONArray array = new JSONArray().put(encoded);

        final List<byte[]> images = PostJsonHelper.imagesFromJsonArray(array);

        assertEquals(1, images.size());
        assertTrue(Arrays.equals(image, images.get(0)));
    }

    public void testImagesToJsonArray() throws JSONException {
        final byte[] image = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
        final String encoded = Base64.encodeToString(image, Base64.NO_WRAP);
        final List<byte[]> images = new ArrayList<byte[]>(1);
        images.add(image);

        final JSONArray array = PostJsonHelper.imagesToJsonArray(images);

        assertEquals(1, array.length());
        assertEquals(encoded, array.getString(0));
    }

    public void testLocationFromJsonArray() throws JSONException {
        final double latitude = 40.1938516;
        final double longitude = -8.4098084;
        final JSONArray array = new JSONArray().put(latitude).put(longitude);

        final Location location = PostJsonHelper.locationFromJsonArray(array);

        assertEquals(latitude, location.getLatitute());
        assertEquals(longitude, location.getLongitude());
    }

    public void testLocationToJsonArray() throws JSONException {
        final double latitude = 40.1938516;
        final double longitude = -8.4098084;
        final Location location = new Location(latitude, longitude);

        final JSONArray array = PostJsonHelper.locationToJsonArray(location);

        assertEquals(latitude, array.get(0));
        assertEquals(longitude, array.get(1));
    }



}
