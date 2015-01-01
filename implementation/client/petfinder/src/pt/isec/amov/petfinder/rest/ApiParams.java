package pt.isec.amov.petfinder.rest;

import static pt.isec.amov.petfinder.Validation.assertNotNullOrEmpty;

/**
 * Credentials to use in the REST API requests
 */
public class ApiParams {

    private final String baseUrl;
    private final String id;
    private final String secret;

    public ApiParams(final String baseUrl, final String id, final String secret) {
        assertNotNullOrEmpty(baseUrl, "the baseUrl parameter cannot be empty");
        assertNotNullOrEmpty(id, "the id parameter cannot be empty");
        assertNotNullOrEmpty(secret, "the secret parameter cannot be empty");
        this.baseUrl = baseUrl;
        this.id = id;
        this.secret = secret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getUrl(final String path) {
        return baseUrl + path;
    }

}
