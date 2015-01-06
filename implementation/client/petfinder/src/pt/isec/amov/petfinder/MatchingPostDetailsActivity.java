package pt.isec.amov.petfinder;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import pt.isec.amov.petfinder.core.PetFinderApp;
import pt.isec.amov.petfinder.core.PostType;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.rest.GetPostByIdTask;
import pt.isec.amov.petfinder.rest.GetPostsAdvancedDetailsTask;

/**
 *
 */
public class MatchingPostDetailsActivity extends Activity implements PostFragment.PostHost {

    public static final String PARAM_POST_ID = "postId";


    private Post post;
    PetFinderApp app;
    int id;
    String foundMsg, lostMsg, successMsg, errMsg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_post_details_activity);

        app = (PetFinderApp) getApplication();
        final FragmentManager fm = getFragmentManager();
        id = getIntent().getExtras().getInt(PARAM_POST_ID);
        foundMsg = app.getString(R.string.matching_post_details_menu_foundMsg);
        lostMsg = app.getString(R.string.matching_post_details_menu_lostMsg);
        successMsg = app.getString(R.string.matching_post_details_menu_successMsg);
        errMsg = app.getString(R.string.matching_post_details_menu_errMsg);


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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.matching_post_detailed_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.matching_post_details_menu_contact:
                getPhoneNumber();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getPhoneNumber() {
        new GetPostsAdvancedDetailsTask(app.getApiParams(), app.getToken().getAccessToken(), new GetPostsAdvancedDetailsTask.Parameters(), id) {

            @Override
            public void onTaskSuccess(final String phoneNumber) {
                sendSMS(phoneNumber);
            }
        }.execute();
    }

    private void sendSMS(final String phoneNumber) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, getSMSText(), null, null);
            showMessage(successMsg);
            launchMainActivity();
        } catch (Exception e) {
            showMessage(errMsg);
        }
    }

    private String getSMSText() {
        if(post.getType() == PostType.FOUND)
            return foundMsg;
        else
            return lostMsg;
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showMessage(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

}