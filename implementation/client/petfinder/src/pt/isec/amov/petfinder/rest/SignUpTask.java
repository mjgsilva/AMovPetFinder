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
public class SignUpTask extends WebServiceTask {

    private static final String PATH = "/user";

    protected SignUpTask(final ApiParams apiParams, final Parameters params) {
        super(POST, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH), params.getBodyRequest());
        super.execute();
    }

    @Override
    protected void onTaskSuccess(final String response) {
        String valid = VALID_NOT_OK;
        boolean isValid = false;
        if(!response.contains(BAD_GATEWAY)) {
            try {
                JSONObject obj = new JSONObject(response);
                valid = obj.getString(VALID);
            } catch (final JSONException e) {
                // TODO add log
                onTaskError(e);
            }
        }
        if(valid.equals(VALID_OK))
            isValid = true;
        this.onTaskSuccess(true);
    }

    public void onTaskSuccess(final boolean valid) {
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
