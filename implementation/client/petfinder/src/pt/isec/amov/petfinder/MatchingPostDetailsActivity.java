package pt.isec.amov.petfinder;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import pt.isec.amov.petfinder.core.PetFinderApp;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.rest.GetPostByIdTask;

/**
 *
 */
public class MatchingPostDetailsActivity extends Activity implements PostFragment.PostHost {

    public static final String PARAM_POST_ID = "postId";

    private Post post;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_post_details_activity);

        final FragmentManager fm = getFragmentManager();
        final PetFinderApp app = (PetFinderApp) getApplication();
        final int id = getIntent().getExtras().getInt(PARAM_POST_ID);

        new GetPostByIdTask(app.getApiParams(), app.getToken().getAccessToken(), new GetPostByIdTask.Parameters(), id) {
            @Override
            public void onTaskSuccess(final Post post) {
                // Save the post and introduce the fragment
                MatchingPostDetailsActivity.this.post = post;

                fm.beginTransaction()
                        .add(R.id.matchingPostDetails_frgPost, new PostFragment())
                        .commit();
            }
        }.execute();
    }

    @Override
    public Post getPost() {
        return post;
    }
}