package movierater.mal.ahmedalaa.com.movierates;

import android.os.Bundle;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmedalaa on 9/11/16.
 */
public class JsonParser {

    String j_data;
    Bundle data;
    public JsonParser(String json) {
        j_data = json;
        data = new Bundle();
    }

    public Bundle getData() throws JSONException {
        ArrayList<MovieData> result = new ArrayList();

        JSONObject json = new JSONObject(j_data);
        JSONArray results = json.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject movie_json = results.getJSONObject(i);
            MovieData movie = new MovieData();
            movie.setAdult(movie_json.getBoolean("adult"));
            movie.setBackdrop_path(movie_json.getString("backdrop_path"));
            movie.setOriginal_title(movie_json.getString("original_title"));
            movie.setPopularity(movie_json.getDouble("popularity"));
            movie.setOverview(movie_json.getString("overview"));
            movie.setTitle(movie_json.getString("original_title"));
            movie.setPoster_path(movie_json.getString("poster_path"));
            movie.setDate(movie_json.getString("release_date"));
            movie.setVote_average(movie_json.getDouble("vote_average"));
            result.add(movie);
        }
        data.putSerializable("data", result);
        return data;
    }
}
