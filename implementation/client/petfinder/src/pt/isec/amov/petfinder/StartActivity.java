package pt.isec.amov.petfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    PetFinderApp app;
    EditText edtUsername,edtPassword;
    Button btnSignIn;
    TextView txtSignUp;
    String errSignIn, errFromServer;

    private String username, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        app = (PetFinderApp)getApplication();
        edtUsername = (EditText)findViewById(R.id.start_edtUsername);
        edtPassword = (EditText)findViewById(R.id.start_edtPassword);
        btnSignIn = (Button)findViewById(R.id.start_btnSignIn);
        txtSignUp = (TextView)findViewById(R.id.start_txtSignUp);
        errSignIn = app.getString(R.string.start_btnSignIn);
        errFromServer = app.getString(R.string.start_errFromServer);


        if(!app.getToken().isTokenExpired()) {
            launchMainActivity();
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
                            ApiParams credentials = app.getApiParams();
                            final AuthenticateUserTask task = new AuthenticateUserTask(credentials,
                                    new AuthenticateUserTask.Parameters(username, password).setConnTimeout(5000)) {

                                @Override
                                public void onTaskSuccess(final Tokens tokens) {
                                    if(tokens.isLoginValid) {
                                        setUpNewToken(tokens.accessToken, tokens.refreshToken, tokens.expiresIn);
                                        launchMainActivity();
                                    } else {
                                        showErrorMessage(errFromServer);
                                    }
                                }
                            };
                        }
                    }
                }
        );

        txtSignUp.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchSignUpActivity();
                    }
                }
        );
    }

    private void showErrorMessage(String errMessage) {
        Toast.makeText(getApplicationContext(), errMessage, Toast.LENGTH_LONG).show();
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void launchSignUpActivity() {
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    private void setUpNewToken(String accessToken, String refreshToken, long expiresIn) {
        Token token = app.getToken();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setExpiresIn(expiresIn);
        app.putToken();
    }

    private boolean isUsernameEmpty(String username) {
        return username.trim().equals("");
    }

    private boolean isPasswordEmpty(String password) {
        return password.trim().equals("");
    }
}
