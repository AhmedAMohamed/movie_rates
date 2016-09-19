package movierater.mal.ahmedalaa.com.movierates;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ReviewAPI_Service extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "movierater.mal.ahmedalaa.com.movierates.action.FOO";
    private static final String ACTION_BAZ = "movierater.mal.ahmedalaa.com.movierates.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "movierater.mal.ahmedalaa.com.movierates.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "movierater.mal.ahmedalaa.com.movierates.extra.PARAM2";
    private StringBuilder stringBuilder;

    public ReviewAPI_Service() {
        super("ReviewAPI_Service");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ReviewAPI_Service.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ReviewAPI_Service.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String uri = intent.getStringExtra("uri");

            try {
                URL url = new URL(uri);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    urlConnection.setDoInput(true);
                    urlConnection.setRequestMethod("GET");
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
                    ReviewParser parser = new ReviewParser(stringBuilder.toString());
                    ArrayList<MovieAdded> reviews = parser.getData();
                    Intent resultsIntent = new Intent("movies_reviews");
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("data", new Gson().toJson(reviews));
                    resultsIntent.putExtra("data", dataBundle);
                    sendBroadcast(resultsIntent);
                }
            }
            catch (Exception ex) {

            }
        }
    }

    private ArrayList<MovieAdded> getReviews(String uri) {
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
                ReviewParser parser = new ReviewParser(stringBuilder.toString());
                ArrayList<MovieAdded> reviews = parser.getData();
                return reviews;
            }
        }
        catch (Exception ex) {

        }
        return null;
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
