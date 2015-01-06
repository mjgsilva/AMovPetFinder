package pt.isec.amov.petfinder.rest;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import pt.isec.amov.petfinder.core.LogTags;

import java.io.*;

import static pt.isec.amov.petfinder.core.LogTags.REST;
import static pt.isec.amov.petfinder.rest.StreamUtils.inputStreamToString;

/**
 *
 */
public abstract class WebServiceTask<T> extends AsyncTask<Void, Integer, T> {

    // connection timeout, in milliseconds (waiting to connect)
    public static final int CONN_TIMEOUT = 3000;

    // socket timeout, in milliseconds (waiting for data)
    public static final int SOCKET_TIMEOUT = 5000;

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String MIME_JSON = "application/json";
    public static final String ACCEPT = "Accept";
    public static final String AUTH = "Authorization";
    public static final String BEARER = "Bearer";
    public static final String BASIC = "Basic";

    protected final TaskType taskType;
    protected final int connTimeout;
    protected final int socketTimeout;
    protected final String url;
    protected final JSONObject requestBody;

    protected Exception taskException;

    protected WebServiceTask(final TaskType taskType, final int connTimeout, final int socketTimeout, final String url, final JSONObject requestBody) {
        // TODO add null checks
        this.taskType = taskType;
        this.connTimeout = connTimeout;
        this.socketTimeout = socketTimeout;
        this.requestBody = requestBody;
        this.url = url;
    }

    @Override
    protected T doInBackground(final Void... params) {
        HttpResponse response = null;
        T result = null;
        try {
            response = doResponse(taskType, url, requestBody);
            result = onResponse(response);
        } catch (final Exception e) {
            Log.e(REST, "An error occurred while executing the API request", e);
            taskException = e; // hold the exception to use in the onTaskSuccess
        }

        return result;
    }

    @Override
    protected void onPostExecute(final T response) {
        if (taskException != null) {
            onTaskError(taskException);
        } else {
            onTaskSuccess(response);
        }
    }

    protected void configureRequest(final HttpUriRequest request) { }

    protected T onResponse(final HttpResponse response) throws IOException {
        // read the entity content and call the overload
        return onResponse(inputStreamToString(response.getEntity().getContent()));
    }

    protected T onResponse(final String content) {
        // Override to convert into the return value
        return null;
    }

    protected void onTaskSuccess(final T content) {
        // Override to handle the result of the task execution
    }

    protected void onTaskError(final Exception e) {
        // Override to handle error during the task execution
    }

    private HttpParams getHttpParams() {
        HttpParams http = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(http, connTimeout);
        HttpConnectionParams.setSoTimeout(http, socketTimeout);

        return http;
    }

    protected HttpResponse doResponse(final TaskType type, final String url, final JSONObject requestBody) throws IOException {
        // Use our connection and data timeouts as parameters for our
        // DefaultHttpClient
        HttpClient http = new DefaultHttpClient(getHttpParams());
        HttpResponse response = null;

        Log.i(REST, "[" + type + "] " + url);

        switch (type) {
            case POST:
                HttpPost post = new HttpPost(url);
                post.addHeader(ACCEPT, MIME_JSON);
                post.addHeader(CONTENT_TYPE, MIME_JSON);
                configureRequest(post);

                StringEntity se = new StringEntity(requestBody.toString());
                post.setEntity(se);

                response = execute(http, post);
                break;
            case GET:
                HttpGet get = new HttpGet(url);
                get.addHeader(CONTENT_TYPE, MIME_JSON);
                configureRequest(get);

                response = execute(http, get);
                break;
            case DELETE:
                HttpDelete delete = new HttpDelete(url);
                delete.addHeader(CONTENT_TYPE, MIME_JSON);
                configureRequest(delete);

                response = execute(http, delete);
                break;
        }

        return response;
    }

    // Deprecate?
    private HttpResponse execute(final HttpClient http, final HttpUriRequest request) throws IOException {
        return http.execute(request);
    }

    public static enum TaskType {

        POST(1),
        GET(2),
        DELETE(3);

        public final int type;

        private TaskType(final int type) {
            this.type = type;
        }

    }

}
