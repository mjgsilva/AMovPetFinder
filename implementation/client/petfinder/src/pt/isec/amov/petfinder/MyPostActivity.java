package pt.isec.amov.petfinder;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import pt.isec.amov.petfinder.core.PetFinderApp;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.rest.GetPostByIdTask;
import pt.isec.amov.petfinder.rest.GetPostByIdTask.Parameters;
import pt.isec.amov.petfinder.rest.GetPostsAdvancedTask;

/**
 * Created by mgois on 04-01-2015.
 */
public class MyPostActivity extends Activity implements PostFragment.PostHost, DeleteMyPostConfirmationDialog.Listener {

    public static final String PARAM_POST_ID = "postId";

    private Post post;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_post_activity);

        final FragmentManager fm = getFragmentManager();
        final PetFinderApp app = (PetFinderApp) getApplication();
        final int id = getIntent().getExtras().getInt(PARAM_POST_ID);

        new GetPostByIdTask(app.getApiParams(), app.getToken().getAccessToken(), new Parameters(), id) {
            @Override
            public void onPostExecute(final Post post) {
                // Save the post and introduce the fragment
                MyPostActivity.this.post = post;

                fm.beginTransaction()
                        .add(R.id.myPost_frgPost, new PostFragment())
                        .commit();
            }
        }.execute();
    }

    @Override
    public Post getPost() {
        return post;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.my_post_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myPostMenu_delete:
                final DialogFragment fragment = new DeleteMyPostConfirmationDialog();
                fragment.show(getFragmentManager(), DeleteMyPostConfirmationDialog.TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogPostitiveClick(final DialogFragment dialog) {
        Toast.makeText(this, "Delete post", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // do nothing
    }
}