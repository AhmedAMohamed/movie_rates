package movierater.mal.ahmedalaa.com.movierates;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmedalaa on 9/16/16.
 */
public class ReviewParser {
    String j_data;
    ArrayList<MovieAdded> data;

    public ReviewParser(String j_data) {
        this.j_data = j_data;
        data = new ArrayList();
    }

    public ArrayList<MovieAdded> getData() throws JSONException {

        JSONArray ar = new JSONObject(j_data).getJSONArray("results");
        for (int i = 0; i < ar.length(); i++) {
            JSONObject obj = ar.getJSONObject(i);
            data.add(new Review(obj.getString("id"), obj.getString("author"),
                    obj.getString("content"), obj.getString("url")));
        }
        return data;
    }
}
