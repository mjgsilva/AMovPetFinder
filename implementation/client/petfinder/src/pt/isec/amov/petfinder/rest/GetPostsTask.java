package pt.isec.amov.petfinder.rest;

import android.content.Context;
import android.graphics.Color;
import com.google.android.gms.maps.model.LatLng;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import pt.isec.amov.petfinder.entities.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.GET;

/**
 *
 */
public class GetPostsTask extends WebServiceTask {

    private GetPostsTask(Context ctx, TaskType taskType, int connTimeout, int socketTimeout, List<? extends NameValuePair> params) {
        super(ctx, taskType, connTimeout, socketTimeout, params);
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

    public static class Builder extends BaseBuilder<GetPostsTask> {

        public static final String GEOLOCATION = "geolocation"; // required
        public static final String SPECIES = "species"; // required
        public static final String SIZE = "size";
        public static final String COLOR = "color";
        public static final String POST_DATE = "postDate";

        private List<NameValuePair> params = new ArrayList<NameValuePair>();

        // TODO consider a proper type for species?
        public Builder(final LatLng latLng, final String species) {
            params.add(new BasicNameValuePair(GEOLOCATION, latLng.toString())); // TODO serialize properly
            params.add(new BasicNameValuePair(SPECIES, species));
        }

        // TODO consider a proper type for size?
        public Builder size(final String build) {
            params.add(new BasicNameValuePair(SIZE, build));

            return this;
        }

        // TODO multiple colors? Is this the correct type?
        public Builder color(final Color color) {
            params.add(new BasicNameValuePair(COLOR, color.toString())); // TODO serialize properly

            return this;
        }

        public Builder postDate(final Date date) {
            params.add(new BasicNameValuePair(POST_DATE, date.toString())); // TODO serialize properly

            return this;
        }

        @Override
        public GetPostsTask build(Context ctx, int connTimeout, int socketTimeout) {
            return new GetPostsTask(ctx, GET, connTimeout, socketTimeout, params);
        }
    }
}
