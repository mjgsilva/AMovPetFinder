package pt.isec.amov.petfinder;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import pt.isec.amov.petfinder.core.AnimalColor;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.ui.StringUtils;

import java.util.Set;

import static pt.isec.amov.petfinder.ui.StringUtils.getStringId;

/**
 * Created by mgois on 03-01-2015.
 */
public class PostFragment extends Fragment {

    private PostHost host;
    private Post post;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_fragment, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        if (activity instanceof PostHost) {
            host = (PostHost) activity;
            post = host.getPost();
            bindPost(post);

        } else {
            throw new ClassCastException(activity.toString() + " must implement the " + PostHost.class.getSimpleName() + " interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        host = null;
        post = null;
    }

    private void bindPost(final Post post) {
        final View view = getView();
        TextView txtSpecie = (TextView) view.findViewById(R.id.postFragment_txtSpecie);
        TextView txtSize = (TextView) view.findViewById(R.id.postFragment_txtSize);
        TextView txtColors = (TextView) view.findViewById(R.id.postFragment_txtColors);

        final Resources res = getResources();
        final Post.Metadata meta = post.getMetadata();

        txtSpecie.setText(res.getString(getStringId(meta.getSpecie())));
        txtSize.setText(res.getString(getStringId(meta.getSize())));
        txtColors.setText(getColors(res, meta.getColors()));
    }

    private String getColors(final Resources res, final Set<AnimalColor> colors) {
        final StringBuilder sb = new StringBuilder();

        for (final AnimalColor color : colors) {
            sb.append(res.getString(getStringId(color)));
        }

        return sb.toString();
    }

    public interface PostHost {
        Post getPost();
    }

}