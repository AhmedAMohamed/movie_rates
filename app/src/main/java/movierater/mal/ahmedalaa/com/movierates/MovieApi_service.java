package movierater.mal.ahmedalaa.com.movierates;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ahmedalaa on 9/11/16.
 */
public class MovieApi_service extends IntentService{
    private StringBuilder stringBuilder;

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

                    Intent resultsIntent = new Intent("data_pop_movies");
                    JsonParser parser = new JsonParser(stringBuilder.toString());
                    Bundle dataBundle = parser.getData();
                    resultsIntent.putExtra("data", dataBundle);
                    sendBroadcast(resultsIntent);

                }
                catch (Exception e) {
                    Log.e("error_in", e.toString());
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch (Exception ex) {

            }
        }
    }
}
