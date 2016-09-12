package movierater.mal.ahmedalaa.com.movierates;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {

    ImageView image;
    TextView plot;
    TextView title;
    TextView date;
    TextView rate;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle m = getActivity().getIntent().getBundleExtra("data");
        MovieData movie = (MovieData) m.getParcelable("data");

        View v = inflater.inflate(R.layout.fragment_details, container, false);
        image = (ImageView) v.findViewById(R.id.posterthumb);
        plot = (TextView) v.findViewById(R.id.plot);
        title = (TextView) v.findViewById(R.id.title);
        date = (TextView) v.findViewById(R.id.date);
        rate = (TextView) v.findViewById(R.id.rate);

        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w185"+movie.getPoster_path())
                .into(image);
        plot.setText(movie.getOverview());
        title.setText(movie.getTitle());
        date.setText(movie.getDate());
        rate.setText(String.valueOf(movie.getVote_average()));

        return v;
    }
}
