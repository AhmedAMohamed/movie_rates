package movierater.mal.ahmedalaa.com.movierates;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by ahmedalaa on 9/16/16.
 */
public interface FavouriteHandler {

    public void addToFav(MovieData m) throws Exception;
    public void deleteFromFav(MovieData m) throws Exception;
    public ArrayList<MovieData> getFavMov() throws JSONException;
    public int findIndex(JSONArray ar, MovieData m) throws JSONException;
    boolean foundInFav(JSONArray ar, MovieData m) throws JSONException;
}
