package pt.isec.amov.petfinder.rest;

import pt.isec.amov.petfinder.core.AnimalColor;
import pt.isec.amov.petfinder.core.Location;
import pt.isec.amov.petfinder.entities.Post;

import java.util.Date;
import java.util.List;

import static pt.isec.amov.petfinder.rest.WebServiceTask.TaskType.POST;

/**
 *
 */
public class GetPostsAdvancedTask extends WebServiceTask {

    private static final String PATH = "/find";

    public GetPostsAdvancedTask(final ApiParams apiParams, final Parameters params) {
        super(POST, params.getConnTimeout(), params.getSocketTimeout(), apiParams.getUrl(PATH), params.getBodyRequest());
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

        // TODO Deprecate this constants
        public static final String POST_TYPE = "type"; //required
        public static final String GEOLOCATION = "location"; //required
        public static final String SPECIE = "specie"; // required
        public static final String SIZE = "size";
        public static final String COLOR = "color";
        public static final String POST_DATE = "publicationDate";

        // TODO consider a proper type for species?
        public Parameters(final int postType, final Location location, final String specie) {
            // TODO validate null
            insertPair(POST_TYPE, String.valueOf(postType));
            insertPair(GEOLOCATION, location.toString()); // TODO serialize properly [lat,lng]
            insertPair(SPECIE, specie);
        }

        // TODO consider a proper type for size?
        public Parameters size(final String size) {
            insertPair(SIZE, size); //TODO {.. metadata: { specie: x, ...} }

            return this;
        }

        // TODO multiple colors? Is this the correct type?
        public Parameters color(final AnimalColor color) {
            insertPair(COLOR, color.toString()); // TODO serialize properly

            return this;
        }

        public Parameters postDate(final Date date) {
            insertPair(POST_DATE, date.toString()); // TODO serialize properly

            return this;
        }

    }
}
