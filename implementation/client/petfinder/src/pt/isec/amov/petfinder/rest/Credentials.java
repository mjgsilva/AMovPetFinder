package pt.isec.amov.petfinder.rest;

import static pt.isec.amov.petfinder.Validation.assertNotNullOrEmpty;

/**
 * Created by mgois on 01-01-2015.
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
