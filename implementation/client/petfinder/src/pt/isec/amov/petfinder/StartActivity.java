package pt.isec.amov.petfinder;

import android.app.Activity;
import android.os.Bundle;
import pt.isec.amov.petfinder.rest.AuthenticateUserTask;
import pt.isec.amov.petfinder.rest.Credentials;

public class StartActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        // Usage example
        final Credentials credentials = new Credentials("", "");
        final String username = "";
        final String password = "";
        final AuthenticateUserTask task = new AuthenticateUserTask(this, credentials,
                new AuthenticateUserTask.Parameters(username, password).setConnTimeout(5000)) {

            @Override
            public void onPostExecute(String accessToken, String refreshToken, long expiresIn) {
                // TODO do something meaningful
            }
        };
    }
}
