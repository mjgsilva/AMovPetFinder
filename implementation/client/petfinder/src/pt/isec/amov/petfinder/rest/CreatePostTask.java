package pt.isec.amov.petfinder.rest;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;
import pt.isec.amov.petfinder.core.*;

import java.util.List;
import java.util.Set;

import static pt.isec.amov.petfinder.json.PostJsonHelper.colorsToJsonArray;
import static pt.isec.amov.petfinder.json.PostJsonHelper.imagesToJsonArray;
import static pt.isec.amov.petfinder.rest.PostConstants.LATITUDE;
import static pt.isec.amov.petfinder.rest.PostConstants.LONGITUDE;
import static pt.isec.amov.petfinder.rest.PostConstants.Metadata.SIZE;
import static pt.isec.amov.petfinder.rest.PostConstants.Metadata.SPECIE;
import static pt.isec.amov.petfinder.rest.PostConstants.IMAGES;
import static pt.isec.amov.petfinder.rest.PostConstants.TYPE;
import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.POST;

/**
 *
 */
public class CreatePostTask extends WebServiceTask {

    private static final String PATH = "/post";

    String accessToken;

    public CreatePostTask(final ApiParams apiParams, final String accessToken, final GetPostsAdvancedTask.Parameters params) {
        super(POST, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH), params.getBodyRequest());
        this.accessToken = accessToken;
    }

    @Override
    protected void configureRequest(final HttpPost post) {
        post.addHeader(AUTH, BEARER + " ");
    }

    @Override
    protected void onPostExecute(final String response) {
        String valid = "";
        boolean isValid = false;
        try {
            final JSONObject jsonObject = new JSONObject(response);
            valid = jsonObject.getString("valid");
        } catch(JSONException e) { e.printStackTrace(); }

        if(valid.equals("ok"))
            isValid = true;

        this.onPostExecute(isValid);
    }

    public void onPostExecute(final boolean valid) {
        // override to provide some meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {

        public Parameters(final PostType type, final Location location, final AnimalSpecie specie, final AnimalSize size, final Set<AnimalColor> color) {
            // TODO validate null
            insertPair(TYPE, type.getValue());
            insertPair(LATITUDE, Double.toString(location.getLatitute()));
            insertPair(LONGITUDE, Double.toString(location.getLongitude()));
            insertPair(SPECIE, specie.getValue());
            insertPair(SIZE, size.getValue());
            /* insertPair(COLOR, colorsToJsonArray(color).toString()); */
        }

        public Parameters image(final List<byte[]> image) {
            insertPair(IMAGES, imagesToJsonArray(image).toString());

            return this;
        }
    }
}
