package pt.isec.amov.petfinder.core;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import pt.isec.amov.petfinder.entities.Token;

/**
 * Created by mario on 02/01/15.
 */
public class PetFinderApp extends Application {
    private SharedPreferences sharedPreferences;
    private Token token;

    public PetFinderApp() {
        sharedPreferences = getSharedPreferences("petfinder",MODE_PRIVATE);
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
    }
}
