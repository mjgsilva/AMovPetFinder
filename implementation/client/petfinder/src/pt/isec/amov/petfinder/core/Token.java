package pt.isec.amov.petfinder.core;

/**
 * Created by mario on 02/01/15.
 */
public class Token {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;

    public Token(String accessToken,String refreshToken,long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean isTokenExpired() {
        long unixtime = System.currentTimeMillis() / 1000L;
        return expiresIn < unixtime;
    }

    public boolean isRefreshTokedUndefined() {
        return refreshToken.equals("undefined");
    }
}
