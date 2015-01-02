package pt.isec.amov.petfinder.core;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Created by mario on 02/01/15.
 */
public class PetFinderApp extends Application {
    private SharedPreferences sharedPreferences;
    private Token token;

    @Override
    public void onCreate() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = tokenBuilder();
    }

    public Token getToken() {
        return token;
    }

    private Token tokenBuilder() {
        String accessToken, refreshToken;
        long expiresIn;

        accessToken = sharedPreferences.getString("accessToken","undefined");
        refreshToken = sharedPreferences.getString("refreshToken","undefined");
        expiresIn = sharedPreferences.getLong("expiresIn", 0);

        return new Token(accessToken,refreshToken,expiresIn);
    }

    public void putToken() {
        Editor editor = sharedPreferences.edit();
        editor.putString("accessToken",token.getAccessToken());
        editor.putString("refreshToken",token.getRefreshToken());
        editor.putLong("expiresIn",token.getExpiresIn());
        editor.commit();
    }
}
