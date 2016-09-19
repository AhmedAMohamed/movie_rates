package movierater.mal.ahmedalaa.com.movierates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private BroadcastReceiver rec;

    ArrayList<MovieData> pop_movies;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View current_view = inflater.inflate(R.layout.fragment_main, container, false);

        final GridView gridview = (GridView) current_view.findViewById(R.id.movieView);
        final GridViewAdapter adapter = new GridViewAdapter(getContext());

        final ArrayList<String> imgPathes = new ArrayList();

        pop_movies = new ArrayList();

        ((MainActivity)getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                String server_base = "https://api.themoviedb.org/3/";
                String api_key = "?api_key=3bd2b46edbf8a79a9b433f1f9c892323";
                SharedPreferences prefs = getActivity().getSharedPreferences("preferences", 0);
                String query = "";
                int user_option = prefs.getInt("order_by", 0);

                if(user_option == 0) {
                    query = "movie/popular";
                }
                else if (user_option == 1) {
                    query = "movie/top_rated";
                }
                else if (user_option == 2) {
                    query = "$$FAVOURITE$$";
                }
                Intent serviceIntent = new Intent(getActivity().getApplicationContext(), MovieApi_service.class);
                serviceIntent.putExtra("uri", server_base+query+api_key);
                getActivity().startService(serviceIntent);

            }
        });

        rec = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Serializable seriData = intent.getBundleExtra("data").getSerializable("data");
                ArrayList<MovieData> movies = (ArrayList<MovieData>)seriData;
                pop_movies.clear();
                imgPathes.clear();
                String server_base = "https://api.themoviedb.org/3/movie/";
                String api_key = "/reviews?api_key=3bd2b46edbf8a79a9b433f1f9c892323";
                Intent serviceIntent = new Intent(getActivity().getApplicationContext(), ReviewAPI_Service.class);
                serviceIntent.putExtra("data", movies);
                getActivity().startService(serviceIntent);

                pop_movies = movies;
                for (MovieData movie : movies) {
                    imgPathes.add("http://image.tmdb.org/t/p/w185" + movie.getBackdrop_path());
                }
                adapter.setImgIds(imgPathes);
                gridview.setAdapter(adapter);
            }
        };
        getActivity().registerReceiver(rec, new IntentFilter("data_pop_movies"));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Bundle data = new Bundle();
                data.putParcelable("data",  pop_movies.get(position));
                Intent detailsActivity = new Intent(getActivity().getApplicationContext(),Details.class);
                detailsActivity.putExtra("data", data);
                getActivity().startActivity(detailsActivity);
            }
        });

        return current_view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity)getActivity()).getFragmentRefreshListener().onRefresh();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(rec);
        super.onDestroy();
    }
}
