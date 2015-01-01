package pt.isec.amov.petfinder.rest;

import static pt.isec.amov.petfinder.Validation.assertNotNullOrEmpty;

/**
 * Credentials to use in the REST API requests
 */
public class Credentials {

    private final String id;
    private final String secret;

    public Credentials(final String id, final String secret) {
        assertNotNullOrEmpty(id, "the id parameter cannot be empty");
        assertNotNullOrEmpty(secret, "the secret parameter cannot be empty");
        this.id = id;
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }
}
