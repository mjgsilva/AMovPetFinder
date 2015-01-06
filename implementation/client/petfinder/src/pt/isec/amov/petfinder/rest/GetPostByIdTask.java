package pt.isec.amov.petfinder.rest;

import org.apache.http.client.methods.HttpGet;
import pt.isec.amov.petfinder.entities.Post;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.GET;

/**
 * Created by mgois on 06-01-2015.
 */
public class GetPostByIdTask extends WebServiceTask {

    private static final String PATH = "/post";

    private final String token;

    protected GetPostByIdTask(final ApiParams apiParams, final String token, final Parameters params, final int id) {
        super(GET, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH + "/" + id), params.getBodyRequest());
        this.token = token;
    }

    @Override
    protected void configureRequest(final HttpGet get) {
        get.addHeader(AUTH, BEARER + " " + token);
    }

    @Override
    protected void onPostExecute(final String s) {

    }

    public void onPostExecute(final Post post) {
        // Override to provide meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {

    }
}
