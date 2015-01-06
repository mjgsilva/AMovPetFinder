package pt.isec.amov.petfinder.rest;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import org.json.JSONObject;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.json.PostJsonHelper;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.GET;

/**
 * Created by mgois on 06-01-2015.
 */
public class GetPostByIdTask extends WebServiceTask<Post> {

    private static final String PATH = "/post";

    private final String token;

    protected GetPostByIdTask(final ApiParams apiParams, final String token, final Parameters params, final int id) {
        super(GET, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH + "/" + id), params.getBodyRequest());
        this.token = token;
    }

    @Override
    protected void configureRequest(final HttpUriRequest request) {
        final HttpGet get = (HttpGet) request;
        get.addHeader(AUTH, BEARER + " " + token);
    }

    @Override
    protected Post onResponse(final String response) {
        final Post post;
        try {
            final JSONObject json = new JSONObject(response);
            post = PostJsonHelper.fromJSON(json);
        } catch (final JSONException e) {
            // TODO add log
            throw new RuntimeException(e); // TODO add message
        }

        return post;
    }

    public static class Parameters extends BaseParameters<Parameters> {
        // This task has no specific parameters
    }
}
