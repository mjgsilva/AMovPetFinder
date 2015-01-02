package pt.isec.amov.petfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import pt.isec.amov.petfinder.core.PetFinderApp;
import pt.isec.amov.petfinder.core.Token;
import pt.isec.amov.petfinder.rest.AuthenticateUserTask;
import pt.isec.amov.petfinder.rest.ApiParams;

public class StartActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    PetFinderApp petFinderApp;
    EditText edtUsername;
    EditText edtPassword;
    Button btnSignIn;
    TextView txtSignUp;

    private final String baseUrl = "https://mjgsilva.eu/api";
    private final String clientId = "mcQbcbUdi1dmlzd4fkdlsbMcf";
    private final String clientSecret = "cYtbFcnbHTdYdCSbAckd68cvgdvbfjc9d7c0dMd2dcbFCebTbS";

    private String username, password;
    private final String errSignIn= "Invalid input";
    private final String errFromServer = "Invalid username or password";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        petFinderApp = (PetFinderApp)getApplication();
        edtUsername = (EditText)findViewById(R.id.start_edtUsername);
        edtPassword = (EditText)findViewById(R.id.start_edtPassword);
        btnSignIn = (Button)findViewById(R.id.start_btnSignIn);
        txtSignUp = (TextView)findViewById(R.id.start_txtSignUp);

        if(!petFinderApp.getToken().isTokenExpired()) {
            launchMainMenu();
        }

        btnSignIn.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username = edtUsername.getText().toString();
                        password = edtPassword.getText().toString();

                        if(isUsernameEmpty(username) || isPasswordEmpty(password)) {
                            showErrorMessage(errSignIn);
                        } else {
                            final ApiParams credentials = new ApiParams(baseUrl, clientId, clientSecret);
                            final AuthenticateUserTask task = new AuthenticateUserTask(credentials,
                                    new AuthenticateUserTask.Parameters(username, password).setConnTimeout(5000)) {

                                @Override
                                public void onPostExecute(boolean loginIsValid, String accessToken, String refreshToken, long expiresIn) {
                                    if(loginIsValid) {
                                        setUpNewToken(accessToken,refreshToken,expiresIn);
                                        launchMainMenu();
                                    } else {
                                        showErrorMessage(errFromServer);
                                    }
                                }
                            };
                        }
                    }
                }
        );
    }

    private void showErrorMessage(String errMessage) {
        Toast.makeText(getApplicationContext(), errMessage, Toast.LENGTH_LONG).show();
    }

    private void launchMainMenu() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setUpNewToken(String accessToken, String refreshToken, long expiresIn) {
        Token token = petFinderApp.getToken();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setExpiresIn(expiresIn);
        petFinderApp.putToken();
    }

    private boolean isUsernameEmpty(String username) {
        return username.trim().equals("");
    }

    private boolean isPasswordEmpty(String username) {
        return username.trim().equals("");
    }
}
