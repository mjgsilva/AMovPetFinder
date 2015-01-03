package pt.isec.amov.petfinder;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pt.isec.amov.petfinder.entities.Post;

/**
 * Created by mgois on 03-01-2015.
 */
public class PostFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_fragment, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        
    }

    public interface PostHost {
        Post getPost();
    }

}