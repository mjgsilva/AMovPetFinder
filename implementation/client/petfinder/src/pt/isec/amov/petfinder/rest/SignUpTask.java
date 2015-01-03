package pt.isec.amov.petfinder.rest;

import org.json.JSONException;
import org.json.JSONObject;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.POST;

/**
 * Created by mario on 31/12/14.
 */
public class SignUpTask extends WebServiceTask {

    private static final String PATH = "/user";

    protected SignUpTask(final ApiParams apiParams, final Parameters params) {
        super(POST, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH), params.getBodyRequest());
        super.execute();
    }

    @Override
    protected void onPostExecute(final String response) {
        String valid = "notok";
        boolean isValid = false;
        if(!response.contains("Bad Gateway")) {
            try {
                JSONObject obj = new JSONObject(response);
                valid = obj.getString("valid");
            } catch (JSONException e) {}
        }
        if(valid.equals("ok"))
            isValid = true;
        this.onPostExecute(true);
    }

    public void onPostExecute(final boolean valid) {
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
