package pt.isec.amov.petfinder.json;

import android.util.Base64;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pt.isec.amov.petfinder.core.*;
import pt.isec.amov.petfinder.entities.Post;

import java.util.HashSet;
import java.util.Set;

import static android.util.Base64.NO_WRAP;
import static pt.isec.amov.petfinder.rest.PostConstants.*;

/**
 * Created by mgois on 03-01-2015.
 */
public class PostJsonHelper {

    public static JSONObject toJSON(final Post post) {
        return null;
    }

    public static Post fromJSON(final JSONObject json) {
        final Post post = new Post();

        try {
            post.setPostId(json.getInt(POST_ID));
            post.setUserId(json.getInt(USER_ID));
            post.setType(PostType.fromValue(json.getString(TYPE)));

            final Post.Metadata metadata = new Post.Metadata();
            metadata.setSpecie(Specie.fromValue(json.getString(MD_SPECIE)));
            metadata.setSize(AnimalSize.fromValue(json.getString(MD_SIZE)));
            metadata.getColors().addAll(getColors(json.getJSONArray(MD_COLOR)));
            metadata.setImage(Base64.decode(json.getString(MD_IMAGES), NO_WRAP));
            metadata.setLocation(getLocation(json.getJSONArray(MD_LOCATION)));
            // TODO parse and set publication date

            post.setMetadata(metadata);

            return post;
        } catch (JSONException e) {
            Log.w("", ""); // TODO complete
            return null;
        }
    }

    private static Set<? extends AnimalColor> getColors(final JSONArray json) throws JSONException {
        final Set<AnimalColor> colors = new HashSet<AnimalColor>(json.length());

        for (int i = 0; i < json.length(); i++) {
            colors.add(AnimalColor.fromValue(json.getString(i)));
        }

        return colors;
    }

    private static Location getLocation(final JSONArray json) throws JSONException {
        return new Location(json.getDouble(0), json.getDouble(1));
    }

}
