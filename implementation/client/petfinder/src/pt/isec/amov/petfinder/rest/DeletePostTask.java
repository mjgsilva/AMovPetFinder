package pt.isec.amov.petfinder.rest;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import org.json.JSONObject;

import static pt.isec.amov.petfinder.rest.RestConstants.VALID;
import static pt.isec.amov.petfinder.rest.RestConstants.VALID_OK;
import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.DELETE;

/**
 *
 */
public class DeletePostTask extends WebServiceTask {
    private static final String PATH = "/post";

    private final String token;

    protected DeletePostTask(final ApiParams apiParams, final String token, final Parameters params, final int id) {
        super(DELETE, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH + "/" + id), params.getBodyRequest());
        this.token = token;
    }

    @Override
    protected void configureRequest(final HttpUriRequest request) {
        final HttpDelete delete = (HttpDelete) request;
        delete.addHeader(AUTH, BEARER + " " + token);
    }

    @Override
    protected void onTaskSuccess(final String response) {
        try {
            final JSONObject json = new JSONObject(response);

            final boolean deleted = json.getString(VALID).equals(VALID_OK);
            onTaskSuccess(deleted);
        } catch (final JSONException e) {
            // TODO add log
            onTaskError(e);
        }
    }

    public void onTaskSuccess(final boolean deleted) {
        // Override to provide meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {
        // This task has no specific parameters
    }
}
