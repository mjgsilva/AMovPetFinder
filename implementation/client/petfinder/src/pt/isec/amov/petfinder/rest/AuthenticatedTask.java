package pt.isec.amov.petfinder.rest;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.io.IOException;

import static pt.isec.amov.petfinder.core.LogTags.REST;

/**
 *
 */
public class AuthenticatedTask<T> extends WebServiceTask<T> {
    protected AuthenticatedTask(final TaskType taskType, final int connTimeout, final int socketTimeout, final String url, final JSONObject requestBody) {
        super(taskType, connTimeout, socketTimeout, url, requestBody);
    }

    @Override
    protected T doInBackground(final Void... params) {
//        final T response = super.doInBackground(params);
//
//        if (response.getStatusLine().getStatusCode() == 401) {
//            // refresh the token
//            return super.doInBackground(params);
//        } else {
//            return response;
//        }
        HttpResponse response = null;
        T result = null;
        try {
            response = doResponse(taskType, url, requestBody);
            if (response.getStatusLine().getStatusCode() == 401) {
                // TODO refresh the token
                return onResponse(doResponse(taskType, url, requestBody));
            } else {
                return onResponse(response);
            }
        } catch (final IllegalStateException e) {
            Log.e(REST, "An error occurred while executing the API request", e);
            taskException = e; // hold the exception to use in the onTaskSuccess
        } catch (final IOException e) {
            Log.e(REST, "An error occurred while executing the API request", e);
            taskException = e; // hold the exception to use in the onTaskSuccess
        }

        return result;
    }
}
