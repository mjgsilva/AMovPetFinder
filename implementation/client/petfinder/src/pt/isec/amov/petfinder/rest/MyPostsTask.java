package pt.isec.amov.petfinder.rest;

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
    private static final String LOG_TAG = "API";

    protected MyPostsTask(final ApiParams apiParams, final String token, final GetPostsAdvancedTask.Parameters params) {
        super(GET, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH), params.getBodyRequest());
    }

    @Override
    protected void onPostExecute(final String response) {
        try {
            final JSONArray json = new JSONArray(new JSONTokener(response));
            final List<Post> posts = PostJsonHelper.fromJson(json);

            onPostExecute(posts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onPostExecute(final List<Post> posts) {
    }
}
