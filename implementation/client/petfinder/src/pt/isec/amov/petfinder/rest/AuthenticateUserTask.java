package pt.isec.amov.petfinder.rest;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.POST;

/**
 *
 */
public class AuthenticateUserTask extends WebServiceTask {

    private static final String TAG = "AuthenticateUserTask";
    private static final String PATH = "/oauth/token";

    private final ApiParams credentials;

    public AuthenticateUserTask(final ApiParams apiParams, final Parameters params) {
        super(POST, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH), params.getBodyRequest());
        this.credentials = apiParams;
        super.execute();
    }

    @Override
    protected void configureRequest(final HttpPost post) {
        final String cred = credentials.getId() + ":" + credentials.getSecret();
        final String encoded = Base64.encodeToString(cred.getBytes(), Base64.NO_WRAP);
        post.setHeader(AUTH, BASIC + " " + encoded);
    }

    @Override
    protected void onPostExecute(final String response) {
        boolean isLoginValid = false;
        String accessToken = "";
        String refreshToken = "";
        long expiresIn = 0;

        if(!response.equals("Unauthorized") || !response.contains("Bad Gateway"))
        {
            try {
                JSONObject obj = new JSONObject(response);
                accessToken = obj.getString("access_token");
                refreshToken = obj.getString("refresh_token");
                expiresIn = obj.getLong("expires_in");
            } catch (JSONException e) {}
            isLoginValid = true;
        }
        // call the task-specific overload
        this.onPostExecute(isLoginValid,accessToken, refreshToken, expiresIn);
    }

    public void onPostExecute(final boolean isLoginValid, final String accessToken, final String refreshToken, final long expiresIn) {
        // override to provide some meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {

        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String GRANT_TYPE = "grant_type";

        public Parameters(final String username, final String password) {
            insertPair(USERNAME, username);
            insertPair(PASSWORD, password);
            insertPair(GRANT_TYPE, PASSWORD);
        }
    }
}
