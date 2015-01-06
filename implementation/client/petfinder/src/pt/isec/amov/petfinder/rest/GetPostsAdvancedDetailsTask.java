package pt.isec.amov.petfinder.rest;

import android.util.Log;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.json.PostJsonHelper;

import java.util.List;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.GET;

/**
 * Created by mario on 06/01/15.
 */

public class GetPostsAdvancedDetailsTask extends WebServiceTask<String> {

    private static final String PATH = "/announcer";

    private final String token;

    public GetPostsAdvancedDetailsTask(final ApiParams apiParams, final String token, final Parameters params, final int id) {
        super(GET, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH + "/" + id), params.getBodyRequest());
        this.token = token;
    }

    @Override
    protected void configureRequest(final HttpUriRequest request) {
        final HttpGet get = (HttpGet) request;
        get.addHeader(AUTH, BEARER + " " + token);
    }

    @Override
    protected String onResponse(final String response) {
        String phoneNumber;
        try {
            final JSONObject json = new JSONObject(new JSONTokener(response));
            phoneNumber = json.getString("phoneNumber");
        } catch (final JSONException e) {
            // TODO add log
            throw new RuntimeException(e); // TODO add message
        }
        return phoneNumber;
    }

    @Override
    public void onTaskSuccess(final String phoneNumber) {
        // Override to provide meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {
        // This task has no specific parameters
    }
}