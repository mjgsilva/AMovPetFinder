package pt.isec.amov.petfinder.rest;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.json.PostJsonHelper;

import java.util.List;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.GET;

/**
 * Created by mgois on 02-01-2015.
 */
public class MyPostsTask extends WebServiceTask {

    private static final String PATH = "/myPosts";

    private final String token;

    public MyPostsTask(final ApiParams apiParams, final String token, final Parameters params) {
        super(GET, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH), params.getBodyRequest());
        this.token = token;
    }

    @Override
    protected void configureRequest(final HttpUriRequest request) {
        final HttpGet get = (HttpGet) request;
        get.addHeader(AUTH, BEARER + " " + token);
    }

    @Override
    protected void onPostExecute(final String response) {
        try {
            final JSONArray json = new JSONArray(new JSONTokener(response));
            final List<Post> posts = PostJsonHelper.fromJson(json);

            onPostExecute(posts);
        } catch (JSONException e) {
            e.printStackTrace(); // TODO create an override
        }
    }

    public void onPostExecute(final List<Post> posts) {
        // Override to provide meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {
        // This task has no specific parameters
    }
}
