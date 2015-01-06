package pt.isec.amov.petfinder.rest;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import pt.isec.amov.petfinder.core.*;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.json.PostJsonHelper;


import java.util.List;
import java.util.Set;

import static pt.isec.amov.petfinder.json.PostJsonHelper.colorsToJsonArray;
import static pt.isec.amov.petfinder.rest.PostConstants.*;
import static pt.isec.amov.petfinder.rest.PostConstants.Metadata.*;
import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.POST;

/**
 *
 */
public class GetPostsAdvancedTask extends WebServiceTask {

    private static final String PATH = "/find";

    private final String accessToken;

    public GetPostsAdvancedTask(final ApiParams apiParams, final String accessToken, final Parameters params) {
        super(POST, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH), params.getBodyRequest());
        this.accessToken = accessToken;
    }

    @Override
    protected void configureRequest(final HttpUriRequest request) {
        final HttpPost post = (HttpPost) request;
        post.addHeader(AUTH, BEARER + " " + accessToken);
    }


    @Override
    protected void onTaskSuccess(final String response) {
        try {
            final JSONArray json = new JSONArray(new JSONTokener(response));
            final List<Post> posts = PostJsonHelper.fromJson(json);

            onTaskSuccess(posts);
        } catch (final JSONException e) {
            // TODO add log
            onTaskError(e);
        }
    }

    public void onTaskSuccess(final List<Post> posts) {
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
            insertPair(COLOR, colorsToJsonArray(color).toString());
        }
    }
}
