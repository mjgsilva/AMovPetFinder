package pt.isec.amov.petfinder;

import android.app.Activity;
import android.os.Bundle;
import pt.isec.amov.petfinder.rest.AuthenticateUserTask;

public class StartActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        // Usage example
        final String username = "";
        final String password = "";
        final AuthenticateUserTask task = new AuthenticateUserTask(this,
                new AuthenticateUserTask.Parameters(username, password).setConnTimeout(5000)) {

            @Override
            public void onPostExecute(String accessToken, long refreshTime, long expiresIn) {
                // TODO do something meaningful
            }
        };
    }
}
