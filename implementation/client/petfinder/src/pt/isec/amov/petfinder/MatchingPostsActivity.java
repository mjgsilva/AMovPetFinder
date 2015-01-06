package pt.isec.amov.petfinder;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import pt.isec.amov.petfinder.core.*;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.json.PostJsonHelper;
import pt.isec.amov.petfinder.rest.GetPostByIdTask;
import pt.isec.amov.petfinder.rest.GetPostsAdvancedTask;
import pt.isec.amov.petfinder.rest.SignUpTask;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mgois on 30-12-2014.
 */
public class MatchingPostsActivity extends Activity implements PostListFragment.PostListHost{

    public static final String PARAM_TYPE = "type";
    public static final String PARAM_SPECIE = "specie";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_COLORS = "color";
    public static final String PARAM_LOCATION= "location";

    private List<Post> posts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_posts_activity);

        final PetFinderApp app = (PetFinderApp)getApplication();
        final FragmentManager fm = getFragmentManager();

        final Intent intent = getIntent();
        final PostType type = (PostType) intent.getSerializableExtra(PARAM_TYPE);
        final AnimalSpecie specie = (AnimalSpecie) intent.getSerializableExtra(PARAM_SPECIE);
        final AnimalSize size = (AnimalSize) intent.getSerializableExtra(PARAM_SIZE);
        final Set<AnimalColor> color = (Set<AnimalColor>) intent.getSerializableExtra(PARAM_COLORS);
        final Location location = (Location) intent.getSerializableExtra(PARAM_LOCATION);

        new GetPostsAdvancedTask(app.getApiParams(), app.getToken().getAccessToken(), new GetPostsAdvancedTask.Parameters(type,location,specie,size,color)) {

            @Override
            public void onPostExecute(final List<Post> posts) {
                MatchingPostsActivity.this.posts = posts;

                fm.beginTransaction()
                        .add(R.id.matching_posts_activity_frgPosts, new PostListFragment())
                        .commit();
            }
        }.execute();


    }

    @Override
    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public void postSelected(Post post) {
        //final Intent intent = new Intent(this, MyPostActivity.class);
        //intent.putExtra(MyPostActivity.PARAM_POST_ID, post.getPostId());
        //startActivity(intent);
    }
}