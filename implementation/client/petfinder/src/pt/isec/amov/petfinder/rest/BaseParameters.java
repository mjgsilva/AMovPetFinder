package pt.isec.amov.petfinder.rest;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public abstract class BaseParameters<T extends BaseParameters> {

    private int connTimeout;
    private int socketTimeout;
    protected List<NameValuePair> params = new ArrayList<NameValuePair>();

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

    public List<NameValuePair> getParams() {
        return Collections.unmodifiableList(params);
    }
}
