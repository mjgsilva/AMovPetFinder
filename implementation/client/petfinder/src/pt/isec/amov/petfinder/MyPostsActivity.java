package pt.isec.amov.petfinder;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import pt.isec.amov.petfinder.core.PetFinderApp;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.rest.MyPostsTask;
import pt.isec.amov.petfinder.rest.MyPostsTask.Parameters;

import java.util.List;

import static pt.isec.amov.petfinder.MyPostActivity.PARAM_POST_ID;

/**
 *
 */
public class MyPostsActivity extends Activity implements PostListFragment.PostListHost {

    private List<Post> posts;
    private PetFinderApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_posts_activity);

        app = (PetFinderApp) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final FragmentManager fm = getFragmentManager();
        // Fetch my posts and after that introduce the fragment that will render them
        new MyPostsTask(app.getApiParams(), app.getToken().getAccessToken(), new Parameters()) {
            @Override
            public void onTaskSuccess(final List<Post> posts) {
                // Save the posts and introduce the fragment
                MyPostsActivity.this.posts = posts;

                fm.beginTransaction()
                        .replace(R.id.myPosts_frgPosts, new PostListFragment())
                        .commit();
            }

            @Override
            protected void onTaskError(final Exception e) {
                final String message = app.getString(R.string.my_posts_load_error);
                Toast.makeText(MyPostsActivity.this, message, Toast.LENGTH_LONG).show();
                finish();
            }
        }.execute();
    }

    @Override
    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public void postSelected(final Post post) {
        final Intent intent = new Intent(this, MyPostActivity.class);
        intent.putExtra(PARAM_POST_ID, post.getPostId());
        startActivity(intent);
    }
}