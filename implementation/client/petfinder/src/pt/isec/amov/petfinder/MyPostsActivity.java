package pt.isec.amov.petfinder;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import pt.isec.amov.petfinder.core.PetFinderApp;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.rest.MyPostsTask;
import pt.isec.amov.petfinder.rest.MyPostsTask.Parameters;

import java.util.List;

/**
 *
 */
public class MyPostsActivity extends Activity implements PostListFragment.PostListHost {

    private List<Post> posts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_posts_activity);

        final FragmentManager fm = getFragmentManager();
        final PetFinderApp app = (PetFinderApp) getApplication();

        // Fetch my posts and after that introduce the fragment that will render them
        new MyPostsTask(app.getApiParams(), app.getToken().getAccessToken(), new Parameters()) {
            @Override
            public void onTaskSuccess(final List<Post> posts) {
                // Save the posts and introduce the fragment
                MyPostsActivity.this.posts = posts;

                fm.beginTransaction()
                        .add(R.id.myPosts_frgPosts, new PostListFragment())
                        .commit();
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
        intent.putExtra(MyPostActivity.PARAM_POST_ID, post.getPostId());
        startActivity(intent);
    }
}