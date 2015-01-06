package pt.isec.amov.petfinder.json;

import android.util.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pt.isec.amov.petfinder.Validation;
import pt.isec.amov.petfinder.core.*;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.entities.Post.Metadata;

import java.text.ParseException;
import java.util.*;

import static android.util.Base64.NO_WRAP;
import static pt.isec.amov.petfinder.rest.PostConstants.*;
import static pt.isec.amov.petfinder.rest.PostConstants.Metadata.*;

/**
 *
 */
public class PostJsonHelper {

    public static JSONObject toJSON(final Post post) throws JSONException {
        Validation.assertNotNull(post, "The post cannot be null");

        final Metadata meta = post.getMetadata();

        return new JSONObject()
            .put(TYPE, post.getType().getValue())
            .put(LATITUDE, post.getLocation().getLatitute())
            .put(LONGITUDE, post.getLocation().getLongitude())
            .put(IMAGES, imagesToJsonArray(post.getImages()))
            .put(METADATA,
                    new JSONObject()
                            .put(SPECIE, meta.getSpecie().getValue())
                            .put(SIZE, meta.getSize().getValue())
                            .put(COLOR, colorsToJsonArray(meta.getColors()))
            );
    }

    public static List<Post> fromJson(final JSONArray json) throws JSONException {
        Validation.assertNotNull(json, "The JSON array cannot be null");

        final List<Post> posts = new ArrayList<Post>(json.length());

        for (int i = 0; i < json.length(); i++) {
            posts.add(fromJSON(json.getJSONObject(i)));
        }

        return posts;
    }

    public static Post fromJSON(final JSONObject json) throws JSONException {
        Validation.assertNotNull(json, "The JSON object cannot be null");

        final Post post = new Post();

        post.setPostId(json.getInt(POST_ID));
        post.setType(PostType.fromValue(json.getString(TYPE)));
        post.getImages().addAll(imagesFromJsonArray(json.getJSONArray(IMAGES)));
        post.setLocation(locationFromJsonArray(json.getJSONArray(LOCATION)));
        try {
            post.setPublicationDate(JsonTimeUtils.fromString(json.getString(PUBDATE)));
        } catch (final ParseException e) {
            // We don't want to pollute the interface with a ParseException just because we chose to use
            // Java's SimpleDateFormat to parse the dates. Wrap it in a JSONException
            throw new JSONException("Cannot parse the publication date");
        }

        final Metadata metadata = post.getMetadata();
        final JSONObject meta = json.getJSONObject(METADATA);
        metadata.setSpecie(AnimalSpecie.fromValue(meta.getString(SPECIE)));
        metadata.setSize(AnimalSize.fromValue(meta.getString(SIZE)));
        metadata.getColors().addAll(colorsFromJsonArray(meta.getJSONArray(COLOR)));

        return post;
    }

    static Set<AnimalColor> colorsFromJsonArray(final JSONArray json) throws JSONException {
        Validation.assertNotNull(json, "The JSON array cannot be null");

        final Set<AnimalColor> colors = new HashSet<AnimalColor>(json.length());

        for (int i = 0; i < json.length(); i++) {
            colors.add(AnimalColor.fromValue(json.getString(i)));
        }

        return colors;
    }

    static JSONArray colorsToJsonArray(final Set<? extends AnimalColor> colors) {
        final JSONArray array = new JSONArray();

        for (final AnimalColor color : colors) {
            array.put(color.getValue());
        }

        return array;
    }

    static List<byte[]> imagesFromJsonArray(final JSONArray json) throws JSONException {
        final List<byte[]> images = new ArrayList<byte[]>(json.length());

        for (int i = 0; i < json.length(); i++) {
            images.add(Base64.decode(json.getString(i), NO_WRAP));
        }

        return images;
    }

    static JSONArray imagesToJsonArray(final List<byte[]> images) {
        final JSONArray array = new JSONArray();

        for(final byte[] image : images) {
            array.put(Base64.encodeToString(image, NO_WRAP));
        }

        return array;
    }

    static Location locationFromJsonArray(final JSONArray json) throws JSONException {
        Validation.assertNotNull(json, "The JSON array cannot be null");

        return new Location(json.getDouble(0), json.getDouble(1));
    }
}
