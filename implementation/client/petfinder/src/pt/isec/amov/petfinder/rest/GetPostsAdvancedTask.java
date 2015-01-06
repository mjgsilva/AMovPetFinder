package pt.isec.amov.petfinder.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import pt.isec.amov.petfinder.core.*;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.json.JsonTimeUtils;
import pt.isec.amov.petfinder.json.PostJsonHelper;
import pt.isec.amov.petfinder.rest.PostConstants.Metadata;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static pt.isec.amov.petfinder.json.PostJsonHelper.colorsToJsonArray;
import static pt.isec.amov.petfinder.rest.PostConstants.*;
import static pt.isec.amov.petfinder.rest.PostConstants.Metadata.*;
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
    protected void onPostExecute(final String response) {
        try {
            final JSONArray json = new JSONArray(new JSONTokener(response));
            final List<Post> posts = PostJsonHelper.fromJson(json);

            onPostExecute(posts);
        } catch (JSONException e) {
            e.printStackTrace(); // TODO create an override
        }
    }

    public void onPostExecute(final List<Post> posts) {
        // override to provide some meaningful behavior
    }

    public static class Parameters extends BaseParameters<Parameters> {

        public Parameters(final PostType type, final Location location, final AnimalSpecie specie) {
            // TODO validate null
            insertPair(TYPE, type.getValue());
            insertPair(LATITUDE, Double.toString(location.getLatitute()));
            insertPair(LONGITUDE, Double.toString(location.getLongitude()));
            insertPair(SPECIE, specie.getValue());
        }

        public Parameters size(final AnimalSize size) {
            insertPair(SIZE, size.getValue());

            return this;
        }

        public Parameters color(final Set<AnimalColor> color) {
            insertPair(COLOR, colorsToJsonArray(color).toString());

            return this;
        }

    }
}
