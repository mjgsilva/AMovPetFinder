package pt.isec.amov.petfinder;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;
import pt.isec.amov.petfinder.core.AnimalSize;
import pt.isec.amov.petfinder.core.AnimalSpecie;
import pt.isec.amov.petfinder.core.Location;
import pt.isec.amov.petfinder.core.PostType;
import pt.isec.amov.petfinder.entities.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class MyPostsActivity extends Activity implements PostListFragment.PostListHost {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_posts_activity);

        final FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .add(R.id.myPosts_frgPosts, new PostListFragment())
                .commit();
    }

    @Override
    public List<Post> getPosts() {
        final List<Post> posts = new ArrayList<Post>();
        final Post post = new Post();
        post.setPostId(1);
        post.setUserId(2);
        post.setType(PostType.FOUND);
        final Post.Metadata meta = post.getMetadata();
        meta.setSpecie(AnimalSpecie.CAT);
        meta.setSize(AnimalSize.MEDIUM);
        meta.setLocation(new Location(40.1938516, -8.4098084));
        meta.setPublicationDate(new Date());

        posts.add(post);

        return posts;
    }

    @Override
    public void postSelected(final Post post) {
        Toast.makeText(this, "Show my post " + post, Toast.LENGTH_SHORT).show();
    }
}