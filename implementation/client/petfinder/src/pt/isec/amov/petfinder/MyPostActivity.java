package pt.isec.amov.petfinder;

import android.app.Activity;
import android.os.Bundle;
import pt.isec.amov.petfinder.entities.Post;

/**
 * Created by mgois on 04-01-2015.
 */
public class MyPostActivity extends Activity implements PostFragment.PostHost {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_post_activity);
    }

    @Override
    public Post getPost() {


        return null;
    }
}