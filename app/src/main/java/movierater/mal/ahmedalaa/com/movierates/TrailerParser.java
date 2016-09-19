package movierater.mal.ahmedalaa.com.movierates;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmedalaa on 9/16/16.
 */
public class TrailerParser {

    String j_data;
    ArrayList<MovieAdded> data;

    public TrailerParser(String j_data) {
        this.j_data = j_data;
        data = new ArrayList();
    }

    public ArrayList<MovieAdded> getData() throws JSONException {
        JSONArray ar = new JSONObject(j_data).getJSONArray("results");
        for (int i = 0; i < ar.length(); i++) {
            JSONObject obj = ar.getJSONObject(i);
            data.add(new Trailer(obj.getString("id"), obj.getString("iso_639_1"),
                    obj.getString("iso_3166_1"), obj.getString("key"),
                    obj.getString("name"), obj.getString("site"),
                    obj.getString("size"), obj.getString("type")));
        }
        return data;
    }
}
