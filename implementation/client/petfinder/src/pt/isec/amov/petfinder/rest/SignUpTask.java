package pt.isec.amov.petfinder.rest;

import android.content.Context;
import org.apache.http.message.BasicNameValuePair;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.POST;

/**
 * Created by mario on 31/12/14.
 */
public class SignUpTask extends WebServiceTask {

    protected SignUpTask(final Context ctx, final GetPostsTask.Parameters params) {
        super(ctx, POST, params.getConnTimeout(), params.getSocketTimeout(), params.getParams());
    }

    @Override
    protected void onPostExecute(final String response) {
        // TODO deserialize the response into the following variables
        boolean isValid = false;

        // call the task-specific overload
        this.onPostExecute(isValid);
    }

    public void onPostExecute(final boolean valid) {
        // override to provide some meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {

        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String PHONE_NUMBER = "phoneNumber";

        public Parameters(final String username, final String password, final String phoneNumber) {
            params.add(new BasicNameValuePair(USERNAME, username));
            params.add(new BasicNameValuePair(PASSWORD, password));
            params.add(new BasicNameValuePair(PHONE_NUMBER, phoneNumber));
        }
    }
}
