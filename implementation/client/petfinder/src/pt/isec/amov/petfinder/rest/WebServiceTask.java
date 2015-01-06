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

import java.io.*;

/**
 *
 */
public abstract class WebServiceTask extends AsyncTask<Void, Integer, HttpResponse> {

    private static final String TAG = "WebServiceTask";

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

    private final TaskType taskType;
    private final int connTimeout;
    private final int socketTimeout;
    private final String url;
    private final JSONObject requestBody;

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
    protected HttpResponse doInBackground(final Void... params) {
        HttpResponse response = null;
        try {
            response = doResponse(url);
        } catch (final IllegalStateException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            taskException = e; // hold the exception to use in the onTaskSuccess
        } catch (final IOException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            taskException = e; // hold the exception to use in the onTaskSuccess
        }

        return response;
    }

    @Override
    protected void onPostExecute(final HttpResponse response) {
        if (taskException != null) {
            onTaskError(taskException);
        } else {
            try {
                onTaskSuccess(response);
            } catch (final IOException e) {
                // If an error occurs while processing the response, it's still a task error
                onTaskError(e);
            }
        }
    }

    protected void configureRequest(final HttpUriRequest request) { }

    protected void onTaskSuccess(final HttpResponse response) throws IOException {
        // read the entity content and call the overload
        onTaskSuccess(inputStreamToString(response.getEntity().getContent()));
    }

    protected void onTaskSuccess(final String content) {
        // Override to handle the result of the task execution
    }

    protected void onTaskError(final Exception e) {
        // Override to handle error during the task execution
    };

    private String inputStreamToString(final InputStream is) throws IOException {
        final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        final StringBuilder total = new StringBuilder();

        try {
            // Read response until the end
            String line;
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (final IOException e) {
            // TODO remove this log ?
            Log.e(TAG, e.getLocalizedMessage(), e);
            throw e;
        }

        return total.toString();
    }

    private HttpParams getHttpParams() {

        HttpParams http = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(http, connTimeout);
        HttpConnectionParams.setSoTimeout(http, socketTimeout);

        return http;
    }

    private HttpResponse doResponse(String url) throws IOException {
        // Use our connection and data timeouts as parameters for our
        // DefaultHttpClient
        HttpClient httpClient = new DefaultHttpClient(getHttpParams());
        HttpResponse response = null;

        try {
            switch (taskType) {
                case POST:
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.addHeader(ACCEPT, MIME_JSON);
                    httpPost.addHeader(CONTENT_TYPE, MIME_JSON);
                    configureRequest(httpPost);

                    StringEntity se = new StringEntity(requestBody.toString());
                    httpPost.setEntity(se);

                    response = execute(httpClient, httpPost);
                    break;
                case GET:
                    HttpGet httpGet = new HttpGet(url);
                    httpGet.addHeader(CONTENT_TYPE, MIME_JSON);
                    configureRequest(httpGet);

                    response = execute(httpClient, httpGet);
                    break;
                case DELETE:
                    HttpDelete httpDelete = new HttpDelete(url);
                    httpDelete.addHeader(CONTENT_TYPE, MIME_JSON);
                    configureRequest(httpDelete);

                    response = execute(httpClient, httpDelete);
                    break;
            }
        } catch (final IOException e) {
            // TODO do some logging?
            Log.e(TAG, e.getLocalizedMessage(), e);

            throw e;
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
