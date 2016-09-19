package movierater.mal.ahmedalaa.com.movierates;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.*;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmedalaa on 8/13/16.
 */
public class GridViewAdapter extends BaseAdapter {

    Context usedContext;
    public ArrayList<String> imgIds;

    public GridViewAdapter(Context main) {
        usedContext = main;
    }

    public ArrayList<String> getImgIds() {
        return imgIds;
    }

    public void setImgIds (ArrayList<String> imgs) {
        this.imgIds = imgs;
    }

    @Override
    public int getCount() {
        return imgIds.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(usedContext);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        String url = imgIds.get(position);
        Picasso.with(usedContext)
                .load(url)
                .resize(300,300)
                .centerCrop()
                .into(imageView);
        return imageView;
    }
}
