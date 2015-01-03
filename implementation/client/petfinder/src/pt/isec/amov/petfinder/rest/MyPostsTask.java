package pt.isec.amov.petfinder.rest;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import pt.isec.amov.petfinder.entities.Post;

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
            JSONObject json = new JSONObject(response);

        } catch (JSONException e) {
            Log.w(LOG_TAG, "An error occurred ");
        }
    }

    public void onPostExecute(final List<Post> posts) {

    }
}
