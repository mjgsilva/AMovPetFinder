package pt.isec.amov.petfinder;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pt.isec.amov.petfinder.core.AnimalColor;
import pt.isec.amov.petfinder.entities.Post;
import pt.isec.amov.petfinder.ui.TimeUtils;

import java.text.DateFormat;
import java.util.List;
import java.util.Set;

import static pt.isec.amov.petfinder.ui.StringUtils.getStringId;

/**
 *
 */
public class PostListArrayAdapter extends ArrayAdapter<Post> {

    private final Context context;
    private final List<Post> posts;
    private final LayoutInflater inflater;
    private final DateFormat dateFormat;

    public PostListArrayAdapter(final Context context, final List<Post> posts) {
        super(context, R.layout.post_list_item, posts);
        this.context = context;
        this.posts = posts;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dateFormat = TimeUtils.newDateFormat();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final View view = inflater.inflate(R.layout.post_list_item, parent, false);
        final TextView txtPostDescription = (TextView) view.findViewById(R.id.postListItem_txtPostDescription);
        final TextView txtPostPublicationDate = (TextView) view.findViewById(R.id.postListItem_txtPostPublicationDate);

        final Post post = posts.get(position);
        txtPostDescription.setText(getDescription(post));
        txtPostPublicationDate.setText(getPublicationDate(post));

        return view;
    }

    private String getDescription(final Post post) {
        final Resources res = context.getResources();
        final String description = res.getString(R.string.post_description);
        final String specie = res.getString(getStringId(post.getMetadata().getSpecie()));
        final String size = res.getString(getStringId(post.getMetadata().getSize()));
        final String color = getColors(res, post.getMetadata().getColors());

        return String.format(description, specie, size, color);
    }

    private String getColors(final Resources res, final Set<AnimalColor> colors) {
        final StringBuilder sb = new StringBuilder();

        for (final AnimalColor color : colors) {
            sb.append(res.getString(getStringId(color)));
        }

        return sb.toString();
    }

    private String getPublicationDate(final Post post) {
        return dateFormat.format(post.getPublicationDate());
    }


}
