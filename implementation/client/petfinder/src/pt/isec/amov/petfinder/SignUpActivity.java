package pt.isec.amov.petfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import pt.isec.amov.petfinder.core.PetFinderApp;
import pt.isec.amov.petfinder.rest.ApiParams;
import pt.isec.amov.petfinder.rest.SignUpTask;

/**
 *
 */
public class SignUpActivity extends Activity {

    PetFinderApp app;
    EditText edtUsername,edtPassword,edtPhoneNumber;
    Button btnSignUp;
    String username, password,phoneNumber,errSignIn,errFromServer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        app = (PetFinderApp)getApplication();
        edtUsername = (EditText)findViewById(R.id.sign_up_edtUsername);
        edtPassword = (EditText)findViewById(R.id.sign_up_edtPassword);
        edtPhoneNumber = (EditText)findViewById(R.id.sign_up_edtPhoneNumber);
        btnSignUp = (Button)findViewById(R.id.sign_up_btnSignUp);
        errSignIn = app.getString(R.string.sign_up_errSignIn);
        errFromServer = app.getString(R.string.sign_up_errFromServer);

        phoneNumber = getPhoneNumber();
        if(!isPhoneNumberEmpty(phoneNumber)) {
            edtPhoneNumber.setText(phoneNumber);
            edtPhoneNumber.setEnabled(false);
        }

        btnSignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username = edtUsername.getText().toString();
                        password = edtPassword.getText().toString();
                        phoneNumber = edtPhoneNumber.getText().toString();

                        if(isUsernameEmpty(username) || isPasswordEmpty(password) || isPhoneNumberEmpty(phoneNumber)) {
                            showErrorMessage(errSignIn);
                        } else {
                            ApiParams apiParams = app.getApiParams();
                            final SignUpTask task = new SignUpTask(apiParams,
                                    new SignUpTask.Parameters(username, password, phoneNumber).setConnTimeout(5000)) {

                                @Override
                                public void onTaskSuccess(final Boolean isValid) {
                                    if(isValid) {
                                        launchStartActivity();
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

    private void launchStartActivity() {
        Intent intent = new Intent(this,StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showErrorMessage(String errMessage) {
        Toast.makeText(getApplicationContext(), errMessage, Toast.LENGTH_LONG).show();
    }

    private String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        return tMgr.getLine1Number();
    }

    private boolean isUsernameEmpty(String username) {
        return username.trim().equals("");
    }

    private boolean isPasswordEmpty(String password) {
        return password.trim().equals("");
    }

    private boolean isPhoneNumberEmpty(String phoneNumber) {
        return phoneNumber.trim().equals("");
    }
}