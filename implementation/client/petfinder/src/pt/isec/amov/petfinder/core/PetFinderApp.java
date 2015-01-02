package pt.isec.amov.petfinder.core;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import pt.isec.amov.petfinder.rest.ApiParams;

/**
 * Created by mario on 02/01/15.
 */
public class PetFinderApp extends Application {
    private SharedPreferences sharedPreferences;
    private ApiParams apiParams;
    private Token token;

    private final String baseUrl = "https://mjgsilva.eu/api";
    private final String clientId = "mcQbcbUdi1dmlzd4fkdlsbMcf";
    private final String clientSecret = "cYtbFcnbHTdYdCSbAckd68cvgdvbfjc9d7c0dMd2dcbFCebTbS";

    @Override
    public void onCreate() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        apiParams = new ApiParams(baseUrl,clientId,clientSecret);
        token = tokenBuilder();
    }

    public ApiParams getApiParams() { return apiParams; }

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
