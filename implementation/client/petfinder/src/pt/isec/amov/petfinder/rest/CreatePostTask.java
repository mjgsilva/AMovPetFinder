package pt.isec.amov.petfinder.rest;

import android.content.Context;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.POST;

/**
 * Created by mario on 31/12/14.
 */
public class CreatePostTask extends WebServiceTask {

    private final String token;

    protected CreatePostTask(final Context ctx, final String token, final GetPostsTask.Parameters params) {
        super(ctx, POST, params.getConnTimeout(), params.getSocketTimeout(), params.getParams());
        this.token = token;
    }

    @Override
    protected void configureRequest(final HttpPost post) {
        post.addHeader(AUTH, BEARER + " " + token);
    }

    @Override
    protected void onPostExecute(final String response) {
        // TODO deserialize the response into the following variables
        boolean isValid = false;

        // call the task-specific overload
        this.onPostExecute(isValid);
    }

    public void onPostExecute(final boolean valid) {
        // override to provide some meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {

        public static final String POST_TYPE = "type";
        public static final String LATITUDE = "lat";
        public static final String LONGITUDE = "long";
        public static final String SPECIE = "specie";
        public static final String IMAGE = "image";
        public static final String SIZE = "size";
        public static final String COLOR = "color";
        public static final String OBSERVATIONS = "obs";

        public Parameters(final String postType, final String latitude, final String longitude, final String specie) {
            params.add(new BasicNameValuePair(POST_TYPE, postType));
            params.add(new BasicNameValuePair(LATITUDE, latitude));
            params.add(new BasicNameValuePair(LONGITUDE, longitude));
            params.add(new BasicNameValuePair(SPECIE, specie));
        }

        public Parameters image(final String image) {
            params.add(new BasicNameValuePair(IMAGE, image));

            return this;
        }

        public Parameters size(final String size) {
            params.add(new BasicNameValuePair(SIZE, size));

            return this;
        }

        public Parameters color(final String color) {
            params.add(new BasicNameValuePair(COLOR, color));

            return this;
        }

        public Parameters observation(final String observation) {
            params.add(new BasicNameValuePair(OBSERVATIONS, observation));

            return this;
        }
    }
}
