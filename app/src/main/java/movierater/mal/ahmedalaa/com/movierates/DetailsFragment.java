package movierater.mal.ahmedalaa.com.movierates;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {

    ImageView image;
    TextView plot;
    TextView title;
    TextView date;
    TextView rate;

    private FavouriteHandler handler;

    public static final String FAV_PREF_NAME = "favourite_data";
    public static final String FAV_TAG = "fav";


    private ArrayList<MovieData> movies;

    public DetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle m = getActivity().getIntent().getBundleExtra("data");
        final MovieData movie = (MovieData) m.getParcelable("data");

        View v = inflater.inflate(R.layout.fragment_details, container, false);
        image = (ImageView) v.findViewById(R.id.posterthumb);
        plot = (TextView) v.findViewById(R.id.plot);
        title = (TextView) v.findViewById(R.id.title);
        date = (TextView) v.findViewById(R.id.date);
        rate = (TextView) v.findViewById(R.id.rate);
        final CheckBox fav_box = (CheckBox) v.findViewById(R.id.favourite_check);

        handler = new FavouriteHandler() {
            @Override
            public void addToFav(MovieData m) throws JSONException {
                SharedPreferences prefs = getActivity().getSharedPreferences(FAV_PREF_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();
                String movie_json = new Gson().toJson(m);
                String favs = prefs.getString(FAV_TAG, null);
                if (favs != null) {
                    JSONArray ar = new JSONArray(favs);
                    ar.put(new JSONObject(movie_json));
                    editor.putString(FAV_TAG, ar.toString());
                    editor.apply();
                }
                else {
                    JSONArray ar = new JSONArray();
                    ar.put(new JSONObject(movie_json));
                    editor.putString(FAV_TAG, ar.toString());
                    editor.apply();
                }
            }

            @Override
            public void deleteFromFav(MovieData m) throws JSONException {
                SharedPreferences prefs = getActivity().getSharedPreferences(FAV_PREF_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();
                String movie_json = new Gson().toJson(m);
                String favs = prefs.getString(FAV_TAG, null);
                if (favs != null) {
                    JSONArray ar = new JSONArray(favs);
                    int index = handler.findIndex(ar, m);
                    if (index >= 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            ar.remove(index);
                            editor.putString(FAV_TAG, ar.toString());
                            editor.apply();
                        }
                    }
                }
            }

            @Override
            public ArrayList<MovieData> getFavMov() throws JSONException {
                SharedPreferences prefs = getActivity().getSharedPreferences(FAV_PREF_NAME, 0);
                String favs = prefs.getString(FAV_TAG, null);
                if (favs != null) {
                    JSONArray ar = new JSONArray(favs);
                    Type t = new TypeToken<List<MovieData>>() {}.getType();
                    return new Gson().fromJson(ar.toString(), t);
                }
                else {
                    return new ArrayList<MovieData>();
                }
            }

            @Override
            public int findIndex(JSONArray ar, MovieData m) throws JSONException {
                for (int i = 0; i < ar.length(); i++) {
                    JSONObject obj = ar.getJSONObject(i);
                    Type t = new TypeToken<MovieData>() {} .getType();
                    MovieData mov = new Gson().fromJson(obj.toString(), t);
                    if (mov.equals(m)) {
                        return i;
                    }
                }
                return -1;
            }


        };

        try {
            boolean semaphore = false;
            movies = handler.getFavMov();
            for (MovieData mov : movies) {
                if (mov.equals(movie)) {
                    semaphore = true;
                    break;
                }
            }
            if (semaphore) {
                semaphore = false;
                fav_box.setChecked(true);
            }
            else {
                fav_box.setChecked(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        fav_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    try {
                        handler.addToFav(movie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        handler.deleteFromFav(movie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
