package pt.isec.amov.petfinder.rest;

import org.json.JSONException;
import org.json.JSONObject;

import static pt.isec.amov.petfinder.rest.HttpConstants.BAD_GATEWAY;
import static pt.isec.amov.petfinder.rest.RestConstants.VALID;
import static pt.isec.amov.petfinder.rest.RestConstants.VALID_NOT_OK;
import static pt.isec.amov.petfinder.rest.RestConstants.VALID_OK;
import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.POST;

/**
 *
 */
public class SignUpTask extends WebServiceTask<Boolean> {

    private static final String PATH = "/user";

    protected SignUpTask(final ApiParams apiParams, final Parameters params) {
        super(POST, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH), params.getBodyRequest());
        super.execute();
    }

    @Override
    protected Boolean onResponse(final String response) {
        String valid = VALID_NOT_OK;
        boolean isValid = false;
        if(!response.contains(BAD_GATEWAY)) {
            try {
                JSONObject obj = new JSONObject(response);
                valid = obj.getString(VALID);
            } catch (final JSONException e) {
                // TODO add log
                throw new RuntimeException(e); // TODO add message
            }
        }
        if(valid.equals(VALID_OK))
            isValid = true;

        return isValid;
    }

    @Override
    public void onTaskSuccess(final Boolean valid) {
        // override to provide some meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {

        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String PHONE_NUMBER = "phoneNumber";

        public Parameters(final String username, final String password, final String phoneNumber) {
            insertPair(USERNAME, username);
            insertPair(PASSWORD, password);
            insertPair(PHONE_NUMBER, phoneNumber);
        }
    }
}
