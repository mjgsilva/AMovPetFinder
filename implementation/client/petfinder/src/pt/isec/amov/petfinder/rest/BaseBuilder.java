package pt.isec.amov.petfinder.rest;

import android.content.Context;

/**
 * Created by mgois on 31-12-2014.
 */
public abstract class BaseBuilder<T> {

    public T build(final Context ctx) {
        return build(ctx, WebServiceTask.CONN_TIMEOUT, WebServiceTask.SOCKET_TIMEOUT);
    }

    public abstract T build(final Context ctx, final int connTimeout, final int socketTimeout);

}
