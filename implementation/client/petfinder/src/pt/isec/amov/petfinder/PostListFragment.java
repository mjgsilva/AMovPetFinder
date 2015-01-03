package pt.isec.amov.petfinder;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import pt.isec.amov.petfinder.entities.Post;

import java.util.List;

/**
 *
 */
public class PostListFragment extends ListFragment {

    private PostListHost host;
    private List<Post> posts;

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        if (activity instanceof PostListHost) {
            host = (PostListHost) activity;
            posts = host.getPosts();
            final PostListArrayAdapter adapter = new PostListArrayAdapter(activity, posts);
            setListAdapter(adapter);
        } else {
            throw new ClassCastException(activity.toString() + " must implement the " + PostListHost.class.getSimpleName() + " interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        host = null;
        posts = null;
    }

    @Override
    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        super.onListItemClick(l, v, position, id);

        host.postSelected(posts.get(position));
    }

    public interface PostListHost {
        List<Post> getPosts();

        void postSelected(final Post post);
    }

}