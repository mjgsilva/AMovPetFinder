package pt.isec.amov.petfinder.rest;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.POST;

/**
 *
 */
public class CreatePostTask extends WebServiceTask {

    private static final String PATH = "/post";

    private final String token;

    protected CreatePostTask(final ApiParams apiParams, final String token, final GetPostsAdvancedTask.Parameters params) {
        super(POST, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH), params.getBodyRequest());
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

        // TODO deprecate these constants and user the ones in the interface PostConstants?
        public static final String POST_TYPE = "type";
        public static final String LATITUDE = "lat";
        public static final String LONGITUDE = "long";
        public static final String SPECIE = "specie";
        public static final String IMAGE = "image";
        public static final String SIZE = "size";
        public static final String COLOR = "color";
        public static final String OBSERVATIONS = "obs";

        public Parameters(final String postType, final String latitude, final String longitude, final String specie) {
            insertPair(POST_TYPE, postType);
            insertPair(LATITUDE, latitude);
            insertPair(LONGITUDE, longitude);
            insertPair(SPECIE, specie);
        }

        public Parameters image(final String image) {
            insertPair(IMAGE, image);

            return this;
        }

        public Parameters size(final String size) {
            insertPair(SIZE, size);

            return this;
        }

        public Parameters color(final String color) {
            insertPair(COLOR, color);

            return this;
        }

        public Parameters observation(final String observation) {
            insertPair(OBSERVATIONS, observation);

            return this;
        }
    }
}
