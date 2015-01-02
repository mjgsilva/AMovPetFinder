package pt.isec.amov.petfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import pt.isec.amov.petfinder.core.PetFinderApp;

/**
 *
 */
public class MainActivity extends Activity {

    PetFinderApp petFinderApp;
    LinearLayout llCreate, llMyPosts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        petFinderApp = (PetFinderApp)getApplication();
        llCreate = (LinearLayout)findViewById(R.id.main_llCreate);
        llMyPosts = (LinearLayout)findViewById(R.id.main_llMyPosts);

        if(petFinderApp.getToken().isTokenExpired()) {
            launchStartActivity();
        }

        llCreate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchCreatePostActivity();
                    }
                }
        );

        llMyPosts.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchMyPostsActivity();
                    }
                }
        );
    }

    private void launchStartActivity() {
        Intent intent = new Intent(this,StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void launchCreatePostActivity() {
        Intent intent = new Intent(this,CreatePostActivity.class);
        startActivity(intent);
    }

    private void launchMyPostsActivity() {
        Intent intent = new Intent(this,MyPostsActivity.class);
        startActivity(intent);
    }
}