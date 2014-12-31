package pt.isec.amov.petfinder.rest;

import android.content.Context;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.POST;

/**
 *
 */
public class AuthenticateUserTask extends WebServiceTask {

    private AuthenticateUserTask(final Context ctx, final TaskType taskType, final int connTimeout, final int socketTimeout, List<? extends NameValuePair> params) {
        super(ctx, taskType, connTimeout, socketTimeout, params);
    }

    @Override
    protected void onPostExecute(final String response) {
        // TODO deserialize the response into the following variables
        String accessToken = "";
        long refreshTime = 0;
        long expiresIn = 0;

        // call the task-specific overload
        this.onPostExecute(accessToken, refreshTime, expiresIn);
    }

    public void onPostExecute(final String accessToken, final long refreshTime, final long expiresIn) {
        // override to provide some meaningful behavior
    }

    public static class Builder extends BaseBuilder<AuthenticateUserTask> {

        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";

        private List<NameValuePair> params = new ArrayList<NameValuePair>();

        public Builder(final String username, final String password) {
            params.add(new BasicNameValuePair(USERNAME, username));
            params.add(new BasicNameValuePair(PASSWORD, password));
        }

        @Override
        public AuthenticateUserTask build(final Context ctx, final int connTimeout, final int socketTimeout) {
            return new AuthenticateUserTask(ctx, POST, connTimeout, socketTimeout, params);
        }

    }
}
