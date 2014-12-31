package pt.isec.amov.petfinder.rest;

import android.content.Context;
import android.graphics.Color;
import com.google.android.gms.maps.model.LatLng;
import org.apache.http.message.BasicNameValuePair;
import pt.isec.amov.petfinder.entities.Post;

import java.util.Date;
import java.util.List;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.GET;

/**
 *
 */
public class GetPostsTask extends WebServiceTask {

    public GetPostsTask(Context ctx, final Parameters params) {
        super(ctx, GET, params.getConnTimeout(), params.getSocketTimeout(), params.getParams());
    }

    @Override
    protected void onPostExecute(final String s) {
        // TODO deserialize the response into the following variable
        List<Post> posts = null;

        // call the task-specific overload
        onPostExecute(posts);
    }

    public void onPostExecute(final List<Post> posts) {
        // override to provide some meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {

        public static final String POST_ID = "postId"; // required
        public static final String POST_TYPE = "type"; //required
        public static final String GEOLOCATION = "location"; //required
        public static final String SPECIE = "specie"; // required
        public static final String IMAGE = "image";
        public static final String SIZE = "size";
        public static final String COLOR = "color";
        public static final String OBSERVATIONS = "obs";
        public static final String POST_DATE = "publicationDate";

        // TODO consider a proper type for species?
        public Parameters(final int postId, final int postType, final LatLng latLng, final String specie) {
            // TODO validate null
            params.add(new BasicNameValuePair(POST_ID, String.valueOf(postId)));
            params.add(new BasicNameValuePair(POST_TYPE, String.valueOf(postType)));
            params.add(new BasicNameValuePair(GEOLOCATION, latLng.toString())); // TODO serialize properly [lat,lng]
            params.add(new BasicNameValuePair(SPECIE, specie));
        }

        public Parameters image(final String image) {
            params.add(new BasicNameValuePair(IMAGE,image));

            return this;
        }

        // TODO consider a proper type for size?
        public Parameters size(final String size) {
            params.add(new BasicNameValuePair(SIZE, size)); //TODO {.. metadata: { specie: x, ...} }

            return this;
        }

        // TODO multiple colors? Is this the correct type?
        public Parameters color(final Color color) {
            params.add(new BasicNameValuePair(COLOR, color.toString())); // TODO serialize properly

            return this;
        }

        public Parameters observation(final String observation) {
            params.add(new BasicNameValuePair(OBSERVATIONS, observation)); // TODO serialize properly

            return this;
        }

        public Parameters postDate(final Date date) {
            params.add(new BasicNameValuePair(POST_DATE, date.toString())); // TODO serialize properly

            return this;
        }

    }
}
