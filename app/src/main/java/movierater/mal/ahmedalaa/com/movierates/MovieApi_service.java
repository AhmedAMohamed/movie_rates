package movierater.mal.ahmedalaa.com.movierates;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmedalaa on 9/11/16.
 */
public class MovieApi_service extends IntentService{


    private StringBuilder stringBuilder;
    private ArrayList<MovieData> movies;

    public MovieApi_service() {
        super("");

    }

    public MovieApi_service(String name) {
        super(name);
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            String uri = intent.getStringExtra("uri");
            if (uri.contains("$$FAVOURITE$$")) {
                FavouriteHandler handler = new FavouriteHandler() {
                    @Override
                    public void addToFav(MovieData m) throws Exception {
                        throw new Exception();
                    }

                    @Override
                    public void deleteFromFav(MovieData m) throws Exception {
                        throw new Exception();
                    }

                    @Override
                    public ArrayList<MovieData> getFavMov() throws JSONException {
                        SharedPreferences prefs = getSharedPreferences(DetailsFragment.FAV_PREF_NAME, 0);
                        String favs = prefs.getString(DetailsFragment.FAV_TAG, null);
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
                        return -1;
                    }

                    @Override
                    public boolean foundInFav(JSONArray ar, MovieData m) throws JSONException {
                        return false;
                    }
                };
                try {
                    movies = handler.getFavMov();
                    Intent resultsIntent = new Intent("data_pop_movies");
                    Bundle dataBundle = new Bundle();
                    dataBundle.putSerializable("data", movies);
                    resultsIntent.putExtra("data", dataBundle);
                    sendBroadcast(resultsIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    URL url = new URL(uri);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setDoInput(true);
                        urlConnection.setDoOutput(true);
                        urlConnection.connect();

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                    }
                    catch (Exception e) {
                        Log.e("error_in", e.toString());
                    }
                    finally{
                        urlConnection.disconnect();
                        JsonParser parser = new JsonParser(stringBuilder.toString());
                        ArrayList<MovieData> movies_list = parser.getData();
                        Intent resultsIntent = new Intent("data_pop_movies");
                        Bundle dataBundle = new Bundle();
                        dataBundle.putSerializable("data", movies_list);
                        resultsIntent.putExtra("data", dataBundle);
                        sendBroadcast(resultsIntent);
                    }
                }
                catch (Exception ex) {

                }
            }
        }
    }

}
