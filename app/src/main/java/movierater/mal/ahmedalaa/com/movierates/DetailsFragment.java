package movierater.mal.ahmedalaa.com.movierates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.io.Serializable;
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

    public static final String FAV_PREF_NAME = "favourites_data";
    public static final String FAV_TAG = "fav";

    private final String server_base = "http://api.themoviedb.org/3/movie/";
    private final String api_key = "?api_key=3bd2b46edbf8a79a9b433f1f9c892323";


    private ArrayList<MovieData> movies;

    private BroadcastReceiver rec_review;
    private BroadcastReceiver rec_trailer;

    MovieAddedAdapter adapter;

    public DetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle m = this.getArguments();
        if (m == null) {
            m = getActivity().getIntent().getBundleExtra("movie");
        }

        final MovieData movie = (MovieData) m.getParcelable("data");

        final View v = inflater.inflate(R.layout.fragment_details, container, false);
        image = (ImageView) v.findViewById(R.id.posterthumb);
        plot = (TextView) v.findViewById(R.id.plot);
        title = (TextView) v.findViewById(R.id.title);
        date = (TextView) v.findViewById(R.id.date);
        rate = (TextView) v.findViewById(R.id.rate);
        final CheckBox fav_box = (CheckBox) v.findViewById(R.id.favourite_check);
        final RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.added_data_rec);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(adapter);
        handler = new FavouriteHandler() {
            @Override
            public void addToFav(MovieData m) throws JSONException {
                SharedPreferences prefs = getActivity().getSharedPreferences(FAV_PREF_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();
                String movie_json = new Gson().toJson(m);
                String favs = prefs.getString(FAV_TAG, null);

                if (favs != null) {
                    JSONArray ar = new JSONArray(favs);
                    if (!handler.foundInFav(ar, m)) {
                        ar.put(new JSONObject(movie_json));
                        editor.putString(FAV_TAG, ar.toString());
                        editor.apply();
                    }
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
                    if (mov.getId().equalsIgnoreCase(m.getId())) {
                        return i;
                    }
                }
                return -1;
            }

            @Override
            public boolean foundInFav(JSONArray ar, MovieData m) throws JSONException {
                for (int i = 0; i < ar.length(); i++) {
                    JSONObject obj = ar.getJSONObject(i);
                    if (obj.getString("id").equalsIgnoreCase(m.getId())) {
                        return true;
                    }
                }
                return false;
            }


        };

        try {
            movies = handler.getFavMov();
            for (MovieData mov : movies) {
                if (mov.getId().equalsIgnoreCase(movie.getId())) {
                    fav_box.setChecked(true);
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<MovieAdded> tr = new ArrayList<>();
        adapter = new MovieAddedAdapter(tr, getActivity());

        mRecyclerView.setAdapter(adapter);


        if (movie.getReviews() == null) {
            //http://api.themoviedb.org/3/movie/271110/reviews?api_key=3bd2b46edbf8a79a9b433f1f9c892323
            String query = "/reviews";
            Intent serviceIntent = new Intent(getActivity().getApplicationContext(), ReviewAPI_Service.class);
            serviceIntent.putExtra("uri", server_base + movie.getId() + query + api_key);
            getActivity().startService(serviceIntent);
        }
        else {
            adapter.updateList(movie.getReviews());
        }
        if (movie.getTrailers() == null) {
            String query = "/videos";
            Intent serviceIntent = new Intent(getActivity().getApplicationContext(), TrailerAPI_Service.class);
            serviceIntent.putExtra("uri", server_base + movie.getId() + query + api_key);
            getActivity().startService(serviceIntent);
        }
        else {
            adapter.updateList(movie.getTrailers());
        }


        rec_review = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String seriData = intent.getBundleExtra("data").getString("data");
                ArrayList<MovieAdded> reviews = new ArrayList();
                reviews.add(new Review("","Reviews","",""));
                try {
                    JSONArray ar = new JSONArray(seriData);
                    Gson gson = new Gson();
                    Type t = new TypeToken<Review> () {}.getType();
                    for (int i = 0; i < ar.length(); i++) {
                        MovieAdded m = gson.fromJson(ar.getJSONObject(i).toString(), t);
                        reviews.add(m);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                movie.setReviews(reviews);
                if (adapter == null) {
                    adapter.updateList(movie.getReviews());
                }
                else {
                    adapter.updateList(movie.getReviews());
                }
            }
        };

        rec_trailer =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String seriData = intent.getBundleExtra("data").getString("data");
                ArrayList<MovieAdded> trailers = new ArrayList();
                trailers.add(new Trailer(null,null, null, null, "Trailers", null,null,null));
                try {
                    JSONArray ar = new JSONArray(seriData);
                    Gson gson = new Gson();
                    Type t = new TypeToken<Trailer> () {}.getType();
                    for (int i = 0; i < ar.length(); i++) {
                        MovieAdded m = gson.fromJson(ar.getJSONObject(i).toString(), t);
                        trailers.add(m);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                movie.setTrailers(trailers);
                if (adapter == null) {
                    adapter.updateList(movie.getTrailers());
                }
                else {
                    adapter.updateList(movie.getTrailers());
                }
            }
        };

        getActivity().registerReceiver(rec_review, new IntentFilter("movies_reviews"));
        getActivity().registerReceiver(rec_trailer, new IntentFilter("movies_trailers"));

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
                .resize(700,700)
                .centerCrop()
                .into(image);
        plot.setText(movie.getOverview());
        title.setText(movie.getTitle());
        date.setText(movie.getDate());
        rate.setText(String.valueOf(movie.getVote_average()));

        return v;
    }


    @Override
    public void onDestroyView() {

    //    getActivity().unregisterReceiver(rec_review);
    //    getActivity().unregisterReceiver(rec_trailer);

        super.onDestroyView();
    }

    @Override
    public void onDetach() {

    //    getActivity().unregisterReceiver(rec_review);
    //    getActivity().unregisterReceiver(rec_trailer);

        super.onDetach();
    }
}
