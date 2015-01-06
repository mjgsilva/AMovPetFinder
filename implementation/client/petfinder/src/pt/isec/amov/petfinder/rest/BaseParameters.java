package pt.isec.amov.petfinder.rest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public abstract class BaseParameters<T extends BaseParameters> {

    private int connTimeout = WebServiceTask.CONN_TIMEOUT;
    private int socketTimeout = WebServiceTask.SOCKET_TIMEOUT;
    private JSONObject requestBody = new JSONObject();

    public int getConnTimeout() {
        return connTimeout;
    }

    public T setConnTimeout(final int connTimeout) {
        this.connTimeout = connTimeout;

        return (T) this;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public T setSocketTimeout(final int socketTimeout) {
        this.socketTimeout = socketTimeout;

        return (T) this;
    }

    public void insertPair(String key, String value) {
        try {
            requestBody.accumulate(key,value);
        } catch (JSONException e) {}
    }

    public JSONObject getBodyRequest() { return requestBody; }
}
