package pt.isec.amov.petfinder.json;

import android.util.Base64;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pt.isec.amov.petfinder.core.*;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.entities.Post.Metadata;

import java.util.*;

import static android.util.Base64.NO_WRAP;
import static pt.isec.amov.petfinder.core.AnimalColor.BLACK;
import static pt.isec.amov.petfinder.core.AnimalColor.WHITE;
import static pt.isec.amov.petfinder.core.AnimalSize.MEDIUM;
import static pt.isec.amov.petfinder.core.AnimalSpecie.CAT;
import static pt.isec.amov.petfinder.core.PostType.FOUND;
import static pt.isec.amov.petfinder.rest.PostConstants.*;
import static pt.isec.amov.petfinder.rest.PostConstants.Metadata.*;

/**
 *
 */
public class PostJsonHelperTest extends TestCase {

    public void testToJson() throws JSONException {
        final PostType type = FOUND;
        final AnimalSpecie specie = CAT;
        final AnimalColor color = BLACK;
        final AnimalSize size = MEDIUM;
        final byte[] image = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
        final String encodedImage = Base64.encodeToString(image, NO_WRAP);
        final double latitude = 40.1938516;
        final double longitude = -8.4098084;
        final Location location = new Location(latitude, longitude);
        final Date date = new Date();

        final Post post = new Post();
        post.setType(type);
        post.getImages().add(image);
        post.setLocation(location);

        final Metadata meta = post.getMetadata();
        meta.setSpecie(specie);
        meta.setSize(size);
        meta.getColors().add(color);


        final JSONObject json = PostJsonHelper.toJSON(post);

        assertEquals(type.getValue(), json.getString(TYPE));

        final JSONArray imagesJson = json.getJSONArray(IMAGES);
        assertEquals(1, imagesJson.length());
        assertEquals(encodedImage, imagesJson.getString(0));

        final JSONObject metaJson = json.getJSONObject(METADATA);
        assertEquals(specie.getValue(), metaJson.getString(SPECIE));
        assertEquals(size.getValue(), metaJson.getString(SIZE));

        final JSONArray colorJson = metaJson.getJSONArray(COLOR);
        assertEquals(1, colorJson.length());
        assertEquals(color.getValue(), colorJson.getString(0));
    }

    public void testFromJson() throws JSONException {
        final int postId = 1;
        final PostType type = FOUND;
        final AnimalSpecie specie = CAT;
        final AnimalColor color = BLACK;
        final AnimalSize size = MEDIUM;
        final byte[] image = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
        final String encodedImage = Base64.encodeToString(image, NO_WRAP);
        final double latitude = 40.1938516;
        final double longitude = -8.4098084;
        final Location location = new Location(latitude, longitude);
        final Date date = new Date();
        final String serializedDate = JsonTimeUtils.toString(date);

        final JSONObject json = new JSONObject()
                .put(POST_ID, postId)
                .put(TYPE, type.getValue())
                .put(IMAGES, new JSONArray().put(encodedImage))
                .put(LOCATION, new JSONArray().put(latitude).put(longitude))
                .put(PUB_DATE, serializedDate)
                .put(METADATA,
                        new JSONObject()
                                .put(SPECIE, specie.getValue())
                                .put(SIZE, size.getValue())
                                .put(COLOR, new JSONArray().put(color))
                );

        final Post post = PostJsonHelper.fromJSON(json);

        assertEquals(postId, post.getPostId());
        assertEquals(type, post.getType());
        assertEquals(date, post.getPublicationDate());

        final Metadata meta = post.getMetadata();
        assertEquals(specie, meta.getSpecie());
        assertEquals(size, meta.getSize());

        final Set<AnimalColor> postColors = meta.getColors();
        assertEquals(1, postColors.size());
        assertTrue(postColors.contains(color));

        final List<byte[]> postImages = post.getImages();
        assertEquals(1, postImages.size());
        assertTrue(Arrays.equals(image, postImages.get(0)));

        final Location postLocation = post.getLocation();
        assertEquals(latitude, postLocation.getLatitute());
        assertEquals(longitude, postLocation.getLongitude());
    }

    public void testFromJsonArray() throws JSONException {
        final int postId = 1;
        final int userId = 2;
        final PostType type = FOUND;
        final AnimalSpecie specie = CAT;
        final AnimalColor color = BLACK;
        final AnimalSize size = MEDIUM;
        final byte[] image = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
        final String encodedImage = Base64.encodeToString(image, NO_WRAP);
        final double latitude = 40.1938516;
        final double longitude = -8.4098084;
        final Location location = new Location(latitude, longitude);
        final Date date = new Date();
        final String serializedDate = JsonTimeUtils.toString(date);

        final JSONArray json = new JSONArray().put(new JSONObject()
                .put(POST_ID, postId)
                .put(TYPE, type.getValue())
                .put(IMAGES, new JSONArray().put(encodedImage))
                .put(LOCATION, new JSONArray().put(latitude).put(longitude))
                .put(PUB_DATE, serializedDate)
                .put(METADATA,
                        new JSONObject()
                                .put(SPECIE, specie.getValue())
                                .put(SIZE, size.getValue())
                                .put(COLOR, new JSONArray().put(color))
                ));

        final List<Post> posts = PostJsonHelper.fromJson(json);

        assertEquals(1, posts.size());
        assertEquals(postId, posts.get(0).getPostId());
        // Reasonably assume that the rest is ok
    }

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
        final String encoded = Base64.encodeToString(image, NO_WRAP);
        final JSONArray array = new JSONArray().put(encoded);

        final List<byte[]> images = PostJsonHelper.imagesFromJsonArray(array);

        assertEquals(1, images.size());
        assertTrue(Arrays.equals(image, images.get(0)));
    }

    public void testImagesToJsonArray() throws JSONException {
        final byte[] image = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
        final String encoded = Base64.encodeToString(image, NO_WRAP);
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

    /* During the adjustment in order to match the API implementation,
    sending a post should pass lat and lng as request params
    public void testLocationToJsonArray() throws JSONException {
        final double latitude = 40.1938516;
        final double longitude = -8.4098084;
        final Location location = new Location(latitude, longitude);

        final JSONArray array = PostJsonHelper.locationToJsonArray(location);

        assertEquals(latitude, array.get(0));
        assertEquals(longitude, array.get(1));
    } */

}
